package org.example.infosTests;

import org.example.colors.Color;
import org.example.colors.ConsoleColor;
import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.InfoStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class InfoStatusTest {

    @Mock
    DataCollector dataCollector;

    @Mock
    ConnectionProvider connectionProvider;

    private InfoStatus infoStatus;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        infoStatus = new InfoStatus(dataCollector, connectionProvider);

    }

    @Test
    public void should_return_collecting_status_on(){
        //given
        Mockito.when(dataCollector.isCollecting()).thenReturn(true);

        //when
        String actual = infoStatus.collectingStatus();

        //then
        Assertions.assertEquals(ConsoleColor.colorIt("ON", Color.GREEN), actual);

    }


    @Test
    public void should_return_collecting_status_off(){
        //given
        Mockito.when(dataCollector.isCollecting()).thenReturn(false);

        //when
        String actual = infoStatus.collectingStatus();

        //then
        Assertions.assertEquals(ConsoleColor.colorIt("OFF", Color.RED), actual);
    }

    @Test
    public void should_return_db_status_connected(){
        //given
        Mockito.when(connectionProvider.isConnected()).thenReturn(true);

        //when
        String actual = infoStatus.dbStatus();

        //then
        Assertions.assertEquals(ConsoleColor.colorIt("Connected", Color.GREEN), actual);
    }

    @Test
    public void should_return_db_status_disconnected(){
        //given
        Mockito.when(connectionProvider.isConnected()).thenReturn(false);

        //when
        String actual = infoStatus.dbStatus();

        //then
        Assertions.assertEquals(ConsoleColor.colorIt("Disconnected", Color.RED), actual);
    }


}
