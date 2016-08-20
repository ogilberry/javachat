import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientMain {

    private boolean hasMessage = false;
    private String host;
    private int port;

    ClientMain(String host, int port){
        this.host = host;
        this.port = port;
        go();
    }

    private void go(){
        try{
            //connect to the server
            Socket socket = new Socket(host, port);

            //send a message to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello Server! From the client");

            //reader to read lines of text from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //loop for messages from the server
            ClientMainThread messageThread = new ClientMainThread(reader);
            messageThread.start();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    public static void main(String[] args){

        if(args.length!=2) {
            System.out.println("Usage: ClientMain <host> <port>");
        }
        try{
            //connect to the server
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

            //send a message to the server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello Server! From the client");

            //reader to read lines of text from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //loop for messages from the server
            while(true){
                if(reader.ready()) {
                    String message = reader.readLine();
                    System.out.println(message);
                }
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

    private boolean hasMessage(){
        return this.hasMessage;
    }

    private String getMessage(){
        return null;
    }

}
