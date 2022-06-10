package org.example.datacollectors;


import org.example.measurements.Humidity;
import org.example.measurements.MeasurementDao;
import org.example.measurements.Temperature;

import java.util.Optional;

public class DataCollectorThread extends Thread{

    private final DataCollector dataCollector;
    private final MeasurementDao<Temperature> temperatureDao;
    private final MeasurementDao<Humidity> humidityDao;
    private boolean running = true;

    public DataCollectorThread(DataCollector dataCollector, MeasurementDao<Temperature> temperatureDao, MeasurementDao<Humidity> humidityDao){
        this.dataCollector = dataCollector;
        this.temperatureDao = temperatureDao;
        this.humidityDao = humidityDao;

    }

    @Override
    public void run() {


        while (running){
            if(dataCollector.isCollecting()) {
                Optional<Temperature> temperature = dataCollector.getTemperature();
                temperature.ifPresent(temperatureDao::save);
                Optional<Humidity> humidity = dataCollector.getHumidity();
                humidity.ifPresent(humidityDao::save);


            }
            try {
               Thread.sleep(dataCollector.getCollectingFrequency() * 1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
                }

        }


    }

    public void setRunning(boolean running) {
        this.running = running;
    }


}
