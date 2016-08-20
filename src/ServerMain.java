/**
 * Created by Jordan on 20-Aug-16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMain {

    private static ArrayList<String> messages;

    public static void main(String[] args){
        try {
            //create server on first available port
            ServerSocket server = new ServerSocket(0);
            int port = server.getLocalPort();
            System.out.println("Server started on port " + Integer.toString(port));

            //an array of PrintWriters to send messages to the clients
            ArrayList<PrintWriter> writers = new ArrayList<PrintWriter>();
            messages = new ArrayList<String>();

            //loop for connections and accept them
            while(true){
                Socket newSocket = server.accept();
                System.out.println("Connection accepted from " + newSocket.getInetAddress().getHostName());

                //get message from the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
                String message = reader.readLine();
                System.out.println("A client says: " + message);
                messages.add(message);
                printAllMessages();

                //send message to the client
                PrintWriter writer = new PrintWriter(newSocket.getOutputStream(), true);
                writer.println("Welcome! From the server");
                writers.add(writer);
            }

        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static void printAllMessages(){
        System.out.println("All messages: ");
        if(messages==null){
            return;
        }
        for(String message : messages){
            System.out.println(message);
        }
    }

    private static ArrayList<String> getMessages(){
        return messages;
    }

}
