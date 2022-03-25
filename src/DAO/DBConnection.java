package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String databaseName="client_schedule";
    private static final String DB_URL="jdbc:mysql://localhost:3306/"+databaseName;
    private static final String username="sqlUser";
    private static final String password="Passw0rd!";
    private static final String driver = "com.mysql.jdbc.Driver";
    static Connection conn;

    /**
     * Connect to mySQL database
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception {
        conn=(Connection) DriverManager.getConnection(DB_URL,username,password);
    }

    /**
     * close mySQL database connection
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn.close();
    }
}
