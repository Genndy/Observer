package org.skytech.observer.console;

import org.skytech.observer.dao.PerceptronDAO;
import org.skytech.observer.dao.WeightsDAO;
import org.skytech.observer.dao.services.ConnectionPool;
import org.skytech.observer.perceptron.models.Perceptron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

/*
    Заведует всем и вся. Является синглтоном
    Создаёт PerceptronManager и юзер вводит свои команды по сути отсюда.
    То есть, здесь производиться чтение команды.
 */

public class PerceptronsManager{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PerceptronDAO perDAO = new PerceptronDAO();
    WeightsDAO weightsDAO = new WeightsDAO();

    public PerceptronsManager() throws SQLException { // init()
        System.out.println("Введите команду для работы с перцептроном");
        System.out.println("Введите help для получения инструкции");
        consoleListen();
    }
    public void consoleListen(){
        String s = null;
        try { s = br.readLine(); } catch (IOException e) { e.printStackTrace(); }

        if(s.equals("help")) {
            showHelp();
        } else if(s.equals("create perceptron")){
            createPerceptron();
        } else if(s.equals("train")){
            trainPerceptron();
        } else if(s.equals("delete")){
            deletePerceptron();
        } else if(s.equals("start")){
            startPerceptron();
        } else if(s.equals("show all")) {
            showAllPerceptronList();
        } else if(s.equals("exit")){
            System.out.println("Отключение сервера");
            try {
                ConnectionPool.getConnectionPool().getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.err.println("Команда не распознана.");
            consoleListen();
        }
    }
    // ===================================================================
    public void createPerceptron(){
        System.out.println("Сборка нового перцептрона");
        Perceptron perceptron = null;
        String id = UUID.randomUUID().toString();
        try {
            perceptron = Perceptron.builder()
                    .setId(id)
                    .setName(consoleListenString("Введите имя:"))
                    .setInputCount(consoleListenInt("Введите количество входных нейронов:"))
                    .setOutputCount(consoleListenInt("Введите количество выходных нейронов:"))
                    .setHiddenLayerModificators(consoleListenHiddenLayerModificator("Введите количество слоёв:"))
                    .setSpecialization(consoleListenString("Введите назначение перцептрона"))
                    .setCreatedDate(new Date())
                    .setGeneration(0)
                    .setError_degree(1.0)
                    .setPerceptronType(consoleListenString("Введите название типа перцептрона"))
                    .setStatus("not ready to use")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            consoleListen();
        }
        System.out.println("Перцептрон создан");
        // Запись в базе данных!
        perDAO.addPerceptron(perceptron);
        try{
            weightsDAO.addWeights(perceptron);
        }catch (SQLException e){
            System.err.println("Ошибка при попытке создать веса для перцептрона - удаление перцептрона с базы данных");
            perDAO.deletePerceptronById(perceptron.getId());
            consoleListen();
        }
        System.out.println("Перцептрон записан в базу данных");
        consoleListen();
    }

    // ===================================================================
    private String consoleListenString(String message) throws Exception {
        System.out.println(message);
        String s = "";
        try {
            s = br.readLine();
            if (s.equals("")) {
                System.out.println("Отмена");
                consoleListen(); // вернёт обратно
            } else if (s.toCharArray().length >= 100) {
                System.err.println("Ошибка: текст должен иметь менее 100 символов");
                consoleListen(); // вернёт обратно
            } else {
                return s;
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при попытке ввода");
            e.printStackTrace();
        }
        throw new IOException();
    }

    private int consoleListenInt(String message) throws IOException{
        System.out.println(message);
        try{
            String s = "";
            s = br.readLine();
            return Integer.parseInt(s);
        }catch (IOException e){
            e.getStackTrace();
            consoleListen();
        }catch (NumberFormatException e){
            System.err.println("Неверный формат ввода значения типа int");
            e.fillInStackTrace();
        }
        throw new IOException();
    }

    private double consoleListenDouble(String message) throws IOException{
        System.out.println(message);
        try{
            String s = "";
            s = br.readLine();
            return Double.parseDouble(s);
        }catch (IOException e){
            e.getStackTrace();
            consoleListen();
        }catch (NumberFormatException e){
            System.err.println("Неверный формат ввода значения типа double");
            e.fillInStackTrace();
        }
        throw new IOException();
    }

    private Double[] consoleListenHiddenLayerModificator(String message) throws IOException{
        System.out.println(message);
        int hiddenLayerCount = consoleListenInt("Введите количество входных нейронов:");
        Double[] hiddenLayerModificator = new Double[hiddenLayerCount];
        for(int i = 0; i < hiddenLayerCount; i++){
            String s = "";
            hiddenLayerModificator[i] = consoleListenDouble("Введите дробное число для скрытого слоя №" + (i+1));

        }
        return hiddenLayerModificator;
    }

    // ===================================================================
    public void showHelp(){
        System.out.println("Инструкция");
        System.out.println();
        consoleListen();
    }
    public void trainPerceptron(){
    }
    public void startPerceptron(){}
    public void deletePerceptron(){
    }
    public void showAllPerceptronList(){

    }
}

