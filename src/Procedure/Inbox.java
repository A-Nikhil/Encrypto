package Procedure;

import sample.Controller;

import java.sql.*;

public class Inbox {

    public String fileLoc;
    private Controller controller = new Controller();
    private String title = controller.title;

    public void runIt(String DBUrl) {
        try {
            Connection c = DriverManager.getConnection(DBUrl);
            PreparedStatement getDeets = c.prepareStatement("SELECT SIMPLE, CODED FROM BKDOORM WHERE TITLE = ?");
            getDeets.setString(1, title);
            ResultSet getDetails = getDeets.executeQuery();
            controller.messageSimple = getDetails.getString(1);
            controller.messageCoded = getDetails.getString(2);
            getDeets = c.prepareStatement("SELECT FILELOC FROM MESSAGES WHERE TITLE = ?");
            getDeets.setString(1, title);
            ResultSet resultSet = getDeets.executeQuery();
            controller.ImageReady = resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
