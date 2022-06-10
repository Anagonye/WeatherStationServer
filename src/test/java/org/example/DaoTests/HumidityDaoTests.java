package org.example.DaoTests;

import org.example.config.ConnectionProviderConfig;
import org.example.database.ConnectionProvider;
import org.example.errors.ErrorLogger;
import org.example.measurements.Humidity;
import org.example.measurements.HumidityDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class HumidityDaoTests {

    private final ConnectionProviderConfig config = new ConnectionProviderConfig
            (
                    "localhost",
                    "5432", "test3",
                    "postgres","toor"

            );
    private final ConnectionProvider connectionProvider = new ConnectionProvider(config);

    @Mock
    ErrorLogger errorLogger;

    private final HumidityDao humidityDao = new HumidityDao(connectionProvider, errorLogger);

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }



    @Test
    @DisplayName("Given Id of object that exists in database, when findById returns it, then optional.isPresent should return true.")
    public void test_findById_method_case1(){
        //given
        Long id = 1182L;
        //when
        Optional<Humidity> actual = humidityDao.findById(id);
        //then
        Assertions.assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Given Id of object that does not exist in database, when findById trys to return it, then optional.isPresent should return false.")
    public void test_findById_method_case2(){
        //given
        Long id = 5000L;
        //when
        Optional<Humidity> actual =humidityDao.findById(id);
        //then
        Assertions.assertFalse(actual.isPresent());
    }


    @Test
    public void test_save_method(){
        //Given
        Humidity humToSave = new Humidity(25.5f);
        //when
        humidityDao.save(humToSave);
        Optional<Humidity> optional = humidityDao.findById(humToSave.getId());

        Long actual = null;
        if(optional.isPresent()) {
            actual = optional.get().getId();
        }

        //then
        Assertions.assertEquals(humToSave.getId(), actual);

    }
}
