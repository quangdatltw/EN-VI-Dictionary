package main;

import java.sql.*;

public class DictionaryDatabase {
    private static Connection connection = null;

    private DictionaryDatabase(){
            // db parameters
            String url = "jdbc:sqlite:src/resources/sql/dictionary2.0.db";
            try {
                // create a connection to the database
                connection = DriverManager.getConnection(url);
                System.out.println("SQLite database connected!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database disconnected!\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ResultSet view(){
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM dictionary";
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public static void loadLocalDictionary(){
        ResultSet resultSet = new DictionaryDatabase().view();
        LocalDictionary.getIndex().add(0);
        try {
            char check = 'a';
            while(resultSet != null && resultSet.next()){
                String word = resultSet.getString(2);
                if (check != word.charAt(0) && (int) word.charAt(0) >= 97 && (int) word.charAt(0) <= 122) {
                    LocalDictionary.getIndex().add(resultSet.getInt(1) - 1);
                    check = word.charAt(0);
                }
                LocalDictionary.putWord(word, resultSet.getString(3).replace("\\n", "\n"));
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DictionaryDatabase.close();
    }
}

