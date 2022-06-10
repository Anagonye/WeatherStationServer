package org.example.DataCollectorTests;

import org.example.config.DataCollectorConfig;
import org.example.datacollectors.ESPDataCollector;
import org.example.errors.ErrorLogger;
import org.example.exceptions.CollectingOffException;
import org.example.json.JsonReader;
import org.example.measurements.Humidity;
import org.example.measurements.Temperature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Optional;


public class DataCollectorTests {

    @Mock
    ErrorLogger errorLogger;

    @Mock
    JsonReader jsonReader;


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void call_collectingOn_is_collecting_should_return_true(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, false);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);

        //when
        espDataCollector.collectingOn();

        //then

        Assertions.assertTrue(espDataCollector.isCollecting());
    }


    @Test
    public void call_collectingOff_is_collecting_should_return_false(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, true);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);

        //when
        espDataCollector.collectingOff();

        //then

        Assertions.assertFalse(espDataCollector.isCollecting());
    }


    @Test
    public void collecting_is_off_should_throw_collectingOffException(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, false);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);
        //then
        Assertions.assertThrows(CollectingOffException.class, espDataCollector::getTemperature);


    }




    @Test
    public void read_method_throws_IOException_should_return_empty_optional(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, true);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);

        try {
            Mockito.when(jsonReader.read(Temperature.class, "url")).thenThrow(new IOException());
        }catch (IOException e){
            e.printStackTrace();
        }

        //when
        Optional<Temperature> actual = espDataCollector.getTemperature();

        //then
        Assertions.assertEquals(Optional.empty(), actual);

    }





    @Test
    public void read_method_returns_correct_value_should_return_optional_of_temperature_object(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, true);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);

        try {
            Mockito.when(jsonReader.read(Temperature.class, "url")).thenReturn(Optional.of(new Temperature()));
        }catch (IOException e){
            e.printStackTrace();
        }

        //when
        Optional<Temperature> actual = espDataCollector.getTemperature();

        //then
        Assertions.assertInstanceOf(Temperature.class, actual.get());


    }

    @Test
    public void read_method_returns_correct_value_should_return_optional_of_humidity_object(){
        //given
        DataCollectorConfig dataCollectorConfig = new DataCollectorConfig("url", "url", (byte) 10, true);
        ESPDataCollector espDataCollector = new ESPDataCollector(dataCollectorConfig, jsonReader, errorLogger);

        try {
            Mockito.when(jsonReader.read(Humidity.class, "url")).thenReturn(Optional.of(new Humidity()));
        }catch (IOException e){
            e.printStackTrace();
        }

        //when
        Optional<Humidity> actual = espDataCollector.getHumidity();
        //then

        Assertions.assertInstanceOf(Humidity.class, actual.get());


    }






}
