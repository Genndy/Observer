package org.skytech.observer.api.services;

import org.skytech.observer.console.PerceptronsManager;
import org.skytech.observer.dao.services.ConnectionPool;

import java.io.IOException;
import java.sql.*;

public class ThreadController extends Thread { // Лишний класс?
    ThreadController(String name){
        super(name);
    }
    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        // Connection Start i guess...
        try {
            PerceptronsManager perceptronManager = new PerceptronsManager();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }

    public void test() throws IOException, SQLException {
        System.out.println("Тест соединения");
        ConnectionPool pool = ConnectionPool.getConnectionPool();
        Connection connection = pool.getConnection();
        Statement state = connection.createStatement();

        System.out.println("Создаём таблицу");
        state.execute("CREATE TABLE IF NOT EXISTS test_table(text varchar(100));");

        System.out.println("Создаём вводим данные");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO test_table(text) VALUES('test');");
        preparedStatement.execute();

        System.out.println("достаём данные");

        preparedStatement = connection.prepareStatement("SELECT * FROM test_table");
        preparedStatement.execute();
        ResultSet res = preparedStatement.getResultSet();

        System.out.println("Текст в базе данных: " + res.getString(1));
        System.out.println("Дропаем табличку и отключаем соединение");
        preparedStatement.close();
        preparedStatement = connection.prepareStatement("DROP TABLE test_table");
        preparedStatement.execute();
        state.close();
        connection.close();
    }
}
/*
Итак, что мы выяснили:
Connection Pool вроде как работает
PreparedStatement тоже работает нормально.
PreparedStatement работает с табличками (типа курсор контекста) - и соответственно - на одну табличку может
зариться лишь один PreparedStatement. По этой причине надо закрывать соединения Statement после каждой операции.

* */
