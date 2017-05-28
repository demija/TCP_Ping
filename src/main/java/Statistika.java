import java.text.SimpleDateFormat;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by adem on 5/27/17.
 */
public class Statistika extends TimerTask {
    private long startTime = System.currentTimeMillis();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private int brojPorukaSec;
    private static double ukupnoVrijeme = 0;
    private static double prosjekKrugaVrijeme = 0;
    private static double vrijemePitcherCatcher = 0;
    private static double vrijemeCatcherPitcher = 0;

    public Statistika(int brojPorukaSec){
        this.brojPorukaSec = brojPorukaSec;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(500 * 1);

            long systemTime = System.currentTimeMillis();
            long difference = System.currentTimeMillis() - startTime;

            System.out.println("\nTrenutno vrijeme: " + sdf.format(systemTime) + " | Vrijeme izvrsavanja: " +
                    String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(difference),
                            TimeUnit.MILLISECONDS.toMinutes(difference) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(difference) % TimeUnit.MINUTES.toSeconds(1)) +
                    "\nUkupan broj poslanih poruka: " + Pitcher.poslanePoruke.size() + " (mps: " + brojPorukaSec + ")" +
                    "\n[Picher - Catcher - Picher](total): " + ukupnoVrijeme + " | [Picher - Catcher - Picher](avg): " + prosjekKrugaVrijeme / brojPorukaSec +
                    "\n[Picher - Catcher](avg): " + vrijemePitcherCatcher / brojPorukaSec + " | [Catcher - Picher](avg): " + vrijemeCatcherPitcher / brojPorukaSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetAll();
    }

    static void updateStatistic(long timeStampP, long timeStampC, long timeStampPR){
        ukupnoVrijeme += (timeStampPR - timeStampP);
        prosjekKrugaVrijeme = (timeStampPR - timeStampP);
        vrijemePitcherCatcher = (timeStampC - timeStampP);
        vrijemeCatcherPitcher = (timeStampPR - timeStampC);
    }

    private void resetAll(){
        prosjekKrugaVrijeme = 0;
        vrijemePitcherCatcher = 0;
        vrijemeCatcherPitcher = 0;
    }
}
