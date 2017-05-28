import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adem on 5/26/17.
 */
public class PitcherCLIParameters {
    @Parameter(names = "-help", help = true, description = "Prikazi pomoc")
    public boolean help;

    @Parameter(names = "-port", required = true, description = "TCP socket port koji ce se koristiti za connect")
    public Integer port;

    @Parameter(names = "-mps", description = "Brzina slanja izrazena u 'messages per second'")
    public Integer messageRate = 1;

    @Parameter(names = "-size", description = "Duzina poruke min:50; max:3000")
    public Integer messageSize = 300;

    @Parameter(required = true, description = "Ime racunala na kojem je pokrenut Catcher")
    public List<String> hostname = new ArrayList<String>();

    public boolean isHelp(){
        return help;
    }

    @Override
    public String toString(){
        return "\n- port\t= " + port +
                "\n- mps\t= " + messageRate +
                "\n- size\t= " + messageSize;
    }
}
