package org.example.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class JsonReader {


    public  <T> Optional<T> read(Class<T> c, String url) throws IOException, NullPointerException {
        if(c == null){
            throw new NullPointerException();
        }
        URL urL = new URL(url);
        ObjectMapper mapper = new ObjectMapper();

        return Optional.of(mapper.readValue(urL, c));
    }

}
