import java.net.Socket;

/**
 * Created by Jordan on 20-Aug-16.
 */
public class ClientSessionThread extends Thread{

    private Socket socket;

    ClientSessionThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        
    }

}
