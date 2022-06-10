package org.example.errors;

import org.example.database.ConnectionProvider;

import java.sql.*;

public class ErrorDao {

    private Connection connection;

    public ErrorDao(ConnectionProvider connectionProvider){
        try{
            connection = connectionProvider.getConnection();
            createTable();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void save(Error error){
        final  String sqlQuery = "INSERT INTO error (type,description,created_at) VALUES (?,?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, error.getType());
            statement.setString(2, error.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(error.getSavedAt()));
            statement.executeUpdate();
        }catch (SQLException e){
            //TODO: handle catch
           throw new RuntimeException(e);
        }


    }






    private void createTable() throws SQLException {
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS ERROR (id SERIAL PRIMARY KEY NOT NULL , type varchar, description varchar , created_at timestamp)";
        final String isTableExistsSQL = "SELECT EXISTS (SELECT FROM pg_tables WHERE  schemaname = 'public' AND tablename = 'error')";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(isTableExistsSQL);

        if (resultSet.next() && !resultSet.getBoolean("exists")) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'Error' created");
        }
    }






}
