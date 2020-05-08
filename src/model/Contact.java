package model;

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

    @ForeignKey(table = "Company", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int companyFK;

    @ForeignKey (table = "User", attribute = "id")
    @Entity(type = "INTEGER", size = 32)
    int userFK;

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

    public void setTelefon(String password) {
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

}
