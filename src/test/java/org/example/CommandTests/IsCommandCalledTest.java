package org.example.CommandTests;

import org.example.commands.CommandUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IsCommandCalledTest {

    @Test
    public void should_take_correct_command_and_return_true(){
        //given
        String command = "\\set -cds -temperature https://www.weather.org/temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertTrue(actual);


    }
    @DisplayName("Too many arguments should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case8(){
        //given
        String command = "\\set -cds -temperature https://www.weather.org/temperature argument";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }


    @DisplayName("Incorrect first argument should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case1(){
        //given
        String command = "set -cds -temperature https://www.weather.org/temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }

    @DisplayName("Incorrect second argument should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case2(){
        //given
        String command = "\\set cds -temperature https://www.weather.org/temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }

    @DisplayName("Incorrect second argument should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case3(){
        //given
        String command = "\\set -cds temperature https://www.weather.org/temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }

    @DisplayName("Empty parameter should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case4(){
        //given
        String command = "\\set -cds -temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }

    @DisplayName("Command is blank should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case5(){
        //given
        String command = " ";

        //when
        boolean expected = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(expected);
    }

    @DisplayName("Command is null should throw NullPointerException")
    @Test
    public void should_take_incorrect_command_and_return_false_case6(){
        //given
        String command = null;

        //then
        Assertions.assertThrows(
                NullPointerException.class,
                () -> CommandUtils.isSetCDSTemperatureCommandCalled(command)
        );
    }

    @DisplayName("No space between arguments should return false")
    @Test
    public void should_take_incorrect_command_and_return_false_case7(){
        //given
        String command = "\\set-cds-temperaturehttps://www.weather.org/temperature";

        //when
        boolean actual = CommandUtils.isSetCDSTemperatureCommandCalled(command);

        //then
        Assertions.assertFalse(actual);
    }








}
