package org.simple;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DataBaseUtils {

    public static void deleteRecord(String id) {
        // delete record from the table
        try {
            Connection conn = DataBase.getConnect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM todo_list WHERE id = ?");
            stmt.setString(1,id);
            stmt.execute();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
