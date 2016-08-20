/**
 * Created by Jordan on 20-Aug-16.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {


    public static void main(String[] args){
        try {
            //create server on first available port
            ServerSocket server = new ServerSocket(0);
            int port = server.getLocalPort();
            System.out.println("Server started on port " + Integer.toString(port));

            //an array of PrintWriters to send messages to the clients
            ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();

            //loop for connections and accept them
            while(true){
                Socket newSocket = server.accept();
                System.out.println("Connection accepted from " + newSocket.getInetAddress().getHostName());
                PrintWriter writer = new PrintWriter(newSocket.getOutputStream(), true);
                writer.println("Welcome! From the server");
                writers.add(writer);
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

}
