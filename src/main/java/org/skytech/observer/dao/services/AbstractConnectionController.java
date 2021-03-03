package org.skytech.observer.dao.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractConnectionController <E, K> {
    public ConnectionPool connectionPool;
    public Connection connection;

    public AbstractConnectionController() throws SQLException {
        connectionPool = ConnectionPool.getConnectionPool();
        connection = connectionPool.getConnection();
    }

    public void returnConnectionInPool(){
        connectionPool.returnConnection(connection);
    }

    public PreparedStatement getPreparedStatement(String sql){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    public void closePreparedStatement(PreparedStatement ps){
        if(ps != null){
            try {
                ps.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
