package org.example.validators;

public class SimpleValidator implements Validator {

    private static final SimpleValidator INSTANCE = new SimpleValidator();

    private SimpleValidator(){}

    public static SimpleValidator getINSTANCE() {
        return INSTANCE;
    }
    @Override
    public boolean isPortValid(String port){
        return port.matches("[0-9]+") && port.length() <=5;
    }

    @Override
    public boolean isDataSourceValid(String dataSource) {
        return true;
    }


}
