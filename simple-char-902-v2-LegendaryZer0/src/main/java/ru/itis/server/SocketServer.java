package ru.itis.server;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ru.itis.messenge.ClientMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
@Parameters(separators = "=")
public class SocketServer {
    private final List<ClientHandler> clients;
    private final List<ObjectOutputStream> clientsStreams;
    private volatile ClientMessage inputLine;
    @Parameter(names = {"--port"})
    private int port;

    public SocketServer() {
        clientsStreams = new CopyOnWriteArrayList<>();
        clients = new CopyOnWriteArrayList<>();
    }


    public void start() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // запускаем бесконечный цикл
        while (true) {
            try {
                // запускаем обработчик сообщений для каждого подключаемого клиента
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }


    private class ClientHandler extends Thread {
        // связь с одним клиентом
        private final Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;


        ClientHandler(Socket socket) {
            this.clientSocket = socket;
            // добавляем текущее подключение в список
            clients.add(this);
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientsStreams.add(new ObjectOutputStream(out));
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("New client");
        }

        public void run() {
            try {
                System.out.println("Ко мне кто-то подключился");

                in = new ObjectInputStream(clientSocket.getInputStream());
                System.out.println("Получил входной поток клиента");

                while ((inputLine = (ClientMessage) in.readObject()) != null) {
                    System.out.println(inputLine);

                    ClientMessage finalInputLine = inputLine;
                    clients.forEach(x -> {
                        try {
                            if (!x.equals(this)) {//что бы сервер не пересылал сообщения отправителю
                                x.out.writeObject(finalInputLine);
                                x.out.flush();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });

                }
                System.out.println("Перед закрытием входящего потока и клиентского сокета");
                System.out.println(inputLine);
                in.close();
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {

                throw new IllegalStateException(e);
            }
        }
    }


}