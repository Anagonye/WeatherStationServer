package org.example.remote;

import org.example.commands.CommandListener;
import org.example.commands.RemoteCommandListener;
import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.InfoStatus;

import java.net.Socket;

public class RemoteAccessThread extends Thread{
    private final CommandListener commandListener;
    public RemoteAccessThread(Socket socket, DataCollector dataCollector, ConnectionProvider connectionProvider, InfoStatus infoStatus){
        this.commandListener = new RemoteCommandListener(socket,dataCollector,connectionProvider, infoStatus);
    }


    @Override
    public void run() {
        commandListener.startListening();
    }


}
