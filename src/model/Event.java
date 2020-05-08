package model;

import java.util.Date;

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


}
