package koneksi;

import java.sql.*;
import javax.swing.JOptionPane;
public class KoneksiDatabase {
    Connection koneksi;
    public static Connection Koneksi(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/badmahasiswa", "root", "");
            return koneksi;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
