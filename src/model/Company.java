package model;

public class Company extends Table {
    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String name;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String phone;

    public String getName() { return name; }

    @Override public String toString() { return this.getName(); }

    public void setName(String name) { this.name = name; }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
