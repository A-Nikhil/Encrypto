/*
*
 */

package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;

public class Controller {

    @FXML
    private Button processLogin;
    @FXML
    private Button signMeUp;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private Label statusBar;
    @FXML
    private TextField name;
    @FXML
    private TextField uname;
    @FXML
    private TextField pass;
    @FXML
    private TextField passAgain;
    @FXML
    private TextField email;
    @FXML
    private Label statusBarSgnup;
    @FXML
    private Button Inbox;

    // All the stages here
    private Stage LoginWindow = new Stage();
    private Stage Middle = new Stage();

    private void OpenScenes(int number, String  NAME) {
        try {
            Parent root; Scene scene;
            switch (number) {
                case 1: root = FXMLLoader.load(getClass().getResource("/scenes/login.fxml"));
                    LoginWindow.setTitle("Login Window");
                    scene = new Scene(root, 600,400);
                    LoginWindow.setScene(scene);
                    LoginWindow.show();
                    break;
                case 2: root = FXMLLoader.load(getClass().getResource("/scenes/signup.fxml"));
                    LoginWindow.setTitle("Login Window");
                    scene = new Scene(root, 600,400);
                    LoginWindow.setScene(scene);
                    LoginWindow.show();
                    break;
                case 3: closeLogin(processLogin);
                    root = FXMLLoader.load(getClass().getResource("/scenes/middle.fxml"));
                    Middle.setTitle("Welcome " + NAME);
                    scene = new Scene(root, 600,400);
                    Middle.setScene(scene);
                    Middle.show();
                    break;
                case 4: closeLogin(signMeUp);
                    root = FXMLLoader.load(getClass().getResource("/scenes/middle.fxml"));
                    Middle.setTitle("Welcome " + NAME);
                    scene = new Scene(root, 600,400);
                    Middle.setScene(scene);
                    Middle.show();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeLogin(Button requestedButton) {
        Stage stage1 = (Stage) requestedButton.getScene().getWindow();
        stage1.close();
    }

    public void pressLogin(ActionEvent event) throws Exception {
        System.out.println("Login works");
        OpenScenes(1, "");
    }

    public void pressSignup(ActionEvent event) throws Exception {
        System.out.println("Signup works");
        OpenScenes(2, "");
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
        } else if(!password.equalsIgnoreCase(passwordAgain)) {
            statusBarSgnup.setText("Status: Passwords don't match, type them again");
            pass.clear();
            passAgain.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else if(!Email.contains("@") || !Email.contains(".com")) {
            statusBarSgnup.setText("Status: Not a valid email, re-enter please");
            email.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else {
            try {
                Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");

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
                OpenScenes(4,"");
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
            String name = "";
            boolean found = false;
            Connection c = DriverManager.getConnection("jdbc:sqlite:D:\\MyProjects\\Encrypto\\db\\Encrypto.db");
            Statement stmt = c.createStatement();
            ResultSet login = stmt.executeQuery("SELECT USERNAME, PASSWORD, NAME FROM USERS");
            while (login.next()) {
                String chk1 = login.getString(1);
                String chk2 = login.getString(2);
                name = login.getString(3);
                if(username.equalsIgnoreCase(chk1) && password.equalsIgnoreCase(chk2)) {
                    found = true;
                    break;
                }
            }
            stmt.close();
            login.close();
            if(found) {
                System.out.println("Success");
                statusBar.setText("Status: Login Successful");
                statusBar.setTextFill(Paint.valueOf("#43D61F"));
                System.out.println(name);
                OpenScenes(3, name);
            } else {
                System.out.println("Failure");
                statusBar.setText("Status: Login Unsuccessful, Try again");
                userName.clear();
                passWord.clear();
                statusBar.setTextFill(Paint.valueOf("#EC0A0A"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void noteTOSelf(ActionEvent event) {
        // Do Nothing
    }

    public void sendMessage(ActionEvent event) {
        // Do Nothing
    }

    public void viewInbox(ActionEvent event) {
        // Do Nothing
    }
}
