package model;

public class Note extends Table {

    @Entity(type = "INTEGER", size = 32, primary = true)
    int id;

    @Entity(type = "VARCHAR", size = 50, primary = false, isnull = false)
    String title;

    @Entity(type = "TEXT", size = 500, primary = false, isnull = false)
    String text;

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
}
