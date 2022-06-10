package org.example.measurements;

import org.example.database.ConnectionProvider;
import org.example.errors.Error;
import org.example.errors.ErrorLogger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class TemperatureDao implements MeasurementDao<Temperature> {
    private Connection connection;
    private ErrorLogger errorLogger;

    public TemperatureDao(ConnectionProvider connectionProvider, ErrorLogger errorLogger){
        this.errorLogger = errorLogger;
        try {
         this.connection = connectionProvider.getConnection();

         createTable();

     }catch (SQLException e){
         errorLogger.log(new Error("database", e.getMessage()));
     }

    }


    @Override
    public void save(Temperature temperature){
        final String sqlQuery = "INSERT INTO temperature (value, created_at) VALUES(?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setFloat(1, temperature.getTemperature());
            statement.setTimestamp(2, Timestamp.valueOf(temperature.getSaveAt()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                temperature.setId(generatedKeys.getLong(1));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }



    @Override
    public Optional<Temperature> findById(Long id) {
        final String sqlQuery = "SELECT id,value,created_at FROM temperature WHERE id = (?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Long identify = resultSet.getLong("id");
                float value = resultSet.getFloat("value");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                return Optional.of(new Temperature(identify,createdAt, value));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    private void createTable() throws SQLException{
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS TEMPERATURE (id SERIAL PRIMARY KEY NOT NULL , value float, created_at timestamp)";
        final String isTableExistsSQL = "SELECT EXISTS (SELECT FROM pg_tables WHERE  schemaname = 'public' AND tablename = 'temperature')";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(isTableExistsSQL);

        if(resultSet.next() && !resultSet.getBoolean("exists")){
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'Temperature' created");
        }
    }




}
