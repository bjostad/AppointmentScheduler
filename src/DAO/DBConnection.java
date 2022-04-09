package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName="client_schedule";
    private static final String dbURL ="jdbc:mysql://localhost:3306/"+databaseName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection dbConnection;

    /**
     * Connect to mySQL database
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        try{
            dbConnection=(Connection) DriverManager.getConnection(dbURL,username,password);
            System.out.println(dbConnection);
        } catch (SQLException e) {
            // TODO error handling
            System.out.println("DB connection issue: "+ e);
        }
    }

    public static Connection getConnection(){
        return dbConnection;
    }

    /**
     * Close mySQL database connection
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        try{
            dbConnection.close();
            System.out.println("Connection Closed");
        } catch (SQLException e) {
            //TODO error handling
            System.out.println("DB closure issue: "+ e);
        }
    }
}
