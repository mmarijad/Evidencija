package model;

import controller.Login;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event extends Table {

    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String name;

    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String description;

    @Entity(type = "DATETIME")
    Date time;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String place;

    @ForeignKey(table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;

    @Entity(type = "VARCHAR", size = 2, primary = false, isnull = false)
    String remindMe;

    public String getRemindMe() {
        return remindMe;
    }

    public void setRemindMe(String remindMe) {
        this.remindMe = remindMe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() { return time; }

    public void setTime(Date time) { this.time = time; }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public User getUserFK() throws Exception { return (User)Table.get(User.class, userFK); }

    public void setUserFK(int userFK) {
        this.userFK = userFK;
    }

    public static List<?> upcomingEvents(Class cls) throws Exception {
        int fk = Login.LoggedInUser.getId();
        String SQL = "SELECT * FROM `event` WHERE userFK = "+fk+" AND time >= CURDATE() ORDER BY time ASC  LIMIT 3;";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        List<Object> upcomingEvents = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            upcomingEvents.add(obj);
        }
        return upcomingEvents;
    }

    public static List<?> upcomingEvents2(Class cls) throws Exception {
        int fk = Login.LoggedInUser.getId();
        String SQL = "SELECT * FROM `event` WHERE userFK = "+fk+" AND time >= CURDATE()";
        Statement stmt = Database.CONNECTION.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        List<Object> upcomingEvents2 = new ArrayList<>();
        while(rs.next()){
            Object obj = Class.forName(cls.getName()).newInstance();
            Class<?> otherCls = obj.getClass();
            for (Field f : otherCls.getDeclaredFields()){
                f.set(obj, rs.getObject(f.getName()));
            }
            upcomingEvents2.add(obj);
        }
        return upcomingEvents2;
    }

    public void createReminder(){
        Field id = null;
        try {
            id = this.getClass().getDeclaredField("id");
            PreparedStatement stmt = Database.CONNECTION.prepareStatement("UPDATE event SET remindMe = 'DA' WHERE id=?");
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

    public static Event reminder() throws Exception {
        String sql = "SELECT id FROM event WHERE remindMe = 'DA' AND time >= CURDATE() ORDER BY time ASC LIMIT 1;" ;
        PreparedStatement query = Database.CONNECTION.prepareStatement(sql);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            return(Event)  Event.get(Event.class, rs.getInt(1));
        }else {
            return null;
        }

    }
}
