//Student: Litcan Nicolae-Gabirel
//Registration Number: 1903165

package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProcess {
    private final static int port = 8888;
    private static final Game game = new Game();

    public static void main(String[] args) {
        RunServer();
    }

    private static void RunServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for players...\nPlease run ClientProgram.");
            do {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket, game)).start();
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}