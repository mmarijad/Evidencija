package controller;

import main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.Initializable;
import model.User;

import java.sql.SQLException;

public class Login implements Initializable {
    public static User LoggedInUser;

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
    public void prijava(ActionEvent ev) throws SQLException {
        String email = this.emailTxt.getText().toString();
        String lozinka = this.lozinkaTxt.getText().toString();

        if (email.equals("") || lozinka.equals("")) {
            greskaLbl.setVisible(true);
            uspjehLbl.setVisible(false);
        } else {
                greskaLbl.setVisible(false);
                uspjehLbl.setVisible(true);
                   try {
                       Login.LoggedInUser = User.login(email, lozinka);
                       if (Login.LoggedInUser != null){

                           if(Login.LoggedInUser.getRole().equals("ADMIN")){
                           Main.showWindow(
                                   getClass(),
                                   "/view/Admin.fxml",
                                   "Administracija",
                                   1100,
                                   800, 300, 30);}
                                   else {
                               Main.showWindow(
                                       getClass(),
                                       "/view/User.fxml",
                                       "Dobrodošli",
                                       1200,
                                       1200, 250, 0);
                                   }
                           }

                       else {
                           greskaLbl.setText("Molimo, unesite valjane korisničke podatke.");
                           greskaLbl.setVisible(true);
                           uspjehLbl.setVisible(false);
                       }

                   } catch (Exception e) {
                       Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
                       System.out.println("Došlo je do pogreške");
                   }
            }

        }

@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
