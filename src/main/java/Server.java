import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    List<Client> clientList = new ArrayList();
    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    public void run() {
        while (true) {
            System.out.println("Waiting....");
            try {
                Socket socket = serverSocket.accept();

                Client client = new Client(socket, this);
                System.out.println("New client is connected");
                clientList.add(client);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendAll(String message) {
        for (Client client : clientList) {
            client.receive(message);
        }
    }


}
