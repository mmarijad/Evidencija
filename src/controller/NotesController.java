package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import model.Note;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class NotesController implements Initializable {
    @FXML
    Button backToMainBtn;
    @FXML
    Button logoutBtn;
    @FXML
    Button newNoteBtn;
    @FXML
    Button saveBtn;
    @FXML
    Button closeBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button deleteBtn1;
    @FXML
    Button markAsReadBtn;
    @FXML
    Button markAsUnreadBtn;
    @FXML
    Button showAllBtn;
    @FXML
    Button showUnreadBtn;
    @FXML
    Label errorLbl;
    @FXML
    Label successLbl;
    @FXML
    Label notesLbl;
    @FXML
    Label nameLbl;
    @FXML
    Label textLbl;
    @FXML
    Label contactLbl;
    @FXML
    TextField nameTxt;
    @FXML
    TextArea noteTxt;
    @FXML
    ChoiceBox contactsChoicebox;
    @FXML
    TableView <Note> unreadNotesTableView;
    @FXML
    TableColumn <Note, String> unreadTitleTblCol;
    @FXML
    TableColumn <Note, String> unreadTextTblCol;
    @FXML
    TableView <Note> allNotesTableView;
    @FXML
    TableColumn <Note, String> titleTblCol;
    @FXML
    TableColumn <Note, String> textTblCol;
    @FXML
    public void backToMain(ActionEvent ev) {
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

    public void addNew(ActionEvent ev) {
        saveBtn.setVisible(true);
        closeBtn.setVisible(true);
        nameLbl.setVisible(true);
        textLbl.setVisible(true);
        contactLbl.setVisible(true);
        nameTxt.setVisible(true);
        noteTxt.setVisible(true);
        newNoteBtn.setVisible(false);
    }
    public void save(ActionEvent ev){
        String title = this.nameTxt.getText().toString();
        String note = this.noteTxt.getText().toString();
        if (title.equals("") || note.equals("")){
            errorLbl.setText ("Niste dodali poruku.");
            successLbl.setVisible(false);
            errorLbl.setVisible(true);
        } else {
            Note n= new Note();
            n.setTitle(this.nameTxt.getText());
            n.setText(this.noteTxt.getText());
            n.setReadYN("NE");
            n.setContactFK(2);
            n.setUserFK(Login.LoggedInUser.getId());
            nameTxt.setText("");
            noteTxt.setText("");
            successLbl.setVisible(true);
            errorLbl.setVisible(false);
            saveBtn.setVisible(false);
            nameLbl.setVisible(false);
            textLbl.setVisible(false);
            nameTxt.setVisible(false);
            noteTxt.setVisible(false);
            newNoteBtn.setVisible(true);
            successLbl.setVisible(false);
            closeBtn.setVisible(false);
            try {
                n.save();
                this.populateUnreadNotesTableView();
            } catch (Exception ex) {
                ex.printStackTrace();
                errorLbl.setText("Došlo je do pogreške: dodavanje nove poruke nije uspjelo.");
                successLbl.setVisible(false);
            }}}

    public void close(ActionEvent ev) {
        saveBtn.setVisible(false);
        closeBtn.setVisible(false);
        errorLbl.setVisible(false);
        successLbl.setVisible(false);
        nameLbl.setVisible(false);
        textLbl.setVisible(false);
        contactLbl.setVisible(false);
        nameTxt.setVisible(false);
        noteTxt.setVisible(false);
        contactsChoicebox.setVisible(false);
        newNoteBtn.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.unreadTitleTblCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.unreadTextTblCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        this.populateUnreadNotesTableView();
        this.titleTblCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.textTblCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        this.populateAllNotesTableView();
    }

    private void populateAllNotesTableView() {
        try {
        this.allNotesTableView.getItems().setAll((Collection<?extends Note>) Note.allNotes(Note.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
}
    private void populateUnreadNotesTableView() {
        try {
            this.unreadNotesTableView.getItems().setAll((Collection<?extends Note>) Note.unreadNotes(Note.class));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Dohvaćanje podataka nije uspjelo.");
        }
    }
    @FXML
    public void deleteNote (ActionEvent ev) throws Exception {
        Note n = unreadNotesTableView.getSelectionModel().getSelectedItem();
        n.delete();
        this.populateUnreadNotesTableView();
        this.populateAllNotesTableView();
    }
    @FXML
    public void deleteNote1 (ActionEvent ev) throws Exception {
        Note n = allNotesTableView.getSelectionModel().getSelectedItem();
        n.delete();
        this.populateUnreadNotesTableView();
        this.populateAllNotesTableView();
    }
    @FXML
    public void markAsRead (ActionEvent ev) throws Exception {
        Note n = unreadNotesTableView.getSelectionModel().getSelectedItem();
        n.markAsRead();
        this.populateUnreadNotesTableView();
        this.populateAllNotesTableView();
    }
    @FXML
    public void markAsUnread (ActionEvent ev) throws Exception {
        Note n = allNotesTableView.getSelectionModel().getSelectedItem();
        n.markAsUnread();
        this.populateUnreadNotesTableView();
        this.populateAllNotesTableView();
    }

    public void showAll(ActionEvent actionEvent) {
        unreadNotesTableView.setVisible(false);
        showAllBtn.setVisible(false);
        showUnreadBtn.setVisible(true);
        markAsUnreadBtn.setVisible(true);
        markAsReadBtn.setVisible(false);
        allNotesTableView.setVisible(true);
        notesLbl.setText("Sve poruke");
        deleteBtn.setVisible(true);
        deleteBtn1.setVisible(false);
    }

    public void showUnread(ActionEvent actionEvent) {
        unreadNotesTableView.setVisible(true);
        showAllBtn.setVisible(true);
        showUnreadBtn.setVisible(false);
        markAsUnreadBtn.setVisible(false);
        markAsReadBtn.setVisible(true);
        allNotesTableView.setVisible(false);
        notesLbl.setText("Nepročitane poruke");
        deleteBtn1.setVisible(true);
        deleteBtn.setVisible(false);
    }
}

