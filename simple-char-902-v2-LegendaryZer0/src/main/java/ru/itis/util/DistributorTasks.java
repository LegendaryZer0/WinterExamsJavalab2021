package ru.itis.util;

import java.net.Socket;
import java.util.Scanner;
// may be i will use threadpool  with sockets, so i place it here to the future
public class DistributorTasks implements Runnable {
    private Socket socket;

    public DistributorTasks(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Tasks will be distributed " +  socket);

        try {
            // Take the stream sent for this sock
            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNextLine()){
                System.out.println( scanner.nextLine() );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
