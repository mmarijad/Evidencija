package model;

import controller.Login;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.sql.rowset.serial.SerialBlob;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Contact extends Table {
    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String firstName;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String lastName;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String email;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String telefon;

    @Entity(type = "VARCHAR", size = 3, primary = false, isnull = false)
    String favoriteYN;

    @ForeignKey(table = "Company", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int companyFK;

    @ForeignKey (table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;
    @Entity(type="BLOB", size=10000000, isnull = false)
    SerialBlob contactImage;

    public ImageView getContactImage() {
        try{
            return new ImageView(new Image(contactImage.getBinaryStream()));
        } catch (Exception e) {
            return null;
        }
    }
    public void setContactImage(SerialBlob contactImage) {
        this.contactImage = contactImage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Company getCompanyFK() throws Exception { return (Company)Table.get(Company.class, companyFK); }

    public void setCompanyFK(int companyFK) {
        this.companyFK = companyFK;
    }

    public User getUserFK() throws Exception { return (User)Table.get(User.class, userFK); }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public String getFavoriteYN() { return favoriteYN; }

    public void setFavoriteYN(String favoriteYN) {
        this.favoriteYN = favoriteYN;
    }

    public void markAsFavorite () {
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE contact SET favoriteYN = 'DA' WHERE id=?");
            stmt.setObject(1, id.get(this));
            stmt.executeUpdate();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
        }

    public void moveFromFavorites(){
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE contact SET favoriteYN = 'NE' WHERE id=?");
            stmt.setObject(1, id.get(this));
            stmt.executeUpdate();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }

    public static List<?> favorites(Class cls) throws Exception {
        int fk = Login.LoggedInUser.getId();
        String SQL = "SELECT * FROM contact WHERE favoriteYN = 'DA' AND userFK = " + fk;
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        List<Object> favorites = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                if (SerialBlob.class.isAssignableFrom(f.getType())) {
                    if (rs.getBlob(f.getName()) != null)
                        f.set(obj, new SerialBlob(rs.getBlob(f.getName())));
                } else {
                    f.set(obj, rs.getObject(f.getName()));
                }
            }
            favorites.add(obj);
        }
        return favorites;
    }

}
