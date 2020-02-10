package org.simple;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper {

    public static List<String> getTodoList() throws SQLException {
        List<String> todoList = new ArrayList<>();

        Connection conn = DataBase.getConnect();
        Statement sql_stmt = conn.createStatement();
        ResultSet rset = sql_stmt.executeQuery("SELECT id, text FROM todo_list");
        while (rset.next()) {
            todoList.add(rset.getString("text"));
        }

        return todoList;
    }

    public static void addNewNote (String newNote) throws SQLException {
        Connection conn = DataBase.getConnect();

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO TODO_LIST (text) VALUES (?)");
        pstmt.setString(1, newNote );
        pstmt.executeUpdate();

//      можно так, тоже работает
//        Statement sql_stmt = conn.createStatement();
//        sql_stmt.executeUpdate ("INSERT INTO TODO_LIST (text) VALUES ('"+newNote+"')");
    }
}