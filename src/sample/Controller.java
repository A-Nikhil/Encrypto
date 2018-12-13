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

import java.awt.*;

public class Controller {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private Label statusBar;

    // All the stages here
    private Stage LoginWindow = new Stage();

    public void pressLogin(ActionEvent event) throws Exception {
        System.out.println("Login works");
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        LoginWindow.setTitle("Login Screen");
        LoginWindow.setScene(new Scene(root, 600, 400));
        LoginWindow.show();
    }

    public void pressSignup(ActionEvent event) {
        System.out.println("Signup works");
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
            statusBar.setTextFill(Paint.valueOf("#43D61F"));
        }
    }
}
