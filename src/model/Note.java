package model;

import controller.Login;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Note extends Table {

    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String title;

    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String text;

    @Entity(type = "VARCHAR", size = 3, primary = false, isnull = false)
    String readYN;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;

    @ForeignKey(table = "Contact", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int contactFK;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUserFK() throws Exception { return (User)Table.get(User.class, userFK); }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public Contact getContactFK() throws Exception { return (Contact)Table.get(Contact.class, contactFK); }

    public void setContactFK(int contactFK) {
        this.contactFK = contactFK;
    }

    public String getReadYN() {
        return readYN;
    }

    public void setReadYN(String readYN) {
        this.readYN = readYN;
    }

    public void markAsRead () {
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE note SET readYN = 'DA' WHERE id=?");
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

    public void markAsUnread () {
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE note SET readYN = 'NE' WHERE id=?");
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

    public static List<?> unreadNotes(Class cls) throws Exception {
        int fk = Login.LoggedInUser.getId();
        String SQL = "SELECT * FROM note WHERE readYN ='NE' AND userFK = "+fk;
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> usersNotes = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            usersNotes.add(obj);
        }
        return usersNotes;
    }

    public static List<?> allNotes(Class cls) throws Exception {
        int fk = Login.LoggedInUser.getId();
        String SQL = "SELECT * FROM note WHERE userFK = "+fk+";";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> usersNotes = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            usersNotes.add(obj);
        }
        return usersNotes;
    }

    public static Note showUnread() throws Exception {
        int fk = Login.LoggedInUser.getId();
        String sql = "SELECT c.id, n.name FROM note n INNER JOIN contact c ON n.contactFK = c.id WHERE userFK ="+fk+";";
        PreparedStatement query = Database.CONNECTION.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            return(Note)  Note.get(Note.class, rs.getInt(1));
        }else {
            return null;
        }
    }
}
