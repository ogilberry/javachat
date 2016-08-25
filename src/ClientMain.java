import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientMain {

    private boolean hasMessage = false;
    private String host;
    private int port;
    private PrintWriter writer;
    private BufferedReader reader;
    private ArrayList<String> messages;
    private ClientGUI clientGUI;
    private String clientName;

    ClientMain(String host, int port, ArrayList<String> messages, ClientGUI clientGUI){
        this.host = host;
        this.port = port;
        this.messages= messages;
        this.clientGUI = clientGUI;
        go();
    }

    public void sendMessageToServer(String message){
        writer.println(message);
    }

    private void go(){
        try {
            //connect to the server
            Socket socket = new Socket(host, port);

            //send a message to the server
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello Server! From the client");

            //reader to read lines of text from the server
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //loop for messages from the server
            ClientMainThread messageThread = new ClientMainThread(reader, messages, clientGUI);
            messageThread.start();
        }catch(ConnectException ce){
            clientGUI.setConnectionError("Could not connect to this host.");

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }
}
