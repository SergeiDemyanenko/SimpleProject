package org.simple;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
}