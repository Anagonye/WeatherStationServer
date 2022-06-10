package org.example.datacollectors;



import org.example.config.DataCollectorConfig;
import org.example.errors.Error;
import org.example.errors.ErrorLogger;
import org.example.exceptions.CollectingOffException;
import org.example.json.JsonReader;
import org.example.measurements.Humidity;
import org.example.measurements.Temperature;

import java.io.IOException;
import java.util.Optional;

/**
 * Class for collecting data from ESP8266 api;
 *
 */
public final class ESPDataCollector implements DataCollector {
    private String espTemperaturePath;
    private String espHumidityPath;
    private boolean collecting;
    private byte collectingFrequency;
    private final ErrorLogger errorLogger;
    private final JsonReader jsonReader;

    public ESPDataCollector(DataCollectorConfig config, JsonReader jsonReader, ErrorLogger errorLogger) throws NullPointerException  {
        if(config == null || jsonReader == null || errorLogger == null){
            throw new NullPointerException();
        }
        this.espTemperaturePath = config.getCdsTemperaturePath();
        this.espHumidityPath = config.getCdsHumidityPath();
        this.collecting = config.isCollecting();
        this.collectingFrequency = config.getFrequency();
        this.jsonReader = jsonReader;
        this.errorLogger = errorLogger;
    }

    @Override
    public DataCollectorConfig getCurrentConfig() {
        return new DataCollectorConfig(
                espTemperaturePath,
                espHumidityPath,
                collectingFrequency,
                collecting
        );
    }


    @Override
    public Optional<Temperature> getTemperature() throws CollectingOffException {
        return readJson(Temperature.class, espTemperaturePath, "Temperature");
    }

    @Override
    public Optional<Humidity> getHumidity() throws CollectingOffException {
        return readJson(Humidity.class, espHumidityPath, "Humidity");
    }
    @Override
    public void collectingOn(){
        this.collecting = true;
    }
    @Override
    public void collectingOff(){
        this.collecting = false;
    }
    @Override
    public boolean isCollecting() {
        return collecting;
    }

    @Override
    public String getTemperatureSourcePath() {
        return espTemperaturePath;
    }

    @Override
    public String getHumiditySourcePath() {
        return espHumidityPath;
    }

    @Override
    public byte getCollectingFrequency() {
        return collectingFrequency;
    }

    @Override
    public void setTemperatureSourcePath(String path) {
       this.espTemperaturePath = path;
    }

    @Override
    public void setHumiditySourcePath(String path) {
        this.espHumidityPath = path;
    }

    @Override
    public void setCollectingFrequency(Byte frequency) throws IllegalArgumentException {
        if(frequency >=5 && frequency <=60){
            this.collectingFrequency = frequency;
        }
        else{
            throw new IllegalArgumentException("Frequency out of range. The minimum is 5 and the maximum is 60");
        }
    }





    private <T> Optional<T> readJson(Class<T> c, String url, String name) throws CollectingOffException {
        if (!collecting){
            throw new CollectingOffException("Collecting is turned off");
        }
        try {
            return jsonReader.read(c, url);
        }catch (IOException e){
            errorLogger.log(new Error("Data collector", name +": "+ e.getMessage()));
        }
        return Optional.empty();

    }

}

