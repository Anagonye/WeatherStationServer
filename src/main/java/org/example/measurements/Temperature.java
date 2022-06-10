package org.example.measurements;


import java.time.LocalDateTime;

public class Temperature extends Measurement {
    private float temperature;

    public Temperature() {

    }

    public Temperature(Long id, LocalDateTime createdAt, float temperature) {
        super(id, createdAt);
        this.temperature = temperature;
    }

    public Temperature(float temperature, LocalDateTime save_at) {
        super(save_at);
        this.temperature = temperature;
    }

    public Temperature(float temperature){
        this.temperature = temperature;
    }



    public float getTemperature() {
        return temperature;
    }


    @Override
    public String toString() {
        return "Temperature{" +
                "temperature=" + temperature +
                '}';
    }
}
