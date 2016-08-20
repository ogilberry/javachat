import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientSessionThread extends Thread{

    private Socket socket;
    private BufferedReader reader;

    ClientSessionThread(Socket socket){
        this.socket = socket;
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
                }
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
