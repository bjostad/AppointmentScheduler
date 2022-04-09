package DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DAO.DBConnection.dbConnection;

/**
 * @author BJ Bjostad
 */
public class Query {
    private static Statement statement;
    private static ResultSet results;

    /**
     * Perform proper query based on type
     * @param q
     * @throws SQLException
     */
    public static void sendQuery (String q) throws SQLException {
        try {
             statement = dbConnection.createStatement();
             System.out.println(q.toLowerCase().charAt(0));
             switch (q.toLowerCase().charAt(0)) {
                 case 's' :
                     results = statement.executeQuery(q);
                     break;
                 case 'd' :
                     statement.executeUpdate(q);
                     break;
                 case 'i' :
                     statement.executeUpdate(q);
                     break;
                 case 'u' :
                     statement.executeUpdate(q);
                     break;
             }

        } catch (SQLException e){
            System.out.println("Query Issue: "+e);
        }
    }

    /**
     * obtain results
     * @return ResultSet
     */
    public static ResultSet getResults(){
        return results;
    }
}
