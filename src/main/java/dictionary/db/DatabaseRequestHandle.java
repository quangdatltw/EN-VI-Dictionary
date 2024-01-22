package dictionary.db;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

public class DatabaseRequestHandle implements Dictionary {
    private static Connection connection = null;

    /**
     * Instantiates a new Dictionary database.
     */
    public DatabaseRequestHandle() {
    }

    private static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        URL jarUrl = DatabaseRequestHandle.class.getProtectionDomain().getCodeSource().getLocation();
        Path jarPath;
        try {
            jarPath = Paths.get(jarUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        Path parentDir = jarPath.getParent();
        parentDir = parentDir.resolve("res");
        String url = "jdbc:sqlite:" + parentDir + "//dictionary2.0.db";
        try {
            connection = DriverManager.getConnection(url);
            System.out.println("SQLite database connected!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database disconnected!\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ResultSet view() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM dictionary";
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Load word from Database to local dictionary.
     */
    public static void loadLocalDictionary() {
        connect();
        ResultSet resultSet = view();
        try {
            while (resultSet != null && resultSet.next()) {
                LocalDictionaryRequestHandle.loadWord(resultSet.getString(1), resultSet.getString(2).replace("\\n", "\n"));
            }
            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
    }

    /**
     * Add word to the Database.
     *
     * @param word the word
     * @param def  the def
     */
    public void addWord(String word, String def) {
        try {
            connect();

            String insertQuery = "INSERT INTO dictionary (target, definition) VALUES (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, def);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove word from Database.
     *
     * @param word the word
     */
    public void removeWord(String word) {
        try {
            connect();

            String removeQuery = "DELETE FROM dictionary WHERE target = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update word definition in Database.
     *
     * @param wordToUpdate the word to update
     * @param def          the def
     */
    public void updateWord(String wordToUpdate, String def) {
        try {
            connect();

            String updateQuery = "UPDATE dictionary SET definition = ? WHERE target = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, def);
            preparedStatement.setString(2, wordToUpdate);
            preparedStatement.executeUpdate();

            close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

