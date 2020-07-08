package services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@AllArgsConstructor
public class ServerReceiver {
    public final BufferedReader reader;


    public ServerReceiver(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @SneakyThrows
    public String readMessage() {
        return reader.readLine();
    }
}
