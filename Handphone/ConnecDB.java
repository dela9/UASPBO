package Handphone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnecDB {
    private String url, usr, pwd, db;

    // Konstruktor
    public ConnecDB(){
        db = "toko_Handphone";
        url = "jdbc:mysql://localhost/" + db;
        usr = "root";
        pwd = "";
    }
    
    // Metode untuk mendapatkan koneksi
    public Connection getConnect(){
        Connection cn = null;
        try {
            // Memuat driver database
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url, usr, pwd);
            System.out.println("Koneksi Berhasil");
        } catch (ClassNotFoundException er) {
            System.out.println("Error #1: " + er.getMessage());
            System.exit(0);
        } catch (SQLException er) {
            System.out.println("Error #2: " + er.getMessage());
        }
        return cn;
    }

    // Metode main
    public static void main(String[] args) {
        new ConnecDB().getConnect();
    }
}
