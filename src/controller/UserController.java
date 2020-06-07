package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import main.Main;
import model.Company;
import model.Contact;
import model.Event;
import model.Note;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    Pane eventPane;
    @FXML
    Pane contactPane;
    @FXML
    Pane notePane;
    @FXML
    TextField eventNameTxt;
    @FXML
    TextField eventDescriptionTxt;
    @FXML
    TextField eventPlaceTxt;
    @FXML
    Button addEventBtn;
    @FXML
    Button saveEventBtn;
    @FXML
    Button closeEventBtn;
    @FXML
    Label eventErrorLbl;
    @FXML
    Label eventSuccessLbl;
    @FXML
    private ComboBox<Integer> hoursCombobox;
    @FXML
    private ComboBox<Integer> minutesCombobox;
    @FXML
    DatePicker datePicker = null;
    @FXML
    TextField contactNameTxt;
    @FXML
    TextField contactLastnameTxt;
    @FXML
    TextField contactPhoneTxt;
    @FXML
    TextField contactEmailTxt;
    @FXML
    Button addContactBtn;
    @FXML
    Button saveContactBtn;
    @FXML
    Button closeContactBtn;
    @FXML
    Label contactErrorLbl;
    @FXML
    Label contactSuccessLbl;
    @FXML
    ImageView contactImage;

    BufferedImage buffImage;
    Image initialImage;
    @FXML
    Label noteErrorLbl;
    @FXML
    Label noteSuccessLbl;
    @FXML
    TextField titleTxt;
    @FXML
    TextArea noteTxt;
    @FXML
    Label helloLbl;
    @FXML
    Label reminderLbl;
    @FXML
    Button odjavaBtn;
    @FXML
    Button updateData;
    @FXML
    Button updateData1;
    @FXML
    TextField nameTxt;
    @FXML
    TextField lastNameTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TextField passwordTxt;
    @FXML
    Button eventsBtn;
    @FXML
    Button notesBtn;
    @FXML
    TableView <Event> evTableView;
    @FXML
    TableColumn <Event, String> evNameTblCol;
    @FXML
    TableColumn < Event, String> evDescriptionTblCol;
    @FXML
    TableColumn < Event, String> evPlaceTblCol;
    @FXML
    TableColumn < Event, String> evTimeTblCol;
    @FXML
    TableView<Contact> favContactsTblView;
    @FXML
    TableColumn < Contact, String> nameTblClmn;
    @FXML
    TableColumn < Contact, String> lastnameTblClmn;
    @FXML
    TableColumn < Contact, String> phoneTblClmn;
    @FXML
    TableColumn < Contact, String> emailTblClmn;
    @FXML
    TableView <Note> unreadNotesTableView;
    @FXML
    TableColumn <Note, String> unreadTitleTblCol;
    @FXML
    TableColumn <Note, String> unreadTextTblCol;

    public void addContact(ActionEvent actionEvent) {
        contactPane.setVisible(true);
    }
    public void saveContact(javafx.event.ActionEvent actionEvent) throws SQLException {
        String email = this.contactEmailTxt.getText().toString();
        String telephone = this.contactPhoneTxt.getText().toString();
        String ime = this.contactNameTxt.getText().toString();
        String prezime = this.contactLastnameTxt.getText().toString();
        if (ime.equals("") || prezime.equals ("") || (telephone.equals("") && email.equals(""))){
            contactErrorLbl.setText("Niste dodali ni broj telefona ni e-mail.");
            contactErrorLbl.setVisible(true);
            contactSuccessLbl.setVisible(false);
        }
        else {
            Contact c = new Contact();
                c.setFirstName(this.contactNameTxt.getText());
                c.setLastName(this.contactLastnameTxt.getText());
                c.setEmail(this.contactEmailTxt.getText());
                c.setTelefon(this.contactPhoneTxt.getText());
                c.setUserFK(Login.LoggedInUser.getId());
                c.setCompanyFK(1);
                c.setFavoriteYN("DA");
                SerialBlob image = new SerialBlob(imageToByte(this.buffImage));
                c.setContactImage(image);

            contactErrorLbl.setVisible(false);
            contactSuccessLbl.setVisible(true);
            contactNameTxt.setText("");
            contactLastnameTxt.setText("");
            contactPhoneTxt.setText("");
            emailTxt.setText("");
            this.contactImage.setImage(this.initialImage);
            try {
                c.save();
                this.populateTableView();
                this.populateFavoritesTableView();
            } catch (Exception e) {
                e.printStackTrace();
                contactErrorLbl.setText("Došlo je do pogreške.");
                contactErrorLbl.setVisible(true);
                contactSuccessLbl.setVisible(false);
            }}}

    public void closeContact(javafx.event.ActionEvent actionEvent){
        contactPane.setVisible(false);
        contactErrorLbl.setVisible(false);
        contactSuccessLbl.setVisible(false);
    }
    private byte[] imageToByte(BufferedImage bufferimage) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferimage, "jpg", output );
        } catch (IOException e) {
            System.out.println("Nastala je greška: " + e.getMessage());
            e.printStackTrace();
        }
        byte [] data = output.toByteArray();
        return data;
    }
    @FXML
    public void openFileDialog(MouseEvent e) throws Exception {
        FileChooser fc = new FileChooser();
        Node node = (Node) e.getSource();
        File file = fc.showOpenDialog(node.getScene().getWindow());
        this.buffImage = ImageIO.read(file);
        this.initialImage = contactImage.getImage();
        contactImage.setImage(SwingFXUtils.toFXImage(buffImage, null));
    }

    public void addEvent(ActionEvent ev) {
        eventPane.setVisible(true);
        datePicker.setValue(LocalDate.now());
    }
    public void saveEvent(ActionEvent ev){
        String name = this.eventNameTxt.getText().toString();
        String description = this.eventDescriptionTxt.getText().toString();
        String place = this.eventPlaceTxt.getText().toString();
        datePicker.setValue(LocalDate.now());
        LocalDate ld = datePicker.getValue();
        Calendar c =  Calendar.getInstance();
        int hours = hoursCombobox.getValue();
        System.out.println(hours);
        int minutes = minutesCombobox.getValue();
        int seconds = 0;
        System.out.println(minutes);
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), hours, minutes, seconds);
        Date date = c.getTime();
        if (name.equals("") || place.equals("")){
            eventErrorLbl.setText ("Niste popunili tražena polja.");
            eventSuccessLbl.setVisible(false);
            eventErrorLbl.setVisible(true);
        } else {
            Event e = new Event();
            e.setName(this.eventNameTxt.getText());
            e.setDescription(this.eventDescriptionTxt.getText());
            e.setPlace(this.eventPlaceTxt.getText());
            e.setTime(date);
            e.setUserFK(Login.LoggedInUser.getId());
            e.setRemindMe("NE");
            nameTxt.setText("");
            eventDescriptionTxt.setText("");
            eventPlaceTxt.setText("");
            eventSuccessLbl.setVisible(true);
            eventErrorLbl.setVisible(false);
            eventPane.setVisible(false);
            try {
                e.save();
                this.populateTableView();
            } catch (Exception ex) {
                ex.printStackTrace();
                eventErrorLbl.setText("Došlo je do pogreške: dodavanje novog događaja nije uspjelo.");
                eventSuccessLbl.setVisible(false);
            }}}

    public void closeEvent(ActionEvent ev){
        eventNameTxt.setText("");
        eventDescriptionTxt.setText("");
        eventPlaceTxt.setText("");
        eventErrorLbl.setVisible(false);
        eventSuccessLbl.setVisible(false);
        eventPane.setVisible(false);
    }
    public void addNote(ActionEvent ev) {
        notePane.setVisible(true);
    }

    public void saveNote(ActionEvent ev){
        String title = this.titleTxt.getText().toString();
        String note = this.noteTxt.getText().toString();
        if (title.equals("") || note.equals("")){
            noteErrorLbl.setText ("Niste dodali poruku.");
            noteSuccessLbl.setVisible(false);
            noteErrorLbl.setVisible(true);
        } else {
            Note n= new Note();
            n.setTitle(this.titleTxt.getText());
            n.setText(this.noteTxt.getText());
            n.setReadYN("NE");
            n.setContactFK(2);
            n.setUserFK(Login.LoggedInUser.getId());
            titleTxt.setText("");
            noteTxt.setText("");
            noteSuccessLbl.setVisible(false);
            noteErrorLbl.setVisible(false);
            try {
                n.save();
                this.populateUnreadNotesTableView();
            } catch (Exception ex) {
                ex.printStackTrace();
                noteErrorLbl.setText("Došlo je do pogreške: dodavanje nove poruke nije uspjelo.");
                noteSuccessLbl.setVisible(false);
            }}}

    public void closeNote(ActionEvent ev) {
        notePane.setVisible(false);
        noteSuccessLbl.setVisible(false);
        noteErrorLbl.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.helloLbl.setText("Dobrodošli," + "\n" + Login.LoggedInUser.getFirstName() + " " + Login.LoggedInUser.getLastName());
        this.evNameTblCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.evDescriptionTblCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.evPlaceTblCol.setCellValueFactory(new PropertyValueFactory<>("place"));
        this.evTimeTblCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.populateTableView();
        evTableView.setEditable(true);
        this.evNameTblCol.setEditable(true);
        this.evNameTblCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.evDescriptionTblCol.setEditable(true);
        this.evDescriptionTblCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.evPlaceTblCol.setEditable(true);
        this.evPlaceTblCol.setCellFactory(TextFieldTableCell.forTableColumn());
        this.populateFavoritesTableView();
        this.nameTblClmn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.phoneTblClmn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        this.emailTblClmn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.populateTableView();
        this.favContactsTblView.setEditable(true);
        this.nameTblClmn.setEditable(true);
        this.nameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lastnameTblClmn.setEditable(true);
        this.lastnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.phoneTblClmn.setEditable(true);
        this.phoneTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailTblClmn.setEditable(true);
        this.emailTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.unreadTitleTblCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        this.unreadTextTblCol.setCellValueFactory(new PropertyValueFactory<>("text"));
        this.populateUnreadNotesTableView();
        try {
            this.reminderLbl.setText("Podsjetnik" + "\n"+ Event.reminder().getName() + "\n" + Event.reminder().getTime()+ "\n" + Event.reminder().getPlace());
        } catch (Exception e) {
           reminderLbl.setText("Nemate podsjetnika.");
            e.printStackTrace();
            System.out.println("Nema novih podsjetnika.");
        }
        ObservableList<Integer> elements = FXCollections.observableArrayList(
                new Integer(7),
                new Integer(8),
                new Integer(9),
                new Integer(10),
                new Integer(11),
                new Integer(12),
                new Integer(13),
                new Integer(14),
                new Integer(15),
                new Integer(16),
                new Integer(17),
                new Integer(18),
                new Integer(19),
                new Integer(20),
                new Integer(21),
                new Integer(22),
                new Integer(23),
                new Integer(24)
        );
        ObservableList<Integer> elementss = FXCollections.observableArrayList(
                new Integer(0),
                new Integer(10),
                new Integer(20),
                new Integer(30),
                new Integer(40),
                new Integer(50)
        );
        minutesCombobox.setItems(elementss);
        minutesCombobox.getSelectionModel().selectFirst();
        hoursCombobox.setItems(elements);
        hoursCombobox.getSelectionModel().selectFirst();
    }
    @FXML
    public void editEventName (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
        Event e = evt.getRowValue();
        e.setName(evt.getNewValue());
        e.update();
    }
    @FXML
    public void editEventDescription (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
        Event e  = evt.getRowValue();
        e.setDescription(evt.getNewValue());
        e.update();
    }
    @FXML
    public void editEventPlace (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
        Event e = evt.getRowValue();
        e.setPlace(evt.getNewValue());
        e.update();
    }
    private void populateTableView() { try {
        this.evTableView.getItems().setAll((Collection<?extends Event>) Event.upcomingEvents(Event.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    private void populateFavoritesTableView() { try {
        this.favContactsTblView.getItems().setAll((Collection<?extends Contact>) Contact.favorites(Contact.class));
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
    public void editContactFirstName (TableColumn.CellEditEvent<Contact, String> evt) throws Exception {
        Contact c = evt.getRowValue();
        c.setFirstName(evt.getNewValue());
        c.update();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void editContactLastName (TableColumn.CellEditEvent<Contact, String> evt) throws Exception {
        Contact c  = evt.getRowValue();
        c.setLastName(evt.getNewValue());
        c.update();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void editContactPhoneNumber (TableColumn.CellEditEvent<Contact, String> evt) throws Exception {
        Contact c = evt.getRowValue();
        c.setTelefon(evt.getNewValue());
        c.update();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void editContactEmail(TableColumn.CellEditEvent<Contact, String> evt) throws Exception {
        Contact c  = evt.getRowValue();
        c.setEmail(evt.getNewValue());
        c.update();
        this.populateTableView();
        this.populateFavoritesTableView();
    }

    @FXML
    public void markAsRead (ActionEvent ev) throws Exception {
        Note n = unreadNotesTableView.getSelectionModel().getSelectedItem();
        n.markAsRead();
        this.populateUnreadNotesTableView();
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
        nameTxt.setText(Login.LoggedInUser.getFirstName());
        lastNameTxt.setText(Login.LoggedInUser.getLastName());
        emailTxt.setText(Login.LoggedInUser.getEmail());
        passwordTxt.setText(Login.LoggedInUser.getPassword());
        nameTxt.setVisible(true);
        lastNameTxt.setVisible(true);
        emailTxt.setVisible(true);
        passwordTxt.setVisible(true);
        updateData1.setVisible(true);
        updateData.setVisible(false);
    }

    @FXML
    public void updateData1 (ActionEvent ev){
        Login.LoggedInUser.setFirstName(this.nameTxt.getText());
        Login.LoggedInUser.setLastName(this.lastNameTxt.getText());
        Login.LoggedInUser.setEmail(this.emailTxt.getText());
        Login.LoggedInUser.setPassword(this.passwordTxt.getText());
        Login.LoggedInUser.setRole("USER");
        nameTxt.setVisible(false);
        lastNameTxt.setVisible(false);
        emailTxt.setVisible(false);
        passwordTxt.setVisible(false);
        updateData1.setVisible(false);
        updateData.setVisible(true);
        try {
            Login.LoggedInUser.update();
        } catch (Exception e) {
            e.printStackTrace();
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
    public void goToEvents (ActionEvent ev){
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Events.fxml",
                    "Događaji", 1100, 700, 250, 100);
        } catch (IOException e) {
            System.out.println("Došlo je do pogreške.");
        }
    }
    @FXML
    public void goToNotes (ActionEvent ev){
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Notes.fxml",
                    "Poruke", 1100, 700, 250, 100);
        } catch (IOException e) {
            System.out.println("Došlo je do pogreške.");
        }
    }


}