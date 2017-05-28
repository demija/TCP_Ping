import java.io.*;
import java.net.Socket;

/**
 * Created by adem on 5/26/17.
 */
public class ServerThread extends Thread {
    private Socket socket;
    private DataInputStream dIn;
    private DataOutputStream dOut;
    private Poruka primljenaPoruka;

    public ServerThread(Socket socket){
        this.socket = socket;
        this.primljenaPoruka = null;
        start();
    }

    @Override
    public void run(){
        try {
            synchronized (socket){
                this.dIn = new DataInputStream(socket.getInputStream());
                this.dOut = new DataOutputStream(socket.getOutputStream());
                primljenaPoruka = new Poruka();

                boolean done = false;

                while(!done) {
                    byte messageType = dIn.readByte();

                    switch(messageType)
                    {
                        case 1:
                            primljenaPoruka.setIdPoruke(dIn.readLong());
                            System.out.println("\nBroj poruke: " + primljenaPoruka.getIdPoruke());
                            break;
                        case 2:
                            primljenaPoruka.setTimeStampP(dIn.readLong());
                            System.out.println("Time stamp [pitcher]: " + primljenaPoruka.getTimeStampP());
                            break;
                        case 3:
                            primljenaPoruka.setTekstPoruke(dIn.readUTF());
                            System.out.println("Tekst poruke: " + primljenaPoruka.getTekstPoruke());
                            break;
                        default:
                            done = true;
                    }
                }

                primljenaPoruka.setTimeStampC(System.currentTimeMillis());                      // timestamp catcher-a na primljenoj poruci
                System.out.println("Time stamp [catcher]: " + primljenaPoruka.getTimeStampC());

                // Vracanje primljene poruke ka pitcheru. Poruka ima isti ID, kao i tekst primljene poruke, razlika je u vrijednosti timeStamp-a.
                dOut.writeByte(1);
                dOut.writeLong(primljenaPoruka.getIdPoruke());                                  // slanje ID poruke

                dOut.writeByte(2);
                dOut.writeLong(primljenaPoruka.getTimeStampC());                                // slanje timestamp-a catchera

                dOut.writeByte(3);
                dOut.writeUTF(primljenaPoruka.getTekstPoruke());                                // slanje istog sadrzaja poruke

                dOut.writeByte(-1);
                dOut.flush();

                socket.close();
                socket.notify();
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
