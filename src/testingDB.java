import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class testingDB {
    public static void main(String []args) {
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");
            Statement stmt = c.createStatement();
            ResultSet rs =stmt.executeQuery("SELECT * FROM USERS");
            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
