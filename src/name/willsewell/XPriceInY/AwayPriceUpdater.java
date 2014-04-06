package name.willsewell.XPriceInY;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Text watcher to keep the money input field formatted correctly.
 */
public class AwayPriceUpdater {

    private TextView priceOutput;
    private TextView homeLocation;
    private TextView awayLocation;
    private ArrayList<String[]> cPIList;

    public AwayPriceUpdater(TextView priceOutput, TextView homeLocation, TextView awayLocation,
                            ArrayList<String[]> cPIList) {
        this.priceOutput = priceOutput;
        this.homeLocation = homeLocation;
        this.awayLocation = awayLocation;
        this.cPIList = cPIList;
    }

    public void updateAwayPrice(float homePrice) {
        try {
            float homeCPI = getCPI(homeLocation.getText().toString());
            float awayCPI = getCPI(awayLocation.getText().toString());
            priceOutput.setText(Float.toString((homePrice / homeCPI) * awayCPI));
        } catch (NullPointerException | LocationNotFoundException e) {
            e.printStackTrace();
        }
    }

    private float getCPI(String loc) throws LocationNotFoundException {
        for (String[] aCPIList : cPIList) {
            if (loc.equals(aCPIList[0])) {
                return Float.parseFloat(aCPIList[1]);
            }
        }
        throw new LocationNotFoundException();
    }
}