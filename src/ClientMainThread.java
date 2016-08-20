import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientMainThread extends Thread {

    private BufferedReader reader;

    ClientMainThread(BufferedReader reader){
        this.reader = reader;
    }

    @Override
    public void run(){
        try {
            while(true) {
                if (reader.ready()) {
                    String message = reader.readLine();
                    System.out.println(message);
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

}
