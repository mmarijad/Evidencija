package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    @FXML
    TextField nameTxt;
    @FXML
    TextField lastnameTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TextField passwordTxt;
    @FXML
    Button addUserBtn;
    @FXML
    Label errorLbl;
    @FXML
    Label successLbl;

    @FXML

    public void addUserToDatabase(javafx.event.ActionEvent actionEvent)  {
        User u = new User();
        u.setFirstName(this.nameTxt.getText());
        u.setLastName(this.lastnameTxt.getText());
        u.setEmail(this.emailTxt.getText());
        u.setPassword(this.passwordTxt.getText());
        try {
            u.save();
        } catch (Exception e) {
            e.printStackTrace();
            errorLbl.setVisible(true);
            successLbl.setVisible(false);
        }

        errorLbl.setVisible(false);
        successLbl.setVisible(true);
        nameTxt.setText("");
        lastnameTxt.setText("");
        emailTxt.setText("");
        passwordTxt.setText("");

        }
}
