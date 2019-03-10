package Procedure;

import java.awt.*;
import java.io.File;
import java.sql.*;

@SuppressWarnings("Duplicates")
public class Inbox {
    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");
        PreparedStatement preparedStatement = c.prepareStatement("SELECT FILELOC FROM MESSAGES WHERE TITLE = ?;");
        preparedStatement.setString(1, "INTRO");
        ResultSet resultSet = preparedStatement.executeQuery();
        File file = new File(resultSet.getString(1));
    }
}
