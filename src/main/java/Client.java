import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    /**
     * name- String value (not unique) to identify the user and display to other users
     */
    private String nameOfUser;
    private final Socket socket;
    private final Server server;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Scanner in;
    private PrintStream out;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        new Thread(this).start();
    }

    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            in = new Scanner(inputStream);
            out = new PrintStream(outputStream);

            out.println("Welcome to chat");

            out.println("Please enter your name or \"bye\"");
            String inputName = in.nextLine();
            if (inputName.equals("bye")) socket.close();
            nameOfUser = inputName;
            server.sendMessageToAll( nameOfUser + " is connected to our chat");

            String inputMessage = in.nextLine();
            while (!inputMessage.equals("bye")) {
                server.sendMessageToAll(nameOfUser + ": " + inputMessage);
                inputMessage = in.nextLine();
            }
            server.sendMessageToAll(nameOfUser + " left chat");
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive(String message) {
        out.println(message);
    }
}
