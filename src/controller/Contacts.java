package controller;
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
import javafx.stage.FileChooser;
import main.Main;
import model.Company;
import model.Contact;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class Contacts implements Initializable {
    @FXML
    ImageView contactImage;
    @FXML
    Button backBtn;
    @FXML
    Button logoutBtn;
    @FXML
    Button newUserBtn;
    @FXML
    Button addToFavoritesBtn;
    @FXML
    Button removeFromFavoritesBtn;
    @FXML
    Button favoritesBtn;
    @FXML
    Button allBtn;
    @FXML
    Label allLbl;
    @FXML
    Button deleteBtn;
    @FXML
    Button deleteBtn1;
    @FXML
    TextField nameTxt;
    @FXML
    TextField lastnameTxt;
    @FXML
    TextField emailTxt;
    @FXML
    TextField telephoneTxt;
    @FXML
    Button addBtn;
    @FXML
    CheckBox favoriteChck;
    @FXML
    Label errorLbl;
    @FXML
    Label successLbl;
    @FXML
    Label nameLbl;
    @FXML
    Label lastnameLbl;
    @FXML
    Label telephoneLbl;
    @FXML
    Label emailLbl;
    @FXML
    Button closeBtn;
    @FXML
    TableView< Contact> tableView;
    @FXML
    TableColumn < Contact, String> nameTblClmn;
    @FXML
    TableColumn < Contact, String> lastnameTblClmn;
    @FXML
    TableColumn < Contact, String> phoneTblClmn;
    @FXML
    TableColumn < Contact, String> emailTblClmn;
    @FXML
    TableColumn < Contact, String> favoritesTblClmn;
    @FXML
    TableColumn <Contact, ImageView> imageTblCol;
    @FXML
    TableView <Contact> favoritesTblView;
    @FXML
    TableColumn < Contact, String> nameTblClmn1;
    @FXML
    TableColumn < Contact, String> lastnameTblClmn1;
    @FXML
    TableColumn < Contact, String> phoneTblClmn1;
    @FXML
    TableColumn < Contact, String> emailTblClmn1;
    @FXML
    TableColumn <Contact, ImageView> imageTblCol1;

    BufferedImage buffImage;
    Image initialImage;
    @FXML
    ComboBox <Company> companies;
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
    public void goToCompanies(ActionEvent ev) {
        try {
            Main.showWindow(
                    getClass(),
                    "/view/Companies.fxml",
                    "Tvrtke",
                    800,
                    600, 300, 50);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Došlo je do pogreške");
        }
    }
    @FXML
    public void goToFavorites(ActionEvent ev) {
        tableView.setVisible(false);
        favoritesTblView.setVisible(true);
        addToFavoritesBtn.setVisible(false);
        removeFromFavoritesBtn.setVisible(true);
        favoritesBtn.setVisible(false);;
        allBtn.setVisible(true);
        allLbl.setText("Kontakti s oznakom favorit");
        deleteBtn.setVisible(false);
        deleteBtn1.setVisible(true);
    }
    @FXML
    public void showAll(ActionEvent ev) {
        tableView.setVisible(true);
        favoritesTblView.setVisible(false);
        addToFavoritesBtn.setVisible(true);
        removeFromFavoritesBtn.setVisible(false);
        favoritesBtn.setVisible(true);
        allBtn.setVisible(false);
        allLbl.setText("Vaši kontakti");
        deleteBtn.setVisible(true);
        deleteBtn1.setVisible(false);
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
    public void addNew(javafx.event.ActionEvent actionEvent){
        newUserBtn.setVisible(true);
        nameTxt.setVisible(true);
        lastnameTxt.setVisible(true);
        emailTxt.setVisible(true);
        telephoneTxt.setVisible(true);
        addBtn.setVisible(true);
        favoriteChck.setVisible(true);
        errorLbl.setVisible(false);
        successLbl.setVisible(false);
        nameLbl.setVisible(true);
        lastnameLbl.setVisible(true);
        telephoneLbl.setVisible(true);
        emailLbl.setVisible(true);
        closeBtn.setVisible(true);
        contactImage.setVisible(true);
    }
    public void addContactToDatabase(javafx.event.ActionEvent actionEvent) throws SQLException {
        String email = this.emailTxt.getText().toString();
        String telephone = this.telephoneTxt.getText().toString();
        String ime = this.nameTxt.getText().toString();
        String prezime = this.lastnameTxt.getText().toString();
        int userFK = Login.LoggedInUser.getId();
        if (ime.equals("") || prezime.equals ("") || (telephone.equals("") && email.equals(""))){
            errorLbl.setText("Niste dodali ni broj telefona ni e-mail.");
            errorLbl.setVisible(true);
            successLbl.setVisible(false);
        }
        else {
        Contact c = new Contact();
        if (favoriteChck.isSelected()) {
            c.setFirstName(this.nameTxt.getText());
            c.setLastName(this.lastnameTxt.getText());
            c.setEmail(this.emailTxt.getText());
            c.setTelefon(this.telephoneTxt.getText());
            c.setUserFK(Login.LoggedInUser.getId());
            c.setCompanyFK(1);
            c.setFavoriteYN("DA");
            SerialBlob image = new SerialBlob(imageToByte(this.buffImage));
            c.setContactImage(image);
        } else {
            c.setFirstName(this.nameTxt.getText());
            c.setLastName(this.lastnameTxt.getText());
            c.setEmail(this.emailTxt.getText());
            c.setTelefon(this.telephoneTxt.getText());
            c.setUserFK(Login.LoggedInUser.getId());
            c.setCompanyFK(1);
            c.setFavoriteYN("NE");
            SerialBlob image = new SerialBlob(imageToByte(this.buffImage));
            c.setContactImage(image);
        }

        errorLbl.setVisible(false);
        successLbl.setVisible(true);
        nameTxt.setText("");
        lastnameTxt.setText("");
        telephoneTxt.setText("");
        emailTxt.setText("");
        this.contactImage.setImage(this.initialImage);
        try {
            c.save();
            this.populateTableView();
            this.populateFavoritesTableView();
        } catch (Exception e) {
            e.printStackTrace();
            errorLbl.setText("Došlo je do pogreške.");
            errorLbl.setVisible(true);
            successLbl.setVisible(false);
        }}}

        public void close(javafx.event.ActionEvent actionEvent){
            newUserBtn.setVisible(true);
            nameTxt.setVisible(false);
            lastnameTxt.setVisible(false);
            emailTxt.setVisible(false);
            telephoneTxt.setVisible(false);
            addBtn.setVisible(false);
            favoriteChck.setVisible(false);
            errorLbl.setVisible(false);
            successLbl.setVisible(false);
            nameLbl.setVisible(false);
            lastnameLbl.setVisible(false);
            telephoneLbl.setVisible(false);
            emailLbl.setVisible(false);
            closeBtn.setVisible(false);
            contactImage.setVisible(false);
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nameTblClmn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastnameTblClmn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.phoneTblClmn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        this.emailTblClmn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.favoritesTblClmn.setCellValueFactory(new PropertyValueFactory<>("favoriteYN"));
        this.imageTblCol.setCellValueFactory(new PropertyValueFactory<>("contactImage"));
        imageTblCol.setMinWidth(100);

        this.populateTableView();
        this.tableView.setEditable(true);
        this.nameTblClmn.setEditable(true);
        this.nameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lastnameTblClmn.setEditable(true);
        this.lastnameTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.phoneTblClmn.setEditable(true);
        this.phoneTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailTblClmn.setEditable(true);
        this.emailTblClmn.setCellFactory(TextFieldTableCell.forTableColumn());

        this.nameTblClmn1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastnameTblClmn1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.phoneTblClmn1.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        this.emailTblClmn1.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.imageTblCol1.setCellValueFactory(new PropertyValueFactory<>("contactImage"));
        imageTblCol1.setPrefWidth(60);
        this.populateFavoritesTableView();
        this.favoritesTblView.setEditable(true);
        this.nameTblClmn1.setEditable(true);
        this.nameTblClmn1.setCellFactory(TextFieldTableCell.forTableColumn());
        this.lastnameTblClmn1.setEditable(true);
        this.lastnameTblClmn1.setCellFactory(TextFieldTableCell.forTableColumn());
        this.phoneTblClmn1.setEditable(true);
        this.phoneTblClmn1.setCellFactory(TextFieldTableCell.forTableColumn());
        this.emailTblClmn1.setEditable(true);
        this.emailTblClmn1.setCellFactory(TextFieldTableCell.forTableColumn());
    }


    private void populateTableView() {
        try {
        this.tableView.getItems().setAll((Collection<?extends Contact>) Contact.usersList(Contact.class));
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Dohvaćanje podataka nije uspjelo.");
    }
    }
    private void populateFavoritesTableView() {
        try {
            this.favoritesTblView.getItems().setAll((Collection<?extends Contact>) Contact.favorites(Contact.class));
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
    public void deleteContact (ActionEvent ev) throws Exception {
        Contact c = tableView.getSelectionModel().getSelectedItem();
        c.delete();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void deleteContact1 (ActionEvent ev) throws Exception {
        Contact c = favoritesTblView.getSelectionModel().getSelectedItem();
        c.delete();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void addToFavorites (ActionEvent ev) throws Exception {
        Contact c = tableView.getSelectionModel().getSelectedItem();
        c.markAsFavorite();
        this.populateTableView();
        this.populateFavoritesTableView();
    }
    @FXML
    public void removeFromFavorites (ActionEvent ev) throws Exception {
        Contact c = favoritesTblView.getSelectionModel().getSelectedItem();
        c.moveFromFavorites();
        this.populateTableView();
        this.populateFavoritesTableView();
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
}


