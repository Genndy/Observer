package org.skytech.observer.perceptron.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DictionaryManager {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    private static String[] dictionary = {
            "вырастет",
            "упадёт",
            "акция",
            "прорыв",
            "политика",
            "соглашение",
            "между",
            "разрыв",
            "цена",
            "году",
            "месяце",
            "покупайте",
            "продавайте",
            "купили",
            "продали",
            "катастрофа",
            "крах",
            "партнёры",
            "скандал",
            "что-то"
    };

    public DictionaryManager(Connection conn) throws SQLException, ClassNotFoundException {
        this.conn = conn;
        CreateDB();
        WriteDB();
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("DROP TABLE if exists 'dictionary_test'");
        statmt.execute("CREATE TABLE if not exists 'dictionary_test' ('word' char(100));");
        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        for(int i = 0; i < dictionary.length; i++){
            statmt.execute("INSERT INTO 'dictionary_test' ('word') VALUES (" + "\'"+dictionary[i]+"\')");
        }
        System.out.println("Таблица заполнена");
    }

    // --------Получение словаря--------
    public static String[] GetWords() throws SQLException {
//        ArrayList<String> dictionary = new ArrayList<String>();
        ArrayList<String> words = new ArrayList<String>();
        String[] dictionary;
        resSet = statmt.executeQuery("SELECT * FROM dictionary_test");
        while(resSet.next())
        {
            String word = resSet.getString("word");
            words.add(word);
        }
        dictionary = new String[words.size()];
        for(int i = 0; i < words.size(); i++){
            dictionary[i] = words.get(i);
        }
        return dictionary;
    }
}
