package org.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBaseUtils {

    private static Connection CONNECTION_INSTANCE = null;

    private static List<String> todoList = null;

    private static Properties getProps() {
        Properties result = new Properties();
        URL url = DataBaseUtils.class.getClassLoader().getResource("application.properties");
        try{
            result.load(new FileInputStream(url.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static Connection getConnect() throws SQLException {
        if (CONNECTION_INSTANCE == null) {
            Properties props = getProps();
            CONNECTION_INSTANCE = DriverManager.getConnection(
                    props.getProperty("spring.datasource.url"),
                    props.getProperty("spring.datasource.username"),
                    props.getProperty("spring.datasource.password"));
        }

        return CONNECTION_INSTANCE;
    }

    public static List<String> getTodoList() throws SQLException {
        if (todoList == null) {
            todoList = new ArrayList<>();

            Connection conn = getConnect();
            Statement sql_stmt = conn.createStatement();
            ResultSet rset = sql_stmt.executeQuery("SELECT id, text FROM todo_list");
            while (rset.next()) {
                todoList.add(rset.getString("text"));
            }
        }

        return todoList;
    }

    public static void deleteRecord(String id) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM todo_list WHERE id = ?");
        stmt.setString(1,id);
        stmt.execute();
    }

    public static void editRecord(int id, String text) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE todo_list SET text = ? WHERE id = ?");
        stmt.setString(1, text);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public static void addNewNote (String text) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TODO_LIST (text) VALUES (?)");
        pstmt.setString(1, text);
        pstmt.executeUpdate();
    }
}
