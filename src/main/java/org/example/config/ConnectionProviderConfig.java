package org.example.config;

import java.io.Serial;
import java.io.Serializable;

public class ConnectionProviderConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 39393939201201201L;

    private String dataBaseHost;
    private String dataBasePort;
    private String dataBaseName;
    private String user;
    private String password;




    public ConnectionProviderConfig(String dataBaseHost, String dataBasePort, String dataBaseName, String user, String password) {

        this.dataBaseHost = dataBaseHost;
        this.dataBasePort = dataBasePort;
        this.dataBaseName = dataBaseName;
        this.user = user;
        this.password = password;
    }

    public String getDataBaseHost() {
        return dataBaseHost;
    }

    public void setDataBaseHost(String dataBaseHost) {
        this.dataBaseHost = dataBaseHost;
    }

    public String getDataBasePort() {
        return dataBasePort;
    }

    public void setDataBasePort(String dataBasePort) {
        this.dataBasePort = dataBasePort;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }

    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
