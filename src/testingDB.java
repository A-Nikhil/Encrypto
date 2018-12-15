import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class testingDB {
    public static void main(String []args) {
        String NAME = "alpha";
        System.out.println("\"INSERT INTO CURRENT VALUES (\"" + NAME + "\");");
    }
}
