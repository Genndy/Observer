package org.skytech.observer.perceptron.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WeightManager {
    private Connection conn;
    private Statement statmt;


    public void InitiateNewWeights(int weightLenght) throws SQLException { // Внимание! Данный метод стирает все веса!
        statmt = conn.createStatement();
        statmt.execute("DROP TABLE if exists 'weights';");
        System.err.println("Таб6лица weights удалена");
        statmt.execute("CREATE TABLE if not exists 'weights' (id INTEGER PRIMARY KEY AUTOINCREMENT, weight_value double(6,6));");
        System.out.println("Таблица weights создана");
        for(int i = 0; i < weightLenght; i++){
            statmt.execute("INSERT into 'weights' (weight_value) VALUES (0.5)");
        }
        System.out.println("Таблица пересоздана, данные загружены дефолтные");
        ReadDB();
    }
    //---------Изменение значения для обучения---------
    public void updateWeight(int weightId){

    }
    //---------Проверка наличия достаточного количества весов-------
    //---------Чтение с таблицы--------
    public double[] getWeights(int begin, int howMuch) throws SQLException {
        int end = begin + howMuch - 1;
        ResultSet resSet = statmt.executeQuery("SELECT weight_value FROM 'weights' WHERE id BETWEEN " + begin + " AND " + end);
        double[] weights = new double[howMuch];
        int i = 0;
        while (resSet.next()){
           weights[i] = resSet.getFloat("weight_value");
           i++;
        }
        return weights;
    }

    public void ReadDB() throws SQLException
    {
        ResultSet resSet = statmt.executeQuery("SELECT * FROM weights");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            double value = resSet.getDouble("weight_value");
            System.out.println( "id = " + id);
            System.out.println( "value = " + value);
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    public WeightManager(Connection conn){
        this.conn = conn;
    }
}
