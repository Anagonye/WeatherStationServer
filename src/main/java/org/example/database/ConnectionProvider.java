package org.example.database;

import org.example.config.ConnectionProviderConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    private  final String prefix = "jdbc:postgresql://";
    private  String host;
    private  String port;
    private  String dataBaseName;
    private  String url;
    private String user;
    private  String password;

    public ConnectionProvider(ConnectionProviderConfig config) throws NullPointerException{
        if(config == null){
            throw new NullPointerException();
        }
        this.host = config.getDataBaseHost();
        this.port = config.getDataBasePort();
        this.dataBaseName = config.getDataBaseName();
        this.user = config.getUser();
        this.password = config.getPassword();
        this.url = createUrl();
    }




    public  Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user,password);
    }

    public ConnectionProviderConfig getCurrentConfig(){
        return new ConnectionProviderConfig(
                host,
                port,
                dataBaseName,
                user,
                password
        );
    }


    public  String getUrl() {
        return url;
    }


    public  String getUser() {
        return user;
    }

    public  void setUser(String user) {
        if(!user.isBlank()){
        this.user = user;
        }
    }

    public  String getPassword() {
        return password;
    }

    public  void setPassword(String password) {

        if(!password.isBlank()){
        this.password = password;
        }
    }

    public  String getPrefix() {
        return prefix;
    }


    public  String getHost() {
        return host;
    }

    public  void setHost(String host) throws IllegalArgumentException {
        if(!host.isBlank()){
            this.host = host;
            updateUrl();
        }
        else{
            throw new IllegalArgumentException("Host cannot be empty.");
        }


    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) throws IllegalArgumentException {
        if(!port.isBlank()){
            this.port = port;
            updateUrl();
        }
        else {
            throw new IllegalArgumentException("Port number cannot be empty.");
        }
    }

    public  String getDataBaseName() {
        return dataBaseName;
    }

    public  void setDataBaseName(String dataBaseName) throws IllegalArgumentException {
        if(!dataBaseName.isBlank()){
        this.dataBaseName = dataBaseName;
        updateUrl();
        }
        else {
            throw new IllegalArgumentException("Database name cannot be empty.");
        }
    }

    private  String createUrl(){
        return String.format(prefix +"%s:%s/%s", host, port,dataBaseName);
    }

    public  void updateUrl() {
        this.url = createUrl();
    }

    public boolean isConnected(){
        try{
        return getConnection().isValid(15);
        }catch (SQLException e){
            return false;
        }
    }
}
