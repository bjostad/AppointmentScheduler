package model;

/**
 * @author BJ Bjostad
 */
public class User {
    private String username;
    private String password;

    /**
     * User constrcuctor
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * password getter
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * password setter
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * username getter
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * username setter
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
}