package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    // Задание не проверено, поэтому 2 варианта решения JDBC и HIBERNATE:

    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        // JDBC
        try(Connection connection = Util.getConnection()){ // нужно же закрыть connection
            solution(service);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // HIBERNATE
        try(SessionFactory sessionFactory = Util.getSessionFactory()){ // нужно же закрыть connection
            ((UserServiceImpl)service).setHIBERNATEImplDao();
            solution(service);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    static void solution(UserService service){

        //Создание таблицы User(ов)
        service .createUsersTable();

        //Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления
        // должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
        for (byte i = 1; i <= 4; i++) {
            String name = "name-" + i;
            String lastName = "lastName-" + i;
            service .saveUser(name, lastName, i);
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        }

        //Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
        service .getAllUsers().forEach(System.out::println);

        //Очистка таблицы User(ов)
        service .cleanUsersTable();

        //Удаление таблицы
        service .dropUsersTable();
    }
}
