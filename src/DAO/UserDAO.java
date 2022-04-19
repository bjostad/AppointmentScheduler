package DAO;

import javafx.collections.ObservableList;
import model.User;

/**
 * @author BJ Bjostad
 */
public interface UserDAO {
    /**
     * get User from data base matching provided username
     * check for matching password if User is found
     * @param loginUsername username of login user
     * @return User
     */
    User getUser(String loginUsername);

    /**
     * get all Users from database
     * @return ObservableList<User>
     */
    ObservableList<User> getAllUsers();

    /**
     * get user ID based on provided username
     * @param userName username
     * @return int userID
     */int getUserIDFromUserName(String userName);
}
