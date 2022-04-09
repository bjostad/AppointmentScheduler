package DAO;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {

    public static User getUser(String loginUsername) throws SQLException, Exception {
        User foundUser;
        String getUserSQL = "SELECT * FROM users WHERE User_Name = ?";
        PreparedStatement pStatement = DBConnection.getConnection().prepareStatement(getUserSQL);
        pStatement.setString(1,loginUsername);
        ResultSet results = pStatement.executeQuery();
        while(results.next()){
            int id = results.getInt("User_ID");
            String username = results.getString("User_Name");
            String password = results.getString("Password");
            foundUser= new User(id, username, password);
            return foundUser;
        }
        return null;
    }
}
