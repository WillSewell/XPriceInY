package name.willsewell.XPriceInY;

import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Text watcher to keep the money input field formatted correctly.
 */
public class AwayPriceUpdater {

    private TextView priceOutput;
    private Spinner homeLocation;
    private Spinner awayLocation;
    private ArrayList<String[]> cPIList;

    public AwayPriceUpdater(TextView priceOutput, Spinner homeLocation, Spinner awayLocation,
                            ArrayList<String[]> cPIList) {
        this.priceOutput = priceOutput;
        this.homeLocation = homeLocation;
        this.awayLocation = awayLocation;
        this.cPIList = cPIList;
    }

    public void updateAwayPrice(float homePrice) {
        try {
            float homeCPI = getCPI(homeLocation.getSelectedItem().toString());
            float awayCPI = getCPI(awayLocation.getSelectedItem().toString());
            priceOutput.setText(Float.toString((homePrice / homeCPI) * awayCPI));
        } catch (LocationNotFoundException lNFE) {
            lNFE.printStackTrace();
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