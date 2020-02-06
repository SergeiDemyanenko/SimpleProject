package org.simple;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SqlUtils {

    // delete record from the table

    public static void deleteRecord(String id) {
        try {
            Connection conn = DataBase.getConnect();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM todo_list WHERE id = ?");
            stmt.setString(1,id);
            stmt.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }

    }

}
