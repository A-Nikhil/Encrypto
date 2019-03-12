package Procedure;

import java.sql.*;

@SuppressWarnings("Duplicates")
public class Inbox {
    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Transfer.db");
        PreparedStatement preparedStatement = c.prepareStatement("SELECT FILELOC FROM MESSAGES WHERE TITLE = ?;");
        String title = "INTRO";
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println(resultSet.getString(1));
    }
}
