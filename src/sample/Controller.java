package sample;

import Procedure.Inbox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class Controller {

    private String DBurl = "jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db";

    @FXML
    private Button processLogin, SelectImageNote, LOGOUT, signMeUp;
    @FXML
    private TextField userName, name, uname, email, TitleNote, toID, TitleSM;
    @FXML
    private PasswordField passWord, pass, passAgain;
    @FXML
    private Label statusBar, statusBarSgnup, statusNTS, statusSM;
    @FXML
    private Button noteToSelf;
    @FXML
    private TextArea messageNote, messageSM;
    @FXML
    private ImageView imageAreaNote;

    // For Inbox only
    @FXML
    private Button view, refreshMessage;
    @FXML
    private Label messageList;
    @FXML
    private TextField number;

    // All the stages here
    private Stage LoginWindow = new Stage();
    private Stage Middle = new Stage();
    private Stage Processing = new Stage();

    private String imageLocation = "";
//    private String usableName = "";
    private int usableID = 0;

    private void OpenScenes(int number) {
        try {
            // Finding the Name
            String NAME = "";
            Connection c = DriverManager.getConnection(DBurl);
            Statement stmt = c.createStatement();
            ResultSet naming = stmt.executeQuery("SELECT * FROM CURRENT;");
            while (naming.next()) {
                NAME = naming.getString(1);
                usableID = naming.getInt(2);
//                usableName = NAME;
            }
            stmt.execute("DROP TABLE CURRENT");
            stmt.execute("CREATE TABLE CURRENT ( CURRNAME TEXT, CURRID INTEGER);");
            naming.close();
            stmt.close();
            c.close();

            Parent root;
            Scene scene;
            switch (number) {
                case 1:
                    root = FXMLLoader.load(getClass().getResource("/scenes/login.fxml"));
                    LoginWindow.setTitle("Login Window");
                    scene = new Scene(root, 600, 400);
                    LoginWindow.setScene(scene);
                    LoginWindow.show();
                    break;
                case 2:
                    root = FXMLLoader.load(getClass().getResource("/scenes/signup.fxml"));
                    LoginWindow.setTitle("Login Window");
                    scene = new Scene(root, 600, 400);
                    LoginWindow.setScene(scene);
                    LoginWindow.show();
                    break;
                case 3:
                    closeTheWindow(processLogin);
                    root = FXMLLoader.load(getClass().getResource("/scenes/middle.fxml"));
                    Middle.setTitle("Welcome " + NAME);
                    scene = new Scene(root, 600, 400);
                    Middle.setScene(scene);
                    Middle.show();
                    break;
                case 4:
                    closeTheWindow(signMeUp);
                    root = FXMLLoader.load(getClass().getResource("/scenes/middle.fxml"));
                    Middle.setTitle("Welcome " + NAME);
                    scene = new Scene(root, 600, 400);
                    Middle.setScene(scene);
                    Middle.show();
                    break;
                case 5:
                    closeTheWindow(noteToSelf);
                    root = FXMLLoader.load(getClass().getResource("/scenes/notetoself.fxml"));
                    Processing.setTitle("Hey " + NAME + ", Write a Note to Yourself!");
                    scene = new Scene(root, 600, 500);
                    Processing.setScene(scene);
                    Processing.show();
                    break;
                case 6:
                    closeTheWindow(noteToSelf); // Same button used, since all point to middle
                    root = FXMLLoader.load(getClass().getResource("/scenes/sendmessage.fxml"));
                    Processing.setTitle("Hey " + NAME + ", Send a Message to Someone!");
                    scene = new Scene(root, 600, 500);
                    Processing.setScene(scene);
                    Processing.show();
                    break;
                case 7:
                    closeTheWindow(noteToSelf); // Same button used, since all point to middle
                    root = FXMLLoader.load(getClass().getResource("/scenes/inbox.fxml"));
                    Processing.setTitle("Hey " + NAME + ", This is your Inbox!");
                    scene = new Scene(root, 600, 550);
                    Processing.setScene(scene);
                    Processing.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeTheWindow(Button requestedButton) {
        Stage stage1 = (Stage) requestedButton.getScene().getWindow();
        stage1.close();
    }

    public void logout(ActionEvent event) {
        OpenScenes(1);
        closeTheWindow(LOGOUT);
    }

    public void pressLogin(ActionEvent event) throws Exception {
        System.out.println("Login works");
        OpenScenes(1);
    }

    public void pressSignup(ActionEvent event) throws Exception {
        System.out.println("Signup works");
        OpenScenes(2);
    }

    public void ActualSignup(ActionEvent event) {
        System.out.println("Signup works");
        String Name = name.getText();
        String username = uname.getText();
        String password = pass.getText();
        String passwordAgain = passAgain.getText();
        String Email = email.getText();
        if (Name == null || username == null || password == null || passwordAgain == null || Email == null) {
            statusBarSgnup.setText("Status: One of the fields is null, FILL IT");
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else if (!password.equalsIgnoreCase(passwordAgain)) {
            statusBarSgnup.setText("Status: Passwords don't match, type them again");
            pass.clear();
            passAgain.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else if (!Email.contains("@") || !Email.contains(".com")) {
            statusBarSgnup.setText("Status: Not a valid email, re-enter please");
            email.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else {
            try {
                Connection c = DriverManager.getConnection(DBurl);

                // Finding a unique user id
                Statement stmt = c.createStatement();
                ResultSet FAQ = stmt.executeQuery("SELECT MAX(USERID) FROM USERS");
                int userID = FAQ.getInt(1) + 1;
                stmt.close();

                PreparedStatement pstmt = c.prepareStatement("INSERT INTO USERS(USERID, USERNAME, PASSWORD, NAME, EMAIL) VALUES (?, ?, ?, ?, ?);");
                pstmt.setInt(1, userID);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, Name);
                pstmt.setString(5, Email);
                pstmt.executeUpdate();
                pstmt.close();

                PreparedStatement insertion = c.prepareStatement("INSERT INTO CURRENT VALUES (?, ?)");
                insertion.setString(1, Name);
                insertion.setInt(2, userID);
                insertion.executeUpdate();
                insertion.close();

                c.close();

                OpenScenes(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Success");
            statusBarSgnup.setText("Status: Login Successful, Opening your page shortly");
            statusBarSgnup.setTextFill(Paint.valueOf("#43D61F"));
            System.out.println(Name + " " + username + " " + password + " " + Email + " ");
        }

    }

    public void loginCreds(ActionEvent event) {
        String username = userName.getText();
        String password = passWord.getText();
        try {
            String name = ""; int userID = 0;
            boolean found = false;
            Connection c = DriverManager.getConnection(DBurl);
            Statement stmt = c.createStatement();
            ResultSet login = stmt.executeQuery("SELECT USERNAME, PASSWORD, NAME, USERID FROM USERS");
            while (login.next()) {
                String chk1 = login.getString(1);
                String chk2 = login.getString(2);
                name = login.getString(3);
                userID = login.getInt(4);
                if (username.equalsIgnoreCase(chk1) && password.equalsIgnoreCase(chk2)) {
                    found = true;
                    break;
                }
            }
            stmt.close();
            if (found) {
                System.out.println("Success");
                statusBar.setText("Status: Login Successful");
                statusBar.setTextFill(Paint.valueOf("#43D61F"));

                PreparedStatement insertion = c.prepareStatement("INSERT INTO CURRENT VALUES (?, ?)");
                insertion.setString(1, name);
                insertion.setInt(2, userID);
                insertion.executeUpdate();
                insertion.close();

                System.out.println(name);
                OpenScenes(3);
                c.close();
            } else {
                System.out.println("Failure");
                statusBar.setText("Status: Login Unsuccessful, Try again");
                userName.clear();
                passWord.clear();
                statusBar.setTextFill(Paint.valueOf("#EC0A0A"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void noteTOSelf(ActionEvent event) {
        System.out.println("Note to Self works ");
        OpenScenes(5);
    }

    public void sendMessage(ActionEvent event) {
        System.out.println("Send Message works");
        OpenScenes(6);
    }

    public void viewInbox(ActionEvent event) {
        System.out.println("Inbox works");
        OpenScenes(7);
    }

    public void viewActivity(ActionEvent event) {
        System.out.println("Activity works");
        OpenScenes(8);
    }

    steganography.Encryption Encrypt = new steganography.Encryption();

    public void selectImageNote(ActionEvent event) {
        List<String> extensions = new ArrayList<>();
        extensions.add("*.bmp");
        extensions.add("*.jpg");
        extensions.add("*.jpeg");
        extensions.add("*.tiff");
        extensions.add("*.png");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Select any Image", extensions));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            System.out.println("Selected file is : " + file.getAbsolutePath());
            imageLocation = file.getAbsolutePath();
            String imagePath = file.toURI().toString();
            Image yourImage = new Image(imagePath);
            imageAreaNote.setImage(yourImage);
            SelectImageNote.setVisible(false);
            statusNTS.setText("Status: Working");
            statusNTS.setTextFill(Paint.valueOf("#43D61F"));
            Encrypt.imageLoc = file.getAbsolutePath();
        }
    }

    public void proceedNote(ActionEvent event) throws Exception {
        String title = TitleNote.getText();
        String message = messageNote.getText();
        if (title == null) {
            statusNTS.setText("Status: Title should not be empty");
            TitleNote.clear();
            statusNTS.setTextFill(Paint.valueOf("#EC0A0A"));
        } else if (message == null) {
            statusNTS.setText("Status: Message should not be empty");
            messageNote.clear();
            statusNTS.setTextFill(Paint.valueOf("#EC0A0A"));
        } else {
            System.out.println(title);
            System.out.println(message);
            statusNTS.setText("Status: Working");
            statusNTS.setTextFill(Paint.valueOf("#43D61F"));

            int senderID = 0;
            Connection c = DriverManager.getConnection(DBurl);
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT USERID from CURRENT;");
            while (resultSet.next())
                senderID = resultSet.getInt(1);
            stmt.close();

            Encrypt.text = message;
            Encrypt.imageLoc = imageLocation;
            Encrypt.title = title;
            String outputLoc = Encrypt.performOperation(senderID, senderID);

            PreparedStatement preparedStatement = c.prepareStatement("INSERT INTO NTS VALUES (?, ?, ?)");
            preparedStatement.setString(1, Integer.toString(senderID));
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, outputLoc);
            preparedStatement.executeUpdate();

            System.out.println("Completed");
        }
    }

    public void proceedMessage(ActionEvent event) {
        int recID = Integer.parseInt(toID.getText());
        String title = TitleSM.getText();
        String message = messageSM.getText();

        try {
            Connection c = DriverManager.getConnection("");
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery("");
            if (recID == 0 || resultSet.getInt(1) == 0){
                statusSM.setText("Status: Invalid USER ID");
                toID.clear();
                statusSM.setTextFill(Paint.valueOf("#EC0A0A"));
            } else if (title == null) {
                statusSM.setText("Status: Title should not be empty");
                TitleSM.clear();
                statusSM.setTextFill(Paint.valueOf("#EC0A0A"));
            } else if (message == null) {
                statusSM.setText("Status: Message should not be empty");
                messageSM.clear();
                statusSM.setTextFill(Paint.valueOf("#EC0A0A"));
            } else {
                System.out.println(recID);
                System.out.println(title);
                System.out.println(message);
                statusSM.setText("Status: Working");
                statusSM.setTextFill(Paint.valueOf("#43D61F"));

                int senderID = 0;
                stmt.close();
                c.close();
                c = DriverManager.getConnection(DBurl);
                stmt = c.createStatement();
                resultSet = stmt.executeQuery("SELECT USERID from CURRENT;");
                while (resultSet.next())
                    senderID = resultSet.getInt(1);
                stmt.close();
                c.close();

                Encrypt.text = message;
                String outputLoc = Encrypt.performOperation(senderID, recID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> fileLocations = new ArrayList<>();
    public String title;
    public void refreshIt() {
        try {
            String x = ""; String status;
            int v = 1;
            Connection c = DriverManager.getConnection(DBurl);
            Statement messages = c.createStatement();
            ResultSet resultSet = messages.executeQuery("SELECT USERS.NAME AS SENDER, FILELOC, TITLE, STATUS, RECEIVERID AS READ FROM MESSAGES INNER JOIN USERS ON USERID = SENDERID;");
            while (resultSet.next()) {
                if (resultSet.getInt(5) != usableID)
                    continue;
                if (resultSet.getInt(4) == 0)
                    status = "UNREAD";
                else
                    status = "READ";
                x = x.concat(v + ": " + resultSet.getString(3) + " by " + resultSet.getString(1) + " [" + status + "] \n");
                fileLocations.add(resultSet.getString(2));
                title = resultSet.getString(3);
            }
            view.setVisible(true);
            number.setVisible(true);
            messageList.setText(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String messageSimple;
    public String messageCoded;
    public String ImageReady;
    public void viewMessage() {
        String x = number.getText();
        if (x.length() == 0) {
            number.clear();
            number.setPromptText("Invalid");
        }

        Inbox inbox = new Inbox();
        inbox.fileLoc = fileLocations.get((Integer.parseInt(x)) - 1);
        inbox.runIt(DBurl);
    }
}