package DAO;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {

    public static User getUser(String username) throws SQLException, Exception {
        User foundUser;
        String getUserSQLStatement = "SELECT * FROM users WHERE User_Name = '"+ username+"'";
        DBConnection.makeConnection();
        Query.sendQuery(getUserSQLStatement);
        ResultSet results = Query.getResults();
        while(results.next()){
            int foundUserID = results.getInt("User_ID");
            String foundUsername = results.getString("User_Name");
            String foundPassword = results.getString("Password");
            foundUser= new User(foundUserID, foundUsername, foundPassword);
            DBConnection.closeConnection();
            return foundUser;
        }
        DBConnection.closeConnection();
        return null;
    }
}
