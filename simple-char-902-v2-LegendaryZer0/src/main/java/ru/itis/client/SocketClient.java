package ru.itis.client;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import ru.itis.messenge.ClientMessage;
import ru.itis.model.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
@Parameters(separators = "=")
public class SocketClient {
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    @Parameter(names = {"--server-ip"})
    private String ip;
    @Parameter(names = {"--server-port"})
    private int port;
    private final Runnable receiverMessagesTask = new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {

                    in.skipBytes(4);    //Если сразу вызвать in.readObject(), то выбрасывает OptionalDataException,всё из-за  4-ёх "волшебных" байт, подробнее здесь https://stackoverflow.com/questions/36904451/optionaldataexception-java

                    ClientMessage response = (ClientMessage) in.readObject();

                    if (response != null) {
                        System.out.println(response.getFrom().getName() + " говорит : " + response.getMessage());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    };

    // начало сессии - получаем ip-сервера и его порт
    public void startConnection() {
        try {
            // создаем подключение
            clientSocket = new Socket(ip, port);


            // получили выходной поток

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            // входной поток

            in = new ObjectInputStream(clientSocket.getInputStream());

            // запустили слушателя сообщений
            new Thread(receiverMessagesTask).start();
            System.out.println("Начало общения");
            startMessaging();
        } catch (IOException e) {
            throw new IllegalStateException(e);

        }
    }

    public void startMessaging() {
        System.out.println("Представьтесь");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        ClientMessage greetingMessage = ClientMessage.builder().from(Client.builder().name("Сервер").build()).build();
        greetingMessage.setMessage(name + " вошёл на сервер");
        sendMessage(greetingMessage);
        while (true) {
            System.out.println("Введите сообщение(будет видно всем)");

            ClientMessage clientMessage = ClientMessage.builder().from(Client.builder().name(name).build()).build();
            clientMessage.setMessage(sc.nextLine());

            sendMessage(clientMessage);

        }
    }

    public void sendMessage(ClientMessage message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


}