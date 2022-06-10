package org.example.config;

import java.io.Serial;
import java.io.Serializable;

public class DataCollectorConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 38383838200200200L;


    private String dcTemperaturePath;
    private String dcHumidityPath;
    private byte frequency;
    private boolean isCollecting;


    public DataCollectorConfig(String dcTemperaturePath, String dcHumidityPath, byte frequency, boolean isCollecting) {
        this.dcTemperaturePath = dcTemperaturePath;
        this.dcHumidityPath = dcHumidityPath;
        this.frequency = frequency;
        this.isCollecting = isCollecting;
    }

    public String getCdsTemperaturePath() {
        return dcTemperaturePath;
    }

    public void setCdsTemperaturePath(String dcTemperaturePath) {
        this.dcTemperaturePath = dcTemperaturePath;
    }

    public String getCdsHumidityPath() {
        return dcHumidityPath;
    }

    public void setCdsHumidityPath(String cdsHumidityPath) {
        this.dcHumidityPath = cdsHumidityPath;
    }

    public byte getFrequency() {
        return frequency;
    }

    public void setFrequency(byte frequency) {
        this.frequency = frequency;
    }

    public boolean isCollecting() {
        return isCollecting;
    }

    public void setCollecting(boolean collecting) {
        isCollecting = collecting;
    }


}
