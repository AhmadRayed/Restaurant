package Application.Resources;

import java.sql.*;

public class MySqlConnection {
    private static final String
            DATABASE_URL = "jdbc:mariadb://localhost:3306/restaurant_DB?useSSL=false",
//            DATABASE_URL = "jdbc:mariadb:restaurant_DB.sql",
            DATABASE_USERNAME = "rayed_restaurant",
            DATABASE_PASSWORD = "0198";

    private Connection connection;

    private static MySqlConnection mySqlConnection;

    private MySqlConnection () {
        try {
//            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
    }

    public static MySqlConnection MakeConnection () {
        if (mySqlConnection == null)
            mySqlConnection = new MySqlConnection();
        return mySqlConnection;
    }

    public Connection getConnection () {
        try {
            if (connection.isClosed())
                    connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return connection;
    }

    public ResultSet getResultOfQuery (String Query) {
        ResultSet resultSet = null;
        try {
            Statement statement = getConnection().createStatement();
            resultSet = statement.executeQuery(Query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }
        return resultSet;
    }

    public void executeQuery (String Query, String error) {
        try {
            getConnection().createStatement().executeQuery(Query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(error);
        }
    }
}
