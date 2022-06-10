package org.example.datacollectors;

import org.example.config.DataCollectorConfig;
import org.example.exceptions.CollectingOffException;
import org.example.measurements.Humidity;
import org.example.measurements.Temperature;

import java.io.IOException;
import java.util.Optional;

public interface DataCollector {


    Optional<Temperature> getTemperature() throws CollectingOffException;

    Optional<Humidity> getHumidity() throws CollectingOffException;

    String getTemperatureSourcePath();

    String getHumiditySourcePath();

    void collectingOn();

    void collectingOff();

    boolean isCollecting();

    byte getCollectingFrequency();

    void setCollectingFrequency(Byte frequency) throws IllegalArgumentException;

    void setTemperatureSourcePath(String path);

    void setHumiditySourcePath(String path);

    DataCollectorConfig getCurrentConfig();
}
