package org.example.remote;

import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.InfoStatus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RemoteManager extends Thread{

    private boolean running = true;
    private final DataCollector dataCollector;
    private final ConnectionProvider connectionProvider;
    private final InfoStatus infoStatus;

    private ServerSocket serverSocket;

    public RemoteManager(DataCollector dataCollector, ConnectionProvider connectionProvider, InfoStatus infoStatus){
        this.dataCollector = dataCollector;
        this.connectionProvider = connectionProvider;
        this.infoStatus = infoStatus;
    }

    @Override
    public void run() {
       try{
           serverSocket = new ServerSocket(5000);
           while (running){
               new RemoteAccessThread(serverSocket.accept(), dataCollector, connectionProvider, infoStatus).start();
               System.out.println("Client connected");
           }
       }catch (IOException e){
           System.out.println("Server exception: " + e.getMessage());
       }finally {
           try {
               serverSocket.close();
           } catch (IOException e) {
               System.out.println(e.getMessage());
           }
       }


    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void closeServer(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
