package org.skytech.observer.dao.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    Connection connection;
    static ConnectionPool connectionPool;

    private ConnectionPool(){
        connectionPool = this;
    }

    public static ConnectionPool getConnectionPool(){
        if(connectionPool != null){
            return connectionPool;
        }else {
            return new ConnectionPool();
        }
    }

    public Connection getConnection() throws SQLException {
        if(connection != null){
            return connection;
        } else {
            try {
                Class.forName("org.sqlite.JDBC");
//                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\O&K\\IdeaProjects\\Observer\\testneurolinkdata");
                connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Sasha\\IdeaProjects\\Observer\\neurolinkdata.sqlite");
                return connection;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throw new SQLException();
        }
    }

    public void returnConnection(Connection connection) {
        this.connection = connection;
    }
}
