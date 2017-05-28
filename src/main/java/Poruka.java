
/**
 * Created by adem on 5/27/17.
 */
public class Poruka {
    private Long idPoruke;
    private Long timeStampP;
    private Long timeStampC;
    private Long timeStampPR;
    private String tekstPoruke;

    public Poruka(){
        this.idPoruke = null;
        this.timeStampP = null;
        this.timeStampC = null;
        this.timeStampPR = null;
        this.tekstPoruke = null;
    }

    public void setIdPoruke(long idPoruke){
        this.idPoruke = idPoruke;
    }

    public long getIdPoruke(){
        return this.idPoruke;
    }

    public void setTimeStampP(long timeStamp){
        this.timeStampP = timeStamp;
    }

    public long getTimeStampP(){
        return this.timeStampP;
    }

    public void setTimeStampC(long timeStampC){
        this.timeStampC = timeStampC;
    }

    public long getTimeStampC(){
        return this.timeStampC;
    }

    public void setTimeStampPR(long timeStampPR){
        this.timeStampPR = timeStampPR;
    }

    public long getTimeStampPR(){
        return this.timeStampPR;
    }

    public void setTekstPoruke(String tekstPoruke){
        this.tekstPoruke = tekstPoruke;
    }

    public String getTekstPoruke(){
        return this.tekstPoruke;
    }
}
