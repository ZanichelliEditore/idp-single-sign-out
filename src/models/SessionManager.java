package models;

import database.MySqlConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SessionManager {

    private MySqlConfig mySqlConfig;

    public SessionManager(MySqlConfig mySqlConfig){
        super();
        this.mySqlConfig = mySqlConfig;
    }

    public boolean flushSessionByUserId(int userId){

        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://" + this.mySqlConfig.getHost() +
                    "/" + this.mySqlConfig.getDatabase() + "?user="+ this.mySqlConfig.getUsername() + "&password=" +
                    this.mySqlConfig.getPassword());

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM sessions WHERE user_id = ?");
            preparedStatement.setInt(1, userId);

            boolean success = preparedStatement.execute();

            System.out.println("Session deleted for user id: " + userId);

            preparedStatement.close();
            connection.close();

            return success;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error MySql");
            return false;
        }

    }

}
