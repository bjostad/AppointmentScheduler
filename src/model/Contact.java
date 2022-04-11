package model;

/**
 * @author BJ Bjostad
 */
public class Contact {
    public int ID;
    public String name;
    public String email;

    public Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
