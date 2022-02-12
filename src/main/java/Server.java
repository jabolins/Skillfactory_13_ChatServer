import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    /**
     * list of all connections (all chat server users)
     */
    List<Client> clientList = new ArrayList();
    ServerSocket serverSocket;

    public Server() throws IOException {
        serverSocket = new ServerSocket(1234);
    }

    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket, this);
                clientList.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param message- String value that will be received from the Client
     *                 (usually entered by the user) and sent to all chat users
     */
    public void sendMessageToAll(String message) {
        for (Client client : clientList) {
            client.receive(message);
        }
    }

}
