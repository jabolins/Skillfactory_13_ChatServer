import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket;
    private String name;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Scanner in;
    private PrintStream out;
    private Server server;

    public Client(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
       //palaižam plūsmu
        new Thread(this).start();
    }

    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            in = new Scanner(inputStream);
            out = new PrintStream(outputStream);

            // lasām no tīkla un rakstām tīklā
            out.println("Welcome to chat");

            out.println("Please enter your name or \"bye\"");
            String inputName = in.nextLine();
            if (inputName.equals("bye")) socket.close();
            name = inputName;
            server.sendAll( name+ " is connected to our chat");

            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(name + ": " + input);
                input = in.nextLine();
            }
            server.sendAll(name + " left chat");
            System.out.println(name + " left chat");
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void receive(String message) {
        out.println(message);
    }
}
