package model;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class User extends Table {
    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String firstName;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String lastName;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String email;

    @Entity(type = "VARCHAR", size = 150, primary = false, isnull = false)
    String password;

    @Entity(type = "VARCHAR", size = 5, primary = false, isnull = true)
    String role;

    public int getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static User login(String email, String password) throws Exception {
        String sql = "SELECT id FROM user WHERE email =? AND password = ?";
        PreparedStatement query = Database.CONNECTION.prepareStatement(sql);
        query.setString(1, email);
        query.setString(2, password);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
          return(User)  User.get(User.class, rs.getInt(1));
        }else {
            return null;
        }
    }
    public static List<?> admins(Class cls) throws Exception {
        String SQL = "SELECT * FROM user WHERE role = 'ADMIN'";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> admins = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            admins.add(obj);
        }
        return admins;
    }
    public static List<?> users(Class cls) throws Exception {
        String SQL = "SELECT * FROM user WHERE role = 'USER'";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);

        List<Object> users = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            users.add(obj);
        }
        return users;
    }
    public void makeAdmin () {
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE user SET role = 'ADMIN' WHERE id=?");
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
    public void moveFromAdmin () {
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE user SET role = 'USER' WHERE id=?");
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
}

