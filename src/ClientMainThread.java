import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientMainThread extends Thread {

    private BufferedReader reader;
    private ArrayList<String> messages;

    ClientMainThread(BufferedReader reader, ArrayList<String> messages){
        this.reader = reader;
        this.messages = messages;
    }

    @Override
    public void run(){
        try {
            System.out.println("ClientMainThread started!");
            while(true) {
                //loop for messages from the server
                if (reader.ready()) {
                    String message = reader.readLine();
                    System.out.println(message);
                    messages.add(message);
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

}
