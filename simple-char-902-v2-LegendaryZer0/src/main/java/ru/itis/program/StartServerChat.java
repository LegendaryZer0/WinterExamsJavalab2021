package ru.itis.program;

import com.beust.jcommander.JCommander;
import ru.itis.server.SocketServer;

public class StartServerChat {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        JCommander.newBuilder()
                .addObject(socketServer)
                .build()
                .parse(args);

        socketServer.start();

    }
}
