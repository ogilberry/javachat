import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientMain {

    public static void main(String[] args){

        if(args.length!=2){
            System.out.println("Usage: ClientMain <host> <port>");

            try{
                //connect to the server
                Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
                //reader to read lines of text from the server
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                //loop for messages from the server
                while(true){
                    if(reader.ready()){
                        System.out.println(reader.readLine());
                    }
                }

            }catch(IOException ioe){
                ioe.printStackTrace();
            }

        }

    }

}
