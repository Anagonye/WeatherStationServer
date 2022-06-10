package org.example.colors;

public enum Color {
    RESET("\033[0m"),
    GREEN("\033[0;32m"),
    RED("\033[0;31m");

    private final String ANSI_COLOR;

    Color(String ANSI_COLOR){
        this.ANSI_COLOR = ANSI_COLOR;
    }

    public String getANSI_COLOR() {
        return ANSI_COLOR;
    }
}
