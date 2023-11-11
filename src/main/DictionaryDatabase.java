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


    /**
     * Ngắt kết nối tới CSDL
     */
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
            // Khởi tạo đối tượng statement với connection là đối tượng đã kết nối tới CSDL
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM dictionary";

            // Thực thi truy vấn thông qua statement và đưa kết quả trả về ResultSet bao gồm một tập hợp các hàng
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    public static void loadLocalDictionary(){
        ResultSet resultSet = new DictionaryDatabase().view();
        try {
            while(resultSet != null && resultSet.next()){
                String word = resultSet.getString(2);
                LocalDictionary.toWordList(word);
                LocalDictionary.addWord(word, resultSet.getString(3).replace("\\n", "\n"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DictionaryDatabase.close();
    }
}

