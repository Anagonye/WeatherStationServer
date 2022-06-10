package org.example.measurements;

import java.time.LocalDateTime;

public class Humidity extends Measurement {
    private float humidity;

    public Humidity(){};

    public Humidity(Long id, LocalDateTime createdAt, float humidity) {
        super(id, createdAt);
        this.humidity = humidity;
    }

    public Humidity(float humidity) {
        this.humidity = humidity;
    }



    public Humidity(float humidity, LocalDateTime createdAt) {
        super(createdAt);
        this.humidity = humidity;
    }

    public float getHumidity() {
        return humidity;
    }


}
