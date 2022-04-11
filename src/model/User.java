package model;

/**
 * @author BJ Bjostad
 */
public class User {
    private int ID;
    private String username;
    private String password;

    /**
     * User constrcuctor
     * @param username
     * @param password
     */
    public User(int id,String username, String password) {
        this.ID = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return this.username;
    }

    /**
     * password getter
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * username getter
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * userID getter
     * @return
     */
    public int getID() {
        return ID;
    }

}