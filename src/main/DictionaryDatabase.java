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
                System.out.println("Database disconnected!");
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

    public static LocalDictionary getdictionary(){
        ResultSet resultSet = new DictionaryDatabase().view();
        LocalDictionary library = new LocalDictionary();
        try {
            while(resultSet != null && resultSet.next()){
                library.addWord(resultSet.getString(2), resultSet.getString(3).replace("\\n", "\n"));
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DictionaryDatabase.close();
        return library;
    }

    public static void main(String[] ar){
            LocalDictionary library = getdictionary();
            System.out.println(library.getDefinition("ar"));
    }
}

