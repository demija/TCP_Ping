import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by adem on 5/25/17.
 */
public class Catcher extends Thread {
    private Socket socket;
    private String ipAddress;
    private int port;

    public Catcher(CatcherCLIParameters params){
        this.ipAddress = params.ipAddress;
        this.port = params.port;
        start();
    }

    public void run(){
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            ServerSocket ss = new ServerSocket(port, 50, address);

            while (true){
                socket = ss.accept();
                ServerThread st = new ServerThread(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
