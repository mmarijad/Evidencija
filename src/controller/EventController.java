package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Main;
import model.Event;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class EventController implements Initializable {
    @FXML
    Button backToMainBtn;
    @FXML
    Button logoutBtn;
    @FXML
    Button newEventBtn;
    @FXML
    Button deleteBtn;
    @FXML
    Button deleteBtn1;
    @FXML
    Button reminderBtn;
    @FXML
    Button saveBtn;
    @FXML
    Button closeBtn;
    @FXML
    Label eventLbl;
    @FXML
    Label errorLbl;
    @FXML
    Label successLbl;
    @FXML
    Label nameLbl;
    @FXML
    Label descriptionLbl;
    @FXML
    Label dateLbl;
    @FXML
    Label placeLbl;
    @FXML
    Label lbl;
    @FXML
    TextField nameTxt;
    @FXML
    TextField descriptionTxt;
    @FXML
    TextField placeTxt;
    @FXML
    Label reminderLbl;
    @FXML
    private ComboBox<Integer> hoursComboBox ;
    @FXML
    private ComboBox<Integer> minutesComboBox;

    @FXML
    DatePicker datePicker = null;
    @FXML
    TableView<Event> tableView;
    @FXML
    TableColumn < Event, String> nameTblClmn;
    @FXML
    TableColumn < Event, String> descriptionTblClmn;
    @FXML
    TableColumn < Event, String> placeTblClmn;
    @FXML
    TableColumn < Event, String> timeTblClmn;
    @FXML
    TableView<Event> upcomingTableView;
    @FXML
    TableColumn < Event, String> UnameTblClmn;
    @FXML
    TableColumn < Event, String> UdescriptionTblClmn;
    @FXML
    TableColumn < Event, String> UplaceTblClmn;
    @FXML
    TableColumn < Event, String> UtimeTblClmn;
    @FXML
    Button showAllBtn;
    @FXML
    Button showUpcomingBtn;
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
        datePicker.setValue(LocalDate.now());
        saveBtn.setVisible(true);
        closeBtn.setVisible(true);
        eventLbl.setText("Novi događaj");
        eventLbl.setVisible(true);
        nameLbl.setVisible(true);
        descriptionLbl.setVisible(true);
        placeLbl.setVisible(true);
        dateLbl.setVisible(true);
        lbl.setVisible(true);
        nameTxt.setVisible(true);
        descriptionTxt.setVisible(true);
        placeTxt.setVisible(true);
        datePicker.setVisible(true);
        hoursComboBox.setVisible(true);
        minutesComboBox.setVisible(true);
        newEventBtn.setVisible(false);

    }

    public void save(ActionEvent ev){
        String name = this.nameTxt.getText().toString();
        String description = this.descriptionTxt.getText().toString();
        String place = this.placeTxt.getText().toString();
        LocalDate ld = datePicker.getValue();
        Calendar c =  Calendar.getInstance();
        int hours = hoursComboBox.getValue();
        System.out.println(hours);
        int minutes = minutesComboBox.getValue();
        int seconds = 0;
        System.out.println(minutes);
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), hours, minutes, seconds);
        Date date = c.getTime();

        int userFK = Login.LoggedInUser.getId();
        if (name.equals("") || place.equals("")){
           errorLbl.setText ("Niste popunili tražena polja.");
            successLbl.setVisible(false);
           errorLbl.setVisible(true);
        } else {
        Event e = new Event();
            e.setName(this.nameTxt.getText());
            e.setDescription(this.descriptionTxt.getText());
            e.setPlace(this.placeTxt.getText());
            e.setTime(date);
            e.setUserFK(Login.LoggedInUser.getId());
            e.setRemindMe("NE");
            nameTxt.setText("");
            descriptionTxt.setText("");
            placeTxt.setText("");
            successLbl.setVisible(true);
            errorLbl.setVisible(false);
            saveBtn.setVisible(false);
            eventLbl.setVisible(false);
            nameLbl.setVisible(false);
            descriptionLbl.setVisible(false);
            placeLbl.setVisible(false);
            dateLbl.setVisible(false);
            nameTxt.setVisible(false);
            descriptionTxt.setVisible(false);
            placeTxt.setVisible(false);
            datePicker.setVisible(false);
            newEventBtn.setVisible(true);
            successLbl.setVisible(false);
            closeBtn.setVisible(false);
            lbl.setVisible(false);
            hoursComboBox.setVisible(false);
            minutesComboBox.setVisible(false);
        try {
            e.save();
            this.populateTableView();
            this.populateUpcomingTableView();
        } catch (Exception ex) {
            ex.printStackTrace();
           errorLbl.setText("Došlo je do pogreške: dodavanje novog događaja nije uspjelo.");
           successLbl.setVisible(false);
        }}}

    public void close(ActionEvent ev){
        nameTxt.setText("");
        descriptionTxt.setText("");
        placeTxt.setText("");
        errorLbl.setVisible(false);
        saveBtn.setVisible(false);
        closeBtn.setVisible(false);
        eventLbl.setVisible(false);
        nameLbl.setVisible(false);
        descriptionLbl.setVisible(false);
        placeLbl.setVisible(false);
        dateLbl.setVisible(false);
        lbl.setVisible(false);
        nameTxt.setVisible(false);
        descriptionTxt.setVisible(false);
        placeTxt.setVisible(false);
        datePicker.setVisible(false);
        hoursComboBox.setVisible(false);
        minutesComboBox.setVisible(false);
        closeBtn.setVisible(false);
        newEventBtn.setVisible(true);
        successLbl.setVisible(false);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nameTblClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.descriptionTblClmn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.placeTblClmn.setCellValueFactory(new PropertyValueFactory<>("place"));
        this.timeTblClmn.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.populateTableView();
        this.tableView.setEditable(true);
        this.nameTblClmn.setEditable(true);
        this.descriptionTblClmn.setEditable(true);
        this.placeTblClmn.setEditable(true);
        this.nameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.descriptionTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.placeTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.UnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.UdescriptionTblClmn.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.UplaceTblClmn.setCellValueFactory(new PropertyValueFactory<>("place"));
        this.UtimeTblClmn.setCellValueFactory(new PropertyValueFactory<>("time"));
        this.populateUpcomingTableView();
        this.upcomingTableView.setEditable(true);
        this.UnameTblClmn.setEditable(true);
        this.UdescriptionTblClmn.setEditable(true);
        this.UplaceTblClmn.setEditable(true);
        this.UnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.UdescriptionTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.UplaceTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());

        try {
            this.reminderLbl.setText("Podsjetnik" + "\n"+ Event.reminder().getName() + "\n" + Event.reminder().getTime()+ "\n" + Event.reminder().getPlace());
        } catch (Exception e) {
            e.printStackTrace();
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
        minutesComboBox.setItems(elementss);
        minutesComboBox.getSelectionModel().selectFirst();
        hoursComboBox.setItems(elements);
        hoursComboBox.getSelectionModel().selectFirst();
    }

    private void populateTableView() { try {
        this.tableView.getItems().setAll((Collection<?extends Event>) Event.usersList(Event.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    private void populateUpcomingTableView()  { try {
        this.upcomingTableView.getItems().setAll((Collection<?extends Event>) Event.upcomingEvents2(Event.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    @FXML
    public void editEventName (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
        Event e = evt.getRowValue();
        e.setName(evt.getNewValue());
        e.update();
        this.populateUpcomingTableView();
        this.populateTableView();
    }
    @FXML
    public void editEventDescription (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
       Event e  = evt.getRowValue();
        e.setDescription(evt.getNewValue());
        e.update();
        this.populateUpcomingTableView();
        this.populateTableView();
    }
    @FXML
    public void editEventPlace (TableColumn.CellEditEvent<Event, String> evt) throws Exception {
        Event e = evt.getRowValue();
        e.setPlace(evt.getNewValue());
        e.update();
        this.populateUpcomingTableView();
        this.populateTableView();
    }

    @FXML
    public void deleteEvent (ActionEvent ev) throws Exception {
        Event e = tableView.getSelectionModel().getSelectedItem();
        e.delete();
        this.populateTableView();
        this.populateUpcomingTableView();
    }
    @FXML
    public void deleteEvent1 (ActionEvent ev) throws Exception {
        Event e = upcomingTableView.getSelectionModel().getSelectedItem();
        e.delete();
        this.populateTableView();
        this.populateUpcomingTableView();
    }
    @FXML
    public void createReminderForEvent (ActionEvent ev) throws Exception {
        Event e = upcomingTableView.getSelectionModel().getSelectedItem();
        e.createReminder();
        this.populateTableView();
    }

    public void showUpcoming(ActionEvent actionEvent) {
        upcomingTableView.setVisible(true);
        tableView.setVisible(false);
        showAllBtn.setVisible(true);
        showUpcomingBtn.setVisible(false);
        reminderBtn.setVisible(true);
        deleteBtn1.setVisible(true);
        deleteBtn.setVisible(false);
        reminderBtn.setDisable(false);
    }

    public void showAll(ActionEvent actionEvent) {
        upcomingTableView.setVisible(false);
        tableView.setVisible(true);
        showAllBtn.setVisible(false);
        showUpcomingBtn.setVisible(true);
        reminderBtn.setVisible(false);
        deleteBtn.setVisible(true);
        deleteBtn1.setVisible(false);
        reminderBtn.setDisable(true);
    }
}

