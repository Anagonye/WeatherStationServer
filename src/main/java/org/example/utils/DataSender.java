package org.example.utils;


import java.io.DataOutputStream;
import java.io.IOException;


public class DataSender {

    private final DataOutputStream dataOutputStream;

    public DataSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public void sendText(String... values) throws IOException {
        dataOutputStream.writeChar('t');
        dataOutputStream.writeInt(values.length);
        for(String text : values){
            dataOutputStream.writeUTF(text);
        }
        dataOutputStream.flush();
    }



    public void sendBoolean(boolean b) throws IOException{
        dataOutputStream.writeChar('b');
        dataOutputStream.writeBoolean(b);
        dataOutputStream.flush();

    }
}
