package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;


public class Main {

    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        try(SessionFactory sessionFactory = Util.getSessionFactory()){
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
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
