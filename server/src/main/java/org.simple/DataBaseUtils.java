package org.simple;

import org.simple.entity.ToDoGroup;
import org.simple.entity.ToDoItem;
import org.simple.util.AutowiredForUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataBaseUtils {

    private static Connection CONNECTION_INSTANCE = null;

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

    public static List<ToDoItem> getTodoListIT() throws SQLException {
        List<ToDoItem> todoListIT = new ArrayList<>();

        Connection conn = getConnect();
        Statement sql_stmt = conn.createStatement();
        ResultSet rset = sql_stmt.executeQuery("SELECT id, text FROM todo_list order by id");
        while (rset.next()) {
            todoListIT.add(new ToDoItem(rset.getInt("id"), rset.getString("text")));
        }

        return todoListIT;
    }

    public static List<String> getTodoList() throws SQLException {
        List<String> todoList = new ArrayList<>();

       for (ToDoItem item : getTodoListIT()) {
           todoList.add(item.getText());
       }

       return todoList;
    }

    public static void deleteRecord(int id) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM todo_list WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void editRecord(int id, String text) throws SQLException {
        Connection conn = getConnect();
        PreparedStatement stmt = conn.prepareStatement("UPDATE todo_list SET text = ? WHERE id = ?");
        stmt.setString(1, text);
        stmt.setInt(2, id);
        stmt.execute();
    }

    public static int addRecord(String text) throws SQLException {
        Connection conn = getConnect();
        String insert = "INSERT INTO TODO_LIST (text) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, text);
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            throw new SQLException("can not get id for new record");
        }
    }

    public static List<ToDoGroup> getTodoGroups() throws SQLException {
        List<ToDoGroup> todoListGroups = new ArrayList<>();

        Connection conn = getConnect();
        Statement sql_stmt = conn.createStatement();
        ResultSet rset = sql_stmt.executeQuery("SELECT id, group_name FROM todo_group ORDER BY id;");
        while (rset.next()) {
            todoListGroups.add(new ToDoGroup(rset.getInt("id"), rset.getString("group_name")));
        }

        return todoListGroups;
    }

    public static List<ToDoFormedGroup> getFormedGroup(){
        List<ToDoFormedGroup> toDoFormedGroups = new ArrayList<>();

        List<ToDoGroup> toDoGroups = AutowiredForUtils.getToDoGroupRepository().findAll();
        for (ToDoGroup toDoGroup : toDoGroups) {
            toDoFormedGroups.add(new ToDoFormedGroup(
                    toDoGroup.getId(),
                    toDoGroup.getGroup_name(),
                    AutowiredForUtils.getToDoItemRepository().findBytoDoGroupId(toDoGroup.getId())
            ));
        }

        return toDoFormedGroups;
    }
}
