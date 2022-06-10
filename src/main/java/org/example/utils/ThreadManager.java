package org.example.utils;

import org.example.remote.RemoteManager;
import org.example.datacollectors.DataCollectorThread;

public class ThreadManager {

    private final DataCollectorThread dataCollectorThread;
    private final RemoteManager remoteManager;

    public ThreadManager(DataCollectorThread dataCollectorThread, RemoteManager remoteManager) {
        this.dataCollectorThread = dataCollectorThread;
        this.remoteManager = remoteManager;
    }

    public void stopDataCollectingThread(){
        dataCollectorThread.setRunning(false);
        dataCollectorThread.interrupt();

    }
    public void stopRemoteManager(){
        remoteManager.closeServer();
        remoteManager.setRunning(false);
    }
}
