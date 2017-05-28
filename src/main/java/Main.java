import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by adem on 5/25/17.
 */
public class Main {
    private static final CatcherCLIParameters catcherArgs = new CatcherCLIParameters();
    private static final PitcherCLIParameters pitcherArgs = new PitcherCLIParameters();
    private static final Pattern ipAddressPattern = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public static void main(String[] args){
        if(args.length > 0){
            final NacinRada nRada = new NacinRada(args[0]);
            args = Arrays.copyOfRange(args, 1, args.length);

            if(nRada.pitcher){
                handlePitcher(args);
            } else if(nRada.catcher){
                handleCatcher(args);
            } else{
                showError();
            }
        } else{
            showError();
        }
    }

    private static void handlePitcher(String[] args) {
        JCommander jCommander = new JCommander(pitcherArgs);
        jCommander.setProgramName("_PITCHER_");

        try{
            jCommander.parse(args);

            if(pitcherArgs.isHelp()){
                showUsage(jCommander);
            }

            if(validatePitcherParams(pitcherArgs)){
                System.out.print("\n.: PITCHER :.");
                System.out.println(pitcherArgs);

                Pitcher pitcher = new Pitcher(pitcherArgs);
            } else {
                showUsage(jCommander);
            }
        } catch (ParameterException exception){
            System.out.println(exception.getMessage());
            showUsage(jCommander);
        }
    }

    private static boolean validatePitcherParams(PitcherCLIParameters pitcherArgs) {
        boolean valid = true;

        if(pitcherArgs.port < 0 || pitcherArgs.port > 65535){
            valid = false;
        }

        if(pitcherArgs.messageRate < 0){
            valid = false;
        }

        if(pitcherArgs.messageSize < 50 || pitcherArgs.messageSize > 3000){
            valid = false;
        }

        return valid;
    }

    private static void handleCatcher(String[] args) {
        JCommander jCommander = new JCommander(catcherArgs);
        jCommander.setProgramName("_CATCHER_");

        try {
            jCommander.parse(args);

            if(catcherArgs.isHelp()){
                showUsage(jCommander);
            }

            if(validateCatcherParams(catcherArgs)){
                System.out.print("\n.: CATCHER :.");
                System.out.println(catcherArgs);

                Catcher catcher = new Catcher(catcherArgs);
            } else{
                showUsage(jCommander);
            }
        } catch (ParameterException exception){
            System.out.println(exception.getMessage());
            showUsage(jCommander);
        }
    }

    private static boolean validateCatcherParams(CatcherCLIParameters mainArgs) {
        boolean valid = true;

        if(mainArgs.port < 0 || mainArgs.port > 65535){
            valid = false;
        }

        try{
            if(mainArgs.ipAddress == null){
                valid = false;
            } else {
                if(!ipAddressPattern.matcher(mainArgs.ipAddress).matches()){
                    valid = false;
                }
            }
        } catch (NumberFormatException exception){
            valid = false;
        }

        return valid;
    }

    private static void showUsage(JCommander jCommander) {
        jCommander.usage();
        System.exit(0);
    }

    private static void showError() {
        throw new IllegalArgumentException("\nPotrebno je unijeti parametar" + "\n '-p' za PITCHER-a ili" + "\n '-c' za CATCHER-a");
    }
}
