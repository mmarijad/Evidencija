package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Main;
import model.Database;
import model.User;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    @FXML
    Button deleteBtn;
    @FXML
    TextField nameTxt;
    @FXML
    TextField lastnameTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TextField passwordTxt;
    @FXML
    Button saveUserBtn;
    @FXML
    Button addUserBtn;
    @FXML
    Button addToAdminBtn;
    @FXML
    Button moveFromAdminBtn;
    @FXML
    Button cancelBtn;
    @FXML
    Label errorLbl;
    @FXML
    Label successLbl;
    @FXML
    CheckBox adminChck;
    @FXML
    Label helloLbl;
    @FXML
    Button odjavaBtn;
    @FXML
    Button updateData;
    @FXML
    Button updateData1;
    @FXML
    Label nameLbl;
    @FXML
    Label lastnameLbl;
    @FXML
    Label emailLbl;
    @FXML
    Label passwordLbl;
    @FXML
    TextField adminNameTxt;
    @FXML
    TextField adminLastNameTxt;
    @FXML
    TextField adminEmailTxt;
    @FXML
    TextField adminPasswordTxt;
    @FXML
    Button closeBtn;
    @FXML
    TableView <User> tableView;
    @FXML
    TableColumn <User, String> nameTblClmn;
    @FXML
    TableColumn <User, String> lastnameTblClmn;
    @FXML
    TableColumn <User, String> emailTblClmn;
    @FXML
    TableColumn <User, String> adminTblClmn;
    @FXML
    TableView <User> admintableView;
    @FXML
    TableColumn <User, String> AnameTblClmn;
    @FXML
    TableColumn <User, String> AlastnameTblClmn;
    @FXML
    TableColumn <User, String> AemailTblClmn;
    @FXML
    TableColumn <User, String> AadminTblClmn;

    @FXML
    public void addUserToDatabase(javafx.event.ActionEvent actionEvent) {
        String email = this.emailTxt.getText().toString();
        String lozinka = this.passwordTxt.getText().toString();
        String ime = this.nameTxt.getText().toString();
        String prezime = this.lastnameTxt.getText().toString();


         if (email.equals("") || lozinka.equals("") || ime.equals("") || prezime.equals("")){
            errorLbl.setText("Morate popuniti sva polja.");
            errorLbl.setVisible(true);
            successLbl.setVisible(false);
        }

        else {
             User u = new User();
             if (adminChck.isSelected()){
                u.setFirstName(this.nameTxt.getText());
                u.setLastName(this.lastnameTxt.getText());
                u.setEmail(this.emailTxt.getText());
                u.setPassword(this.passwordTxt.getText());
                u.setRole("ADMIN");
        }
            else {
                u.setFirstName(this.nameTxt.getText());
                u.setLastName(this.lastnameTxt.getText());
                u.setEmail(this.emailTxt.getText());
                u.setPassword(this.passwordTxt.getText());
                u.setRole("USER");
        }
            try {
                u.save();
                this.populateTableView();
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

    }}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.helloLbl.setText("Dobrodošli," + "\n"  +Login.LoggedInUser.getFirstName()+" "+ Login.LoggedInUser.getLastName());
        this.nameTblClmn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.emailTblClmn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.adminTblClmn.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.populateTableView();
        this.tableView.setEditable(true);
        this.nameTblClmn.setEditable(true);
        this.lastnameTblClmn.setEditable(true);
        this.nameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lastnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.AnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.AlastnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.AemailTblClmn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.AadminTblClmn.setCellValueFactory(new PropertyValueFactory<>("role"));
        this.populateAdminTableView();
        this.admintableView.setEditable(true);
        this.AnameTblClmn.setEditable(true);
        this.AlastnameTblClmn.setEditable(true);
        this.AnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.AlastnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void populateTableView() { try {
        this.tableView.getItems().setAll((Collection<?extends User>) User.users(User.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    private void populateAdminTableView() { try {
        this.admintableView.getItems().setAll((Collection<?extends User>) User.admins(User.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }

    @FXML
    public void editUserFirstName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        User u = evt.getRowValue();
        u.setFirstName(evt.getNewValue());
        u.update();
    }
    @FXML
    public void editUserLastName (TableColumn.CellEditEvent<User, String> evt) throws Exception {
        User u = evt.getRowValue();
        u.setLastName(evt.getNewValue());
        u.update();
    }

    @FXML
    public void deleteUser (ActionEvent ev) throws Exception {
        User u = tableView.getSelectionModel().getSelectedItem();
        u.delete();
        this.populateTableView();
    }

    @FXML
    public void odjava (ActionEvent ev){
        Login.LoggedInUser = null;
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Login.fxml",
                    "Prijavite se", 515, 310, 550, 200);
        } catch (IOException e) {
            System.out.println("Došlo je do pogreške.");
        }
    };
    @FXML
    public void updateData (ActionEvent ev){
        adminNameTxt.setText(Login.LoggedInUser.getFirstName());
        adminLastNameTxt.setText(Login.LoggedInUser.getLastName());
        adminEmailTxt.setText(Login.LoggedInUser.getEmail());
        adminPasswordTxt.setText(Login.LoggedInUser.getPassword());
        adminNameTxt.setVisible(true);
        adminLastNameTxt.setVisible(true);
        adminEmailTxt.setVisible(true);
        adminPasswordTxt.setVisible(true);
        updateData1.setVisible(true);
        updateData.setDisable(true);
        closeBtn.setVisible(true);
        nameTxt.setVisible(false);
        lastnameTxt.setVisible(false);
        emailTxt.setVisible(false);
        passwordTxt.setVisible(false);
        nameLbl.setVisible(false);
        lastnameLbl.setVisible(false);
        emailLbl.setVisible(false);
        passwordLbl.setVisible(false);
        cancelBtn.setVisible(false);
        saveUserBtn.setVisible(false);
        addUserBtn.setDisable(false);
        adminChck.setVisible(false);
    };
    @FXML
    public void updateData1 (ActionEvent ev){
        Login.LoggedInUser.setFirstName(this.adminNameTxt.getText());
        Login.LoggedInUser.setLastName(this.adminLastNameTxt.getText());
        Login.LoggedInUser.setEmail(this.adminEmailTxt.getText());
        Login.LoggedInUser.setPassword(this.adminPasswordTxt.getText());
        Login.LoggedInUser.setRole("ADMIN");
        adminNameTxt.setVisible(false);
        adminLastNameTxt.setVisible(false);
        adminEmailTxt.setVisible(false);
        adminPasswordTxt.setVisible(false);
        updateData1.setVisible(false);
        closeBtn.setVisible(false);
        try {
            Login.LoggedInUser.update();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void close(ActionEvent actionEvent) {
        adminNameTxt.setText("");
        adminLastNameTxt.setText("");
        adminEmailTxt.setText("");
        adminPasswordTxt.setText("");
        adminNameTxt.setVisible(false);
        adminLastNameTxt.setVisible(false);
        adminEmailTxt.setVisible(false);
        adminPasswordTxt.setVisible(false);
        updateData1.setVisible(false);
        updateData.setDisable(false);
        closeBtn.setVisible(false);
    }

    public void closeAddUser(ActionEvent actionEvent) {
        adminNameTxt.setText("");
        adminLastNameTxt.setText("");
        adminEmailTxt.setText("");
        adminPasswordTxt.setText("");
        adminNameTxt.setVisible(false);
        adminLastNameTxt.setVisible(false);
        adminEmailTxt.setVisible(false);
        adminPasswordTxt.setVisible(false);
        updateData1.setVisible(false);
        updateData.setDisable(false);
        closeBtn.setVisible(false);
        nameTxt.setVisible(false);
        lastnameTxt.setVisible(false);
        emailTxt.setVisible(false);
        passwordTxt.setVisible(false);
        nameLbl.setVisible(false);
        lastnameLbl.setVisible(false);
        emailLbl.setVisible(false);
        passwordLbl.setVisible(false);
        cancelBtn.setVisible(false);
        saveUserBtn.setVisible(false);
        addUserBtn.setDisable(false);
        adminChck.setVisible(false);
    }

    public void openAddUser(ActionEvent actionEvent) {
        adminNameTxt.setText("");
        adminLastNameTxt.setText("");
        adminEmailTxt.setText("");
        adminPasswordTxt.setText("");
        adminNameTxt.setVisible(false);
        adminLastNameTxt.setVisible(false);
        adminEmailTxt.setVisible(false);
        adminPasswordTxt.setVisible(false);
        updateData1.setVisible(false);
        updateData.setDisable(false);
        closeBtn.setVisible(false);
        nameTxt.setVisible(true);
        lastnameTxt.setVisible(true);
        emailTxt.setVisible(true);
        passwordTxt.setVisible(true);
        nameLbl.setVisible(true);
        lastnameLbl.setVisible(true);
        emailLbl.setVisible(true);
        passwordLbl.setVisible(true);
        cancelBtn.setVisible(true);
        saveUserBtn.setVisible(true);
        addUserBtn.setDisable(true);
        adminChck.setVisible(true);
    }

    public void moveFromAdmin(ActionEvent actionEvent) {
        User u = admintableView.getSelectionModel().getSelectedItem();
        u.moveFromAdmin();
        this.populateTableView();
        this.populateAdminTableView();
    }

    public void addToAdmin(ActionEvent actionEvent) {
        User u = tableView.getSelectionModel().getSelectedItem();
        u.makeAdmin();
        this.populateTableView();
        this.populateAdminTableView();
    }
}