package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Waiting for clients...");
            clientSocket = serverSocket.accept(); // Wait for client connection
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error setting up server: " + e.getMessage());
        }
    }

    public void sendObject(Object obj) {
        try {
            outputStream.writeObject(obj);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending object to client: " + e.getMessage());
        }
    }

    public Object receiveObject() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error receiving object from client: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (inputStream != null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
            if (clientSocket != null)
                clientSocket.close();
            if (serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing server connection: " + e.getMessage());
        }
    }
}
