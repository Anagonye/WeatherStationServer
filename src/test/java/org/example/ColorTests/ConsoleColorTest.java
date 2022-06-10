package org.example.ColorTests;

import org.example.colors.Color;
import org.example.colors.ConsoleColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConsoleColorTest {


    @Test
    public void should_return_colored_string_and_reset_color(){
        //given
        String textToColor = "Color me pleas";
        String sampleText = "Do not color me";

        //when
        String actual = ConsoleColor.colorIt(textToColor, Color.RED) + sampleText;

        //then
        Assertions.assertEquals("\033[0;31mColor me pleas\033[0mDo not color me", actual);
    }
}
