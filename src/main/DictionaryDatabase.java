package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DictionaryDatabase {
    private static final String DB_NAME = "envidata";
    private static final String USER_NAME = "InfelPhira";
    private static final String PASSWORD = "METHOD_ACCESS";
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306" + "/" + DB_NAME;

    // Trong đó ://localhost:3306/"DB_NAME" là tên và đường dẫn tới CSDL.
    private static Connection connection = null;

    public DictionaryDatabase(){
        try {
            // Kết nối tới CSDL theo đường dẫn MYSQL_URL, tài khoản đăng nhập là InfelPhira, pass là METHOD_ACCESS
            connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
            System.out.print("Kết nối thành công");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void close() {
        close(connection);
        System.out.println("Database disconnected!");
    }
    public static void main(String[] ar){
        new DictionaryDatabase();
    }
}

