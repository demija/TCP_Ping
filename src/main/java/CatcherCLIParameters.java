import com.beust.jcommander.Parameter;

/**
 * Created by adem on 5/25/17.
 */
public class CatcherCLIParameters {
    @Parameter(names = "-help", help = true, description = "Prikazi pomoc")
    public boolean help;

    @Parameter(names = "-port", required = true, description = "TCP socket port koji se koristi za listen")
    public Integer port;

    @Parameter(names = "-bind", required = true, description = "TCP socket bind IP addresa na kojoj ce biti pokrenut listen")
    public String ipAddress;

    public boolean isHelp(){
        return help;
    }

    @Override
    public String toString(){
        return "\n- port\t\t= " + port +
                "\n- ip_address\t= " + ipAddress;
    }
}
