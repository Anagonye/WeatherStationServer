package org.example.measurements;

import org.example.database.ConnectionProvider;
import org.example.errors.Error;
import org.example.errors.ErrorLogger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HumidityDao implements MeasurementDao<Humidity> {
    private Connection connection;
    private final ErrorLogger errorLogger;

    public HumidityDao(ConnectionProvider connectionProvider, ErrorLogger errorLogger){
        this.errorLogger = errorLogger;
        try {
            this.connection = connectionProvider.getConnection();
            createTable();
        }catch (SQLException e){
            errorLogger.log(new Error("database", e.getMessage()));
        }

    }






    @Override
    public void save(Humidity humidity) {
        final String sql = "INSERT INTO humidity (value, created_at) VALUES(?,?)";
        try(PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setFloat(1, humidity.getHumidity());
            statement.setTimestamp(2, Timestamp.valueOf(humidity.getSaveAt()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                humidity.setId(generatedKeys.getLong(1));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Optional<Humidity> findById(Long id) {
        String sqlQuery = "SELECT id,value,created_at FROM humidity WHERE id = (?)";
        try(PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Long identify = resultSet.getLong("id");
                float value = resultSet.getFloat("value");
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                return Optional.of(new Humidity(identify,createdAt, value));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    private void createTable() throws SQLException{
        final String createTableSQL = "CREATE TABLE IF NOT EXISTS HUMIDITY (id SERIAL PRIMARY KEY NOT NULL , value float, created_at timestamp)";
        final String isTableExistsSQL = "SELECT EXISTS (SELECT FROM pg_tables WHERE  schemaname = 'public' AND tablename = 'humidity')";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(isTableExistsSQL);

        if(resultSet.next() && !resultSet.getBoolean("exists")){
            statement.executeUpdate(createTableSQL);
            System.out.println("Table 'Humidity' created");
        }
    }

    private Humidity map(ResultSet set) throws SQLException{
        float humidity = set.getFloat("value");
        LocalDateTime createdAt = set.getTimestamp("created_at").toLocalDateTime();
        return new Humidity(humidity, createdAt);
    }
}
