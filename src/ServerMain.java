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
            int port = server.getLocalPort();
            System.out.println("Server started on port " + Integer.toString(port));
            //loop for connections and accept them
            while(true){
                Socket newSocket = server.accept();
                System.out.println("Connection accepted from " + newSocket.getInetAddress().getHostName());
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
