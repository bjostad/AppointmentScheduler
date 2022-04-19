package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String loginUsername) {
        String getUserSQL = "SELECT * FROM users WHERE User_Name = ?";
        try{
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getUserSQL);
            pStatement.setString(1,loginUsername);
            ResultSet results = pStatement.executeQuery();
            while(results.next()){
                int id = results.getInt("User_ID");
                String username = results.getString("User_Name");
                String password = results.getString("Password");
                return new User(id, username, password);
            }

        } catch (Exception e){
            //TODO Error handling
            System.out.println(e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObservableList<User> getAllUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        String getAllUsersSQL = "SELECT * FROM USERS";
        try{
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getAllUsersSQL);
            ResultSet results = pStatement.executeQuery();
            while(results.next()){
                int userID = results.getInt("User_ID");
                String username = results.getString("User_Name");
                users.add(new User(userID,username,""));
            }
            return users;
        } catch (SQLException e){
            //TODO error handling
            System.out.println(e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getUserIDFromUserName(String userName){
        String getUserIDFromUserNameSQL = "SELECT USER_ID FROM USERS WHERE USER_NAME = ?";
        try{
            PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getUserIDFromUserNameSQL);
            pStatement.setString(1,userName);
            ResultSet results = pStatement.executeQuery();
            while(results.next()) {
                return results.getInt(1);
            }
        } catch (Exception e){
            //TODO error handling
            System.out.println(e);
        }
        return -1;
    }
}
