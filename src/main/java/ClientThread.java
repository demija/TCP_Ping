import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by adem on 5/27/17.
 */
public class ClientThread extends TimerTask {
    private Socket socket;
    private String address;
    private int port;
    private int messageSize;

    public ClientThread(String hostname, int port, int messageSize){
        this.address = hostname;
        this.port = port;
        this.messageSize = messageSize;
    }

    @Override
    public void run(){
        try {
            InetAddress addr = InetAddress.getByName(address);
            socket = new Socket(addr, port);

            synchronized (socket){
                DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
                DataInputStream dIn = new DataInputStream(socket.getInputStream());

                Poruka poslanaPoruka = new Poruka();
                poslanaPoruka.setIdPoruke(getRandomNumber());
                poslanaPoruka.setTimeStampP(System.currentTimeMillis());

                // Slanje poruke catcher-u. ID poruke se generise kao random number.
                dOut.writeByte(1);
                dOut.writeLong(poslanaPoruka.getIdPoruke());                                    // slanje ID poruke

                dOut.writeByte(2);
                dOut.writeLong(poslanaPoruka.getTimeStampP());                                  // slanje timestamp-a

                dOut.writeByte(3);
                poslanaPoruka.setTekstPoruke(getRandomString(messageSize - dOut.size() - 3));
                dOut.writeUTF(poslanaPoruka.getTekstPoruke());                                  // slanje sadrzaja poruke

                dOut.writeByte(-1);
                dOut.flush();

                Poruka vracenaPoruka = new Poruka();

                boolean done = false;
                while(!done) {
                    byte messageType = dIn.readByte();

                    switch(messageType)
                    {
                        case 1:
                            vracenaPoruka.setIdPoruke(dIn.readLong());
                            break;
                        case 2:
                            if(vracenaPoruka.getIdPoruke() == poslanaPoruka.getIdPoruke()){
                                poslanaPoruka.setTimeStampC(dIn.readLong());
                            }
                            break;
                        case 3:
                            vracenaPoruka.setTekstPoruke(dIn.readUTF());
                            break;
                        default:
                            done = true;
                    }
                }

                poslanaPoruka.setTimeStampPR(System.currentTimeMillis());                       // timestamp poruke vrcene sa catcher-a
                Pitcher.poslanePoruke.add(poslanaPoruka);                                       // dodavanje poruke u listu poslanih poruka

                // update statistickih podataka sa podacima poruke - timestamp pitcher-a, timestamp catcher-a i timestamp nakon vracanja pitcheru
                Statistika.updateStatistic(poslanaPoruka.getTimeStampP(),
                        poslanaPoruka.getTimeStampC(),poslanaPoruka.getTimeStampPR());

                socket.close();
                socket.notify();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long getRandomNumber() {
        Random rnd = new Random();
        return (long) (rnd.nextDouble() * 1234567L);
    }

    private String getRandomString(int duzinaTeksta){
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < duzinaTeksta) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }
}
