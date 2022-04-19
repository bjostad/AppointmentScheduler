package model;

/**
 * @author BJ Bjostad
 */
public class Contact {
    public int ID;
    public String name;
    public String email;

    /**
     * Contact constructor
     * @param ID
     * @param name
     * @param email
     */
    public Contact(int ID, String name, String email) {
        this.ID = ID;
        this.name = name;
        this.email = email;
    }

    /**
     * Override string output to display Contact name
     * @return String
     */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * get Contact ID
     * @return int
     */
    public int getID() {
        return ID;
    }

    /**
     * get Contact name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * get Contact email
     * @return String
     */
    public String getEmail() {
        return email;
    }
}
