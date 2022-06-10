package org.example.infosTests;

import org.example.database.ConnectionProvider;
import org.example.datacollectors.DataCollector;
import org.example.infos.GeneralInfo;
import org.example.infos.InfoStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.mock;

public class GeneralInfoTest {

    @Test
    public void should_print_correct_info(){
        //given
        DataCollector dataCollector = mock(DataCollector.class);
        ConnectionProvider connectionProvider = mock(ConnectionProvider.class);
        InfoStatus infoStatus = new InfoStatus(dataCollector,connectionProvider);

        Mockito.when(dataCollector.isCollecting()).thenReturn(false);
        Mockito.when(connectionProvider.isConnected()).thenReturn(true);

        Mockito.when(dataCollector.getTemperatureSourcePath()).thenReturn("https://www.myweather.org/temperature");
        Mockito.when(dataCollector.getHumiditySourcePath()).thenReturn("https://www.myweather.org/humidity");
        Mockito.when(dataCollector.getCollectingFrequency()).thenReturn((byte) 10);
        Mockito.when(connectionProvider.getUrl()).thenReturn("jdbc:postgresql://localhost:5432/test");

        String expectedLine1 = "Server VERSION: 0.1";
        String expectedLine2 = "Temperature collecting data source: '" + dataCollector.getTemperatureSourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|";
        String expectedLine3 = "Humidity collecting  data source: '" + dataCollector.getHumiditySourcePath() + "' COLLECTING: |" + infoStatus.collectingStatus() + "|";
        String expectedLine4 = "Collecting frequency: " + dataCollector.getCollectingFrequency() + "min.";
        String expectedLine5 = "Database source: '" + connectionProvider.getUrl() + "' STATUS: |" + infoStatus.dbStatus() + "|";

        String expected = expectedLine1
                + System.lineSeparator()
                + expectedLine2
                + System.lineSeparator()
                + expectedLine3
                + System.lineSeparator()
                + expectedLine4
                + System.lineSeparator()
                + expectedLine5;

        PrintStream standardOut = System.out;
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

        //reassign standard output stream
        System.setOut(new PrintStream(outputStreamCaptor));

        GeneralInfo generalInfo = new GeneralInfo(dataCollector,connectionProvider, infoStatus);

        //when
        generalInfo.printInfo();

        //then
        Assertions.assertEquals(expected,outputStreamCaptor.toString().trim());

        //restoring original output stream
        System.setOut(standardOut);
    }
}
