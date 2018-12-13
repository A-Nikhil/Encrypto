package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Controller {

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

    // All the stages here
    private Stage LoginWindow = new Stage();

    public void pressLogin(ActionEvent event) throws Exception {
        System.out.println("Login works");
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        LoginWindow.setTitle("Login Screen");
        LoginWindow.setScene(new Scene(root, 600, 400));
        LoginWindow.show();
    }

    public void pressSignup(ActionEvent event) throws Exception {
        System.out.println("Signup works");
        Parent root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        LoginWindow.setTitle("Sign Up Screen");
        LoginWindow.setScene(new Scene(root, 600, 400));
        LoginWindow.show();
    }

    public void ActualSignup(ActionEvent event) {
        System.out.println("Signup works");
        String Name = name.getText();
        String username = uname.getText();
        String password = pass.getText();
        String passwordAgain = passAgain.getText();
        String Email = email.getText();
        if(!password.equalsIgnoreCase(passwordAgain)) {
            statusBarSgnup.setText("Status: Passwords don't match, type them again");
            pass.clear();
            passAgain.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else if(!Email.contains("@") || !Email.contains(".com")) {
            statusBarSgnup.setText("Status: Not a valid email, re-enter please");
            email.clear();
            statusBarSgnup.setTextFill(Paint.valueOf("#EC0A0A"));
        } else {
            System.out.println("Success");
            statusBarSgnup.setText("Status: Login Successful, Opening your page shortly");
            statusBarSgnup.setTextFill(Paint.valueOf("#43D61F"));
            System.out.println(Name + " " + username + " " + password + " " + Email + " ");
        }

    }

    public void loginCreds(ActionEvent event) {
        String username = userName.getText();
        String password = passWord.getText();
        if(username.equals("ABC") && password.equals("123")) {
            System.out.println("Success");
            statusBar.setText("Status: Login Successful, Opening your page shortly");
            statusBar.setTextFill(Paint.valueOf("#43D61F"));
        } else {
            System.out.println("Failure");
            statusBar.setText("Status: Login Unsuccessful, Try again");
            userName.clear();
            passWord.clear();
            statusBar.setTextFill(Paint.valueOf("#EC0A0A"));
        }
    }
}
