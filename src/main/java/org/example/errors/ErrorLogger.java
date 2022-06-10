package org.example.errors;

import org.example.database.ConnectionProvider;

public class ErrorLogger {
    private final ConnectionProvider connectionProvider;
    private final ErrorDao errorDao;

    public ErrorLogger(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.errorDao = new ErrorDao(connectionProvider);
    }

    public void log(Error error){
        errorDao.save(error);
    }




}
