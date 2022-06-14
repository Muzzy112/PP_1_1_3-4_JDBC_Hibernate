package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static String url = "jdbc:mysql://localhost:3306/";
    private static String db = "pp1db";
    private static String user = "root";
    private static String password = "???";

    // Здесь же пулл конекшенов не нужен, мне кажется?
    private static Connection connection;

    private static SessionFactory sessionFactory;

    // Соединение с БД jdbc
    public static Connection getConnection() {
        if (connection == null){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, user, password);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    // Соединение с БД hibernate
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null){
            Configuration configuration = new Configuration();

            // проперти с настройками, вместо hibernate.cfg.xml
            Properties propSettings = new Properties();
            propSettings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            propSettings.put(Environment.URL, url + db);
            propSettings.put(Environment.USER, user);
            propSettings.put(Environment.PASS, password);

            propSettings.put(Environment.POOL_SIZE, "1");
            propSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            propSettings.put(Environment.SHOW_SQL, "true");
            //propSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            //propSettings.put(Environment.HBM2DDL_AUTO, "create-drop");

            configuration.setProperties(propSettings);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

}
