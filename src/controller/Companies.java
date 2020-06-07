package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Main;
import model.Company;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class Companies implements Initializable {
    @FXML
    TableView <Company> companiesTblView;
    @FXML
    TableColumn <Company, String> nameTblCol;
    @FXML
    Label successLbl;
    @FXML
    Label errorLbl;
    @FXML
    TextField nameTxt;
    @FXML
    Button saveBtn;
    @FXML
    Button closeBtn;
    @FXML
    Button deleteBtn;
    @FXML
    TextField phoneTxt;
    @FXML
    TableColumn <Company, String> phoneTblCol;

    @FXML
    public void backToMain(ActionEvent actionEvent) {
            try {
                Main.showWindow(
                        getClass(),
                        "/view/User.fxml",
                        "Dobrodošli",
                        1200,
                        1200, 300, 0);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Došlo je do pogreške");
            }
    }

    @FXML
    public void goToContacts (ActionEvent ev){
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Contacts.fxml",
                    "Kontakti", 1200, 700, 250, 100);
        } catch (IOException e) {
            System.out.println("Došlo je do pogreške.");
        }
    }

    @FXML
    public void logout(ActionEvent ev) {
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Login.fxml",
                    "Prijavite se", 515, 310, 550, 200);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Došlo je do pogreške");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nameTblCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.phoneTblCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        populateTableView();
        companiesTblView.setEditable(true);
        this.nameTblCol.setEditable(true);
        this.nameTblCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.phoneTblCol.setEditable(true);
        this.phoneTblCol.setCellFactory(TextFieldTableCell.forTableColumn());
    }
    @FXML
    public void editName (TableColumn.CellEditEvent<Company, String> evt) throws Exception {
        Company c = evt.getRowValue();
        c.setName(evt.getNewValue());
        c.update();
    }
    @FXML
    public void editPhone (TableColumn.CellEditEvent<Company, String> evt) throws Exception {
        Company c = evt.getRowValue();
        c.setPhone(evt.getNewValue());
        c.update();
    }
    private void populateTableView() { try {
        this.companiesTblView.getItems().setAll((Collection<?extends Company>) Company.list(Company.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }

    public void save(ActionEvent actionEvent) {
        String naziv = this.nameTxt.getText().toString();
        if (!(naziv.equals(""))){
            Company c = new Company();
            c.setName(this.nameTxt.getText());
            nameTxt.setText("");
            try {
                c.save();
                this.populateTableView();
            } catch (Exception e) {
                e.printStackTrace();
                errorLbl.setText("Došlo je do pogreške.");
                errorLbl.setVisible(true);
                successLbl.setVisible(false);
            }}}

    public void close(ActionEvent actionEvent) {
        saveBtn.setVisible(false);
        closeBtn.setVisible(false);
        nameTxt.setVisible(false);
    }

    public void delete(ActionEvent actionEvent) throws Exception {
        Company c = companiesTblView.getSelectionModel().getSelectedItem();
        c.delete();
        this.populateTableView();
    }

    public void addNew(ActionEvent actionEvent) {
        saveBtn.setVisible(true);
        closeBtn.setVisible(true);
        nameTxt.setVisible(true);
    }
}
