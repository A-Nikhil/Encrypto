package sample;

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

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
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

    // For Decryption
    @FXML
    private Button loadMessage, decryptButton;
    @FXML
    private ImageView ImageHere;
    @FXML
    private Label MessageHere, sender;

    // All the stages here
    private Stage LoginWindow = new Stage();
    private Stage Middle = new Stage();
    private Stage Processing = new Stage();

    private String imageLocation = "";

    private void OpenScenes(int number) {
        try {
            // Finding the Name
            String NAME = "";
            Connection c = DriverManager.getConnection(DBurl);
            Statement stmt = c.createStatement();
            ResultSet naming = stmt.executeQuery("SELECT CURRNAME, CURRID FROM CURRENT;");
            while (naming.next()) {
                NAME = naming.getString(1);
            }
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
                    break;
                case 8:
                    root = FXMLLoader.load(getClass().getResource("/scenes/decryption.fxml"));
                    Processing.setTitle("Decryption ready");
                    scene = new Scene(root, 600,500);
                    Processing.setScene(scene);
                    Processing.show();
                    break;
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
        OpenScenes(9);
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

    private ArrayList<String> Titles = new ArrayList<>();
    private String title;
    private ArrayList<String> SenderNames = new ArrayList<>();
    private String senderName;
    public void refreshIt() {
        try {
            String x = ""; String status, usableName = "";
            int v = 1;
            Connection c = DriverManager.getConnection(DBurl);
            Statement stmt = c.createStatement();
            ResultSet naming = stmt.executeQuery("SELECT CURRNAME, CURRID FROM CURRENT;");
            while (naming.next()) {
                usableName = naming.getString(1);
            }
            System.out.println(usableName);
            PreparedStatement messages = c.prepareStatement("SELECT SENDERID, FILELOC, TITLE, STATUS, RECEIVERID AS READ FROM MESSAGES INNER JOIN USERS ON USERID = RECEIVERID WHERE USERS.NAME = ?");
            messages.setString(1, usableName);
            ResultSet resultSet = messages.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt(4) == 0)
                    status = "UNREAD";
                else
                    status = "READ";
                PreparedStatement preparedStatement = c.prepareStatement("SELECT NAME FROM USERS WHERE USERID = ?");
                preparedStatement.setInt(1, resultSet.getInt(1));
                naming = preparedStatement.executeQuery();
                String goodName = naming.getString(1);
                x = x.concat(v + ": " + resultSet.getString(3) + " by " + goodName + " [" + status + "] \n");
                Titles.add(resultSet.getString(3));
                SenderNames.add(goodName);
            }
            view.setVisible(true);
            number.setVisible(true);
            refreshMessage.setVisible(false);
            messageList.setText(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String messageSimple;
    private String messageCoded;
    private String ImageReady;

    public void viewMessage() {
        String x = number.getText();
        System.out.println(x);
        if (x.length() == 0) {
            number.clear();
            number.setPromptText("Invalid");
        }
        title = Titles.get((Integer.parseInt(x)) - 1);
        senderName = SenderNames.get((Integer.parseInt(x)) - 1);
        System.out.println(title);
        runIt();
        System.out.println(messageSimple + " \n" + messageCoded + " \n" + ImageReady);
        OpenScenes(8);
    }

    private ResultSet messageArray;
    public void loadMessage() {
        try {
            Connection c = DriverManager.getConnection(DBurl);
            PreparedStatement preparedStatement = c.prepareStatement("SELECT FILELOC FROM MESSAGES WHERE TITLE = ?;");
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
            ImageHere.setImage(new Image(resultSet.getString(1)));
            sender.setText("This is from " + senderName);
            preparedStatement = c.prepareStatement("SELECT SIMPLE, CODED FROM BKDOORM WHERE TITLE = ?");
            preparedStatement.setString(1, title);
            messageArray = preparedStatement.executeQuery();
            String coded = generator(messageArray.getString(2));
            MessageHere.setText(coded);
            loadMessage.setVisible(false);
            decryptButton.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptReady() {
        try {
            String simple = generator(messageArray.getString(1));
            MessageHere.setText(simple);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runIt() {
        try {
            Connection c = DriverManager.getConnection(DBurl);
            PreparedStatement getDeets = c.prepareStatement("SELECT SIMPLE, CODED FROM BKDOORM WHERE TITLE = ?");
            getDeets.setString(1, title);
            ResultSet getDetails = getDeets.executeQuery();
            messageSimple = getDetails.getString(1);
            messageCoded = getDetails.getString(2);
            getDeets = c.prepareStatement("SELECT FILELOC FROM MESSAGES WHERE TITLE = ?");
            getDeets.setString(1, title);
            ResultSet resultSet = getDeets.executeQuery();
            ImageReady = resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generator(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String x, y = "";
        try {
            while ((x = reader.readLine()) != null)
                y = y.concat(x);
        } catch (EOFException e) {
            // end of case
        }
        return y;
    }
}