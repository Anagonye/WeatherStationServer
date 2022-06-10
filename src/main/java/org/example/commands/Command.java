package org.example.commands;

public enum Command {
    INFO("\\info", "Displays general information."),
    SET_DB_PORT("\\set -db -port", "Sets database port. Sample use: '\\set -db -port 5432"),
    SET_DB_HOST("\\set -db -host", "Sets database host. Sample use: '\\set -db -host localhost"),
    SET_DB_NAME("\\set -db -name", "Sets database name. Sample use: '\\set -db -name measurement"),
    SET_CDS_TEMPERATURE("\\set -cds -temperature", "Sets temperature data source path. Sample use: '\\set -cds -temperature http://www.example.org/temperature'"),
    SET_CDS_HUMIDITY("\\set -cds -humidity", "Sets humidity data source path. Sample use: '\\set -cds -humidity http://www.example.org/humidity'"),
    SET_CDS_FREQ("\\set -cds -freq","Sets data collecting frequency. Time unit: minutes. Range: 5-60min. Sample use: \\set -cds -freq 10"),
    HELP("\\help","Displays available commands and more information.");



    private final String command;
    private final String description;

    Command(String command, String description){
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }


}
