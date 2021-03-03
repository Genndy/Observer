package org.skytech.observer.perceptron.service;

import java.sql.*;

public class DataBaseManager {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static Connection ConnectToDB() throws ClassNotFoundException, SQLException
    {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\O&K\\IdeaProjects\\TestNeuroLink\\testneurolinkdata");
        return conn;
    }

    public void getStatement(){

    }

    public void closeStatement(){

    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
//        statmt.close();
//        resSet.close();

        System.err.println("Соединения закрыты");
    }
    public static Connection getConnection(){
        return conn;
    }
}
