import java.io.*;
import java.net.*;

public class client_side {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startClient();
    }

    public void startClient() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // Start a thread to listen for messages from the server
            Thread listenerThread = new Thread(new Listener());
            listenerThread.start();

            // Read messages from the console and send them to the server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listener thread that reads messages from the server
    class Listener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
