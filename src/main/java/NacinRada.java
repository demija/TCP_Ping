/**
 * Created by adem on 5/25/17.
 */
public class NacinRada {
    public boolean pitcher;
    public boolean catcher;

    public NacinRada(String nacinRada){
        if(nacinRada.equalsIgnoreCase("-p")){
            this.pitcher = true;
        } else if(nacinRada.equalsIgnoreCase("-c")){
            this.catcher = true;
        }
    }
}
