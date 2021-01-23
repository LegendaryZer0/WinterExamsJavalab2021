package ru.itis.program;

import com.beust.jcommander.JCommander;
import ru.itis.client.SocketClient;

public class StartClentChat {
    public static void main(String[] args) {
        SocketClient client = new SocketClient();
        JCommander.newBuilder().addObject(client).build().parse(args);
        client.startConnection();

    }
}
