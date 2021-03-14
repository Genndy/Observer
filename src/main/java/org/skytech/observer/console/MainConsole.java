package org.skytech.observer.console;

import org.joda.time.DateTime;
import org.skytech.observer.dao.*;
import org.skytech.observer.dao.services.ConnectionPool;
import org.skytech.observer.input.InputDataManager;
import org.skytech.observer.input.model.InputSymbol;
import org.skytech.observer.perceptron.models.Perceptron;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MainConsole {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PerceptronDAO perDAO = new PerceptronDAO();
    WeightsDAO weightsDAO = new WeightsDAO();
    InputSymbolsDAO inputSymbolDAO = new InputSymbolsDAO();

    public MainConsole() throws SQLException { // init()
        System.out.println("Введите команду для работы с перцептроном");
        System.out.println("Введите help для получения инструкции");
        consoleListen();
    }
    public void consoleListen(){
        String s = "";
        try { s = br.readLine(); } catch (IOException e) { e.printStackTrace(); }

        if(s.equals("help")) {
            showHelp();
        } else if(s.equals("create perceptron")){
            createPerceptron();
        } else if(s.equals("create symbol")){
            createSymbol();
        } else if(s.equals("show symbols")){
            createSymbol();
        } else if(s.equals("train")){
            trainPerceptron();
        } else if(s.equals("delete")){
            deletePerceptron();
        } else if(s.equals("start")) {
            startPerceptron();
        }else if(s.equals("test")){
            try {
                String str = consoleListenString("БЛЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯ");
                String strEcraned = str.replaceAll("\\'", "\\\\'");
                System.out.println(strEcraned);

            } catch (Exception e) {
                e.printStackTrace();
            }


            consoleListen();
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

    private void getAllSymbol(){
        try {
            List<InputSymbol> symbols = inputSymbolDAO.getAll();
            for(int i = 0; i < symbols.size(); i++){
                System.out.println(i + " " + symbols.get(i).getName() + " " + symbols.get(i).getVersion());
            }

            boolean wannaReadDataNow = consoleListenBoolean("Желаете ли вы начать чтение данных для данного символа уже сейчас? (y/n)");
            if(wannaReadDataNow = true){
                int j = consoleListenInt("Введите номер читаемого \"символа\"");
                System.out.println("Инициализация чтения данных");
                InputDataManager inputDataManager = new InputDataManager();
                inputDataManager.loadDataNewsForSymbol(symbols.get(j));
                System.out.println("Чтение данных завершено");
                consoleListen();
            }else{
                consoleListen();
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createSymbol() {
        System.out.println("Сборка символа");
        InputSymbol symbol = null;
        try {

//            DateTime date = consoleListenDateTime("Введите дату начала в формате: YYYY-MM-dd (прим.: 2020-12-31)");
            symbol = InputSymbol.builder()
                    .setName(consoleListenString("Введите символ: (пример: AAPL, TSLA)"))
                    .setVersion(consoleListenDouble("Введите версию, или оставьте пустым это поле"))
                    .setDateBeginToRead(consoleListenDateTime("Введите дату начала в формате: YYYY-MM-dd (прим.: 2020-12-31)"))
                    .setDatePlannedToFill(consoleListenDateTime("Введите дату конца чтения данных, или оставьте это поле пустым"))
                    .build();
            System.out.println("символ собран создан");
            inputSymbolDAO.addInputSymbolDAO(symbol);
            System.out.println("Символ добавлен");
            boolean wannaReadDataNow = consoleListenBoolean("Желаете ли вы начать чтение данных для данного символа уже сейчас? (y/n)");
            if(wannaReadDataNow = true){
                System.out.println("Инициализация чтения данных");
                InputDataManager inputDataManager = new InputDataManager();
                inputDataManager.loadDataNewsForSymbol(symbol);
                System.out.println("Чтение данных завершено");
                consoleListen();
            }else{
                consoleListen();
            }
        } catch (Exception e) {
            System.err.println("Ошибка при сборке InputSymbol");
            e.printStackTrace();
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
                    .setCreatedDate(DateTime.now())
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
    private boolean consoleListenBoolean(String message){
        System.out.println(message);
        String s = "";
        try {
            s = br.readLine();
            if(s.equals("") || s.equals("n")){
                return false;
            }else if(s.equals("y")){
                return true;
            }else{
                System.err.println("Неверный формат ввода. Введите \"y\" или \"n\"");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private DateTime consoleListenDateTime(String message) throws Exception{
        System.out.println(message);
        String s = "";
        DateTime date;
        try {
            s = br.readLine();
            if (s.equals("")) {
                System.out.println("Отмена");
            } else if (s.toCharArray().length >= 100) {
                System.err.println("Ошибка: текст должен иметь менее 100 символов");
            } else {
//                SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS+ZZZZZ", Locale.ENGLISH);
                date = DateTime.parse(s);
                return date;
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при попытке ввода");
            e.printStackTrace();
        }
        throw new IOException();
    }

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

    //
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