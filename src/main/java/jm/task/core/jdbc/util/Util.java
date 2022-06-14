package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    // Здесь же пулл конекшенов не нужен, мне кажется?
    private static Connection connection;

    // Соединение с БД
    public static Connection getConnection() {
        if (connection == null){
            String db = "pp1db";
            String user = "root";
            String password = "????";

            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

}
