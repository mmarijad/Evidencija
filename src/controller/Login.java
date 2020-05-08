package controller;

import main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Login {
    @FXML
    Button prijavaBtn;

    @FXML
    TextField emailTxt;

    @FXML
    PasswordField lozinkaTxt;

    @FXML
    Label greskaLbl;

    @FXML
    Label uspjehLbl;

    @FXML
    public void prijava(ActionEvent ev){
        String email = this.emailTxt.getText().toString();
        String lozinka = this.lozinkaTxt.getText().toString();

        if (email.equals("") || lozinka.equals("")) {
            greskaLbl.setVisible(true);
            uspjehLbl.setVisible(false);
        } else {
            greskaLbl.setVisible(false);
            uspjehLbl.setVisible(true);
               try {
                   Main.showWindow(
                           getClass(),
                           "/view/Admin.fxml",
                           "Administracija",
                           800,
                           570);
               } catch (IOException e) {
                   e.printStackTrace();
               }
        }
    }
}
