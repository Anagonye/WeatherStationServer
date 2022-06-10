package org.example.DaoTests;

import org.example.config.ConnectionProviderConfig;
import org.example.database.ConnectionProvider;
import org.example.errors.ErrorLogger;
import org.example.measurements.Temperature;
import org.example.measurements.TemperatureDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


public class TemperatureDaoTests {

    private final ConnectionProviderConfig config = new ConnectionProviderConfig
            (
              "localhost",
              "5432", "test3",
              "postgres","toor"

            );
    private final ConnectionProvider connectionProvider = new ConnectionProvider(config);

    @Mock
    ErrorLogger errorLogger;

    private final TemperatureDao temperatureDao = new TemperatureDao(connectionProvider, errorLogger);

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }



    @Test
    @DisplayName("Given Id of object that exists in database, when findById returns it, then optional.isPresent should return true.")
    public void test_findById_method_case1(){
        //given
        Long id = 1200L;
        //when
        Optional<Temperature> actual = temperatureDao.findById(id);
        //then
        Assertions.assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Given Id of object that does not exist in database, when findById try to returns it, then optional.isPresent should return false.")
    public void test_findById_method_case2(){
        //given
        Long id = 5000L;
        //when
        Optional<Temperature> actual = temperatureDao.findById(id);
        //then
        Assertions.assertFalse(actual.isPresent());
    }


    @Test
    public void test_save_method(){
        //Given
        Temperature tempToSave = new Temperature(25.5f);
        //when
        temperatureDao.save(tempToSave);
        Optional<Temperature> optional = temperatureDao.findById(tempToSave.getId());

        Long actual = null;
        if(optional.isPresent()) {
            actual = optional.get().getId();
        }

        //then
        Assertions.assertEquals(tempToSave.getId(), actual);

    }




}
