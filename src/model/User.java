package model;

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


}

