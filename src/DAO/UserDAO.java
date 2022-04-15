package DAO;

import javafx.collections.ObservableList;
import model.User;

/**
 * @author BJ Bjostad
 */
public interface UserDAO {
    public ObservableList<User> getAllUsers();
    public User getUser(String loginUsername);
    public int getUserIDFromUserName(String userName);
}
