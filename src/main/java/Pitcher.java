import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Created by adem on 5/26/17.
 */
public class Pitcher extends Thread {
    private int port;
    private int messageRate;
    private int messageSize;
    private String hostname;
    public static List<Poruka> poslanePoruke;

    public Pitcher(PitcherCLIParameters params){
        this.port = params.port;
        this.messageRate = params.messageRate;
        this.messageSize = params.messageSize;
        this.hostname = params.hostname.get(0);
        this.poslanePoruke = new LinkedList<>();
        start();
    }

    public void run(){
        ClientThread ct = new ClientThread(hostname, port, getMessageSize());
        Timer timerSendMsg = new Timer();
        timerSendMsg.scheduleAtFixedRate(ct, 1000, 1000/messageRate);

        Statistika s = new Statistika(messageRate);
        Timer displayStat = new Timer();
        displayStat.scheduleAtFixedRate(s, 1000, 1000);
    }

    public int getMessageSize(){
        return messageSize;
    }
}
