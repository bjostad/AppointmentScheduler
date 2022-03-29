package model;

/**
 * @author BJ Bjostad
 */
public class Contact {
    public int ID;
    public String name;
    public String email;

    private Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    private int getID() {
        return ID;
    }

    private String getName() {
        return name;
    }

    private String getEmail() {
        return email;
    }
}
