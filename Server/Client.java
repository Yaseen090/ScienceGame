package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void sendObject(Object obj) {
        try {
            outputStream.writeObject(obj);
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Error sending object to server: " + e.getMessage());
        }
    }

    public Object receiveObject() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error receiving object from server: " + e.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (inputStream != null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.err.println("Error closing client connection: " + e.getMessage());
        }
    }
}
