/**
 * Created by Jordan on 20-Aug-16.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {


    public static void main(String[] args){
        try {
            //create server on first available port
            ServerSocket server = new ServerSocket(0);
            final int port = server.getLocalPort();

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
