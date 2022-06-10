package org.example.jsonTests;

import org.example.json.JsonReader;
import org.example.measurements.Temperature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.net.MalformedURLException;



public class JsonReaderTests {

    private final JsonReader jsonReader = new JsonReader();


    @Test
    public void given_url_is_null_should_throw_MalformedURLException() {
        //given
        String url = null;
        //then
        Assertions.assertThrows(MalformedURLException.class, () -> jsonReader.read(Temperature.class, url) );
    }

    @Test
    public void given_url_is_blank_should_throw_MalformedURLException(){
        //given
        String url = " ";
        //then
        Assertions.assertThrows(MalformedURLException.class, () -> jsonReader.read(Temperature.class, url) );
    }

    @Test
    public void given_class_is_null_should_throw_NullPointerException(){
        //given
        String url =  "https://www.myweather/temperature";


        //then
        Assertions.assertThrows(NullPointerException.class, () -> jsonReader.read(null, url));
    }
}
