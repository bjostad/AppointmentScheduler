package DAO;

import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl {

    public static User getUser(String loginUsername) throws SQLException, Exception {
        User foundUser;
        String getUserSQLStatement = "SELECT * FROM users WHERE User_Name = '"+ loginUsername+"'";
        Query.sendQuery(getUserSQLStatement);
        ResultSet results = Query.getResults();
        while(results.next()){
            int id = results.getInt("User_ID");
            String username = results.getString("User_Name");
            String password = results.getString("Password");
            foundUser= new User(id, username, password);
            DBConnection.closeConnection();
            return foundUser;
        }
        return null;
    }
}
