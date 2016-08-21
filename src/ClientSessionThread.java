import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientSessionThread extends Thread{

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter myWriter;
    private ArrayList<PrintWriter> writers;

    ClientSessionThread(Socket socket, PrintWriter myWriter, ArrayList<PrintWriter> writers){
        this.socket = socket;
        this.writers = writers;
        this.myWriter = myWriter;
    }

    private void sendToAll(String message){
        //sends the string message on every writer in writers. Every clients reader will receive this.
        System.out.println(writers + ", mine is " + myWriter);
        for(PrintWriter writer : writers){
            //messages should come in the format "username: message". check this is the case, then
            //replace the username with 'You' for the writer that matches myWriter
            String finalMessage = message;
            if(message.contains(":") && myWriter==writer){
                finalMessage = "You " + message.substring(message.indexOf(":"));
            }
            else{
                finalMessage = message;
            }
            writer.println(finalMessage);
        }
    }

    @Override
    public void run() {

        try {
            //receives messages from the client I think?
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //loop for messages from this client. Prints it to the console the server is running on
            while(true){
                if(reader.ready()){
                    String message = reader.readLine();
                    System.out.println(message);
                    sendToAll(message);
                }
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
