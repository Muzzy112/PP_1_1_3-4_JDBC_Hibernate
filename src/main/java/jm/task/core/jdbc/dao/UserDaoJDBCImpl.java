package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoJDBCImpl implements UserDao {
    private String tableName = "users";
    private Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Objects.requireNonNull(Util.getConnection(), "no database connection");
    }

    /*
    You should close ResultSet and Statement explicitly because Oracle has problems previously
    with keeping the cursors open even after closing the connection. If you don't close the
    ResultSet (cursor) it will throw an error like Maximum open cursors exceeded.
     */

    public void createUsersTable() {
        String createStatement = String.format("CREATE TABLE IF NOT EXISTS %s(" +
                "id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL," +
                "name VARCHAR(30)," +
                "lastName VARCHAR(30)," +
                "age TINYINT);", tableName);
        try(Statement statement = connection.createStatement()){
            statement.execute(createStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropStatement = String.format("DROP TABLE IF EXISTS %s;", tableName);
        try(Statement statement = connection.createStatement()){
            statement.execute(dropStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String saveStatement = String.format("INSERT INTO %s(name, lastName, age) VALUES(?, ?, ?);", tableName);
        try(PreparedStatement statement = connection.prepareStatement(saveStatement)){
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String removeStatement = String.format("DELETE FROM %s WHERE id = ?;", tableName);
        try(PreparedStatement statement = connection.prepareStatement(removeStatement)){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String getAllStatement = String.format("SELECT * FROM %s;", tableName);
        try(PreparedStatement statement = connection.prepareStatement(getAllStatement)){
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    byte age = resultSet.getByte("age");
                    User user = new User(name, lastName, age); // может добавить еще 1 конструктор?
                    user.setId(id);
                    users.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String truncate = String.format("TRUNCATE TABLE %s;", tableName);
        try(Statement statement = connection.createStatement()){
            statement.execute(truncate);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
