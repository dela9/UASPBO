package Handphone;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormToko extends JFrame {
    private String[] judul = {"kode_Handphone", "nama_Handphone", "harga_Handphone", "stok_Handphone"};
    DefaultTableModel df;
    JTable tab = new JTable();
    JScrollPane scp = new JScrollPane();
    JPanel pnl = new JPanel();
    JLabel lblnama_Handphone = new JLabel("nama_Handphone");
    JTextField txnama_Handphone = new JTextField(20);
    JLabel lblkode_Handphone = new JLabel("kode_Handphone");
    JTextField txkode_Handphone = new JTextField(10);
    JLabel lblharga_Handphone = new JLabel("harga_Handphone");
    JTextArea txharga_Handphone = new JTextArea();
    JLabel lblstok_Handphone = new JLabel("stok_Handphone");
    JTextArea txstok_Handphone = new JTextArea();
    JScrollPane sca = new JScrollPane(txharga_Handphone);
    JButton btadd = new JButton("Simpan");
    JButton btnew = new JButton("Baru");
    JButton btdel = new JButton("Hapus");
    JButton btedit = new JButton("Ubah");

    FormToko() {
        super("Data Barang");
        setSize(460, 300);
        pnl.setLayout(null);
        pnl.add(lblkode_Handphone);
        lblkode_Handphone.setBounds(20, 10, 80, 20);
        pnl.add(txkode_Handphone);
        txkode_Handphone.setBounds(105, 10, 100, 20);
        pnl.add(lblnama_Handphone);
        lblnama_Handphone.setBounds(20, 33, 80, 20);
        pnl.add(txnama_Handphone);
        txnama_Handphone.setBounds(105, 33, 175, 20);
        pnl.add(lblharga_Handphone);
        lblharga_Handphone.setBounds(20, 56, 80, 20);
        pnl.add(sca);
        sca.setBounds(105, 56, 175, 45);
        pnl.add(lblstok_Handphone);
        lblstok_Handphone.setBounds(20, 110, 80, 20);
        pnl.add(txstok_Handphone);
        txstok_Handphone.setBounds(105, 110, 175, 45);

        pnl.add(btnew);
        btnew.setBounds(300, 10, 125, 20);
        btnew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnewAksi(e);
            }
        });
        pnl.add(btadd);
        btadd.setBounds(300, 33, 125, 20);
        btadd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btaddAksi(e);
            }
        });
        pnl.add(btedit);
        btedit.setBounds(300, 56, 125, 20);
        btedit.setEnabled(false);
        btedit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bteditAksi(e);
            }
        });
        pnl.add(btdel);
        btdel.setBounds(300, 79, 125, 20);
        btdel.setEnabled(false);
        btdel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btdelAksi(e);
            }
        });
        df = new DefaultTableModel(null, judul);
        tab.setModel(df);
        scp.getViewport().add(tab);
        tab.setEnabled(true);
        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });
        scp.setBounds(20, 160, 405, 100);
        pnl.add(scp);
        getContentPane().add(pnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        try {
            Connection cn = new ConnecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "SELECT * FROM toko_handphone";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String kode_Handphone, nama_Handphone, harga_Handphone, stok_Handphone;
                kode_Handphone = rs.getString("kode_Handphone");
                nama_Handphone = rs.getString("nama_Handphone");
                harga_Handphone = rs.getString("harga_Handphone");
                stok_Handphone = rs.getString("stok_Handphone");
                String[] data = {kode_Handphone, nama_Handphone, harga_Handphone, stok_Handphone};
                df.addRow(data);
            }
            rs.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void clearTable() {
        int numRow = df.getRowCount();
        for (int i = 0; i < numRow; i++) {
            df.removeRow(0);
        }
    }

    void clearTextField() {
        txkode_Handphone.setText(null);
        txnama_Handphone.setText(null);
        txharga_Handphone.setText(null);
        txstok_Handphone.setText(null);
    }

    void simpanData(Toko M) {
        try {
            Connection cn = new ConnecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "INSERT INTO toko_handphone (kode_Handphone, nama_Handphone, harga_Handphone, stok_Handphone) " +
                    "VALUES ('" + M.getkode_Handphone() + "', '" + M.getnama_Handphone() + "', '" + M.getharga_Handphone() + "', '" + M.getstok_Handphone() + "')";
            int result = st.executeUpdate(sql);
            if (result > 0) {
                // Lakukan sesuatu jika ada baris yang terpengaruh
            } else {
                // Lakukan sesuatu jika tidak ada baris yang terpengaruh
            }

            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan", "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            String[] data = {M.getkode_Handphone(), M.getnama_Handphone(), M.getharga_Handphone(), M.getstok_Handphone()};
            df.addRow(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void hapusData(String kode_Handphone) {
        try {
            Connection cn = new ConnecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "DELETE FROM toko_handphone WHERE kode_Handphone = '" + kode_Handphone + "'";
            int result = st.executeUpdate(sql);
            if (result > 0) {
                // Lakukan sesuatu jika query berhasil
            } else {
                // Lakukan sesuatu jika query tidak berhasil
            }

            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus", "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            df.removeRow(tab.getSelectedRow());
            clearTextField();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void ubahData(Toko M, String kode_Handphone) {
        try {
            Connection cn = new ConnecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "UPDATE toko_handphone SET " +
                    "nama_Handphone = '" + M.getnama_Handphone() + "', " +
                    "harga_Handphone = '" + M.getharga_Handphone() + "', " +
                    "stok_Handphone = '" + M.getstok_Handphone() + "' " +
                    "WHERE kode_Handphone = '" + kode_Handphone + "'";
            int result = st.executeUpdate(sql);
            if (result > 0) {
                // Lakukan sesuatu jika query berhasil
            } else {
                // Lakukan sesuatu jika query tidak berhasil
            }

            cn.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah", "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            clearTable();
            loadData();
            clearTextField();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void btnewAksi(ActionEvent evt) {
        clearTextField();
        btadd.setEnabled(true);
        btedit.setEnabled(false);
        btdel.setEnabled(false);
    }

    private void btaddAksi(ActionEvent evt) {
        Toko M = new Toko();
        M.setkode_Handphone(txkode_Handphone.getText());
        M.setnama_Handphone(txnama_Handphone.getText());
        M.setharga_Handphone(txharga_Handphone.getText());
        M.setstok_Handphone(txstok_Handphone.getText());
        simpanData(M);
    }

    private void bteditAksi(ActionEvent evt) {
        Toko M = new Toko();
        M.setkode_Handphone(txkode_Handphone.getText());
        M.setnama_Handphone(txnama_Handphone.getText());
        M.setharga_Handphone(txharga_Handphone.getText());
        M.setstok_Handphone(txstok_Handphone.getText());
        ubahData(M, txkode_Handphone.getText());
    }

    private void btdelAksi(ActionEvent evt) {
        hapusData(txkode_Handphone.getText());
    }

    private void tabMouseClicked(MouseEvent evt) {
        int row = tab.getSelectedRow();
        txkode_Handphone.setText(df.getValueAt(row, 0).toString());
        txnama_Handphone.setText(df.getValueAt(row, 1).toString());
        txharga_Handphone.setText(df.getValueAt(row, 2).toString());
        txstok_Handphone.setText(df.getValueAt(row, 3).toString());
        btadd.setEnabled(false);
        btedit.setEnabled(true);
        btdel.setEnabled(true);
    }

    public static void main(String[] args) {
        new FormToko().loadData();
    }
}
