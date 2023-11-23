package dictionary.db;

import java.sql.*;

public class DictionaryDatabase {
    private static Connection connection = null;

    private DictionaryDatabase(){
            // db parameters
            String url = "jdbc:sqlite:src/main/resources/dictionary/db/dictionary2.0.db";
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
        try {
            while(resultSet != null && resultSet.next()) {
                LocalDictionary.putWord(resultSet.getString(2), resultSet.getString(3).replace("\\n", "\n"));
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DictionaryDatabase.close();
        InputHandle.inputFile("src/main/resources/external_dictionary/Add_on.txt");
    }
}

