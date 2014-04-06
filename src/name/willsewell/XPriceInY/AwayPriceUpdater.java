package name.willsewell.XPriceInY;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Text watcher to keep the money input field formatted correctly.
 */
public class AwayPriceUpdater {

    private EditText priceInput;
    private TextView priceOutput;
    private TextView homeLocation;
    private TextView awayLocation;
    private ArrayList<String[]> cPIList;

    public AwayPriceUpdater(EditText priceInput, TextView priceOutput, TextView homeLocation, TextView awayLocation,
                            ArrayList<String[]> cPIList) {
        this.priceInput = priceInput;
        this.priceOutput = priceOutput;
        this.homeLocation = homeLocation;
        this.awayLocation = awayLocation;
        this.cPIList = cPIList;
    }

    public void updateAwayPrice() {
        try {
            float homeCPI = getCPI(homeLocation.getText().toString());
            float awayCPI = getCPI(awayLocation.getText().toString());
            priceOutput.setText(Float.toString((getHomePrice() / homeCPI) * awayCPI));
        } catch (LocationNotFoundException e) {
            // They are probably still typing in the country
            priceOutput.setText("0.00");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private float getHomePrice() {
        String cleanString = priceInput.getText().toString().replaceAll("[£$,.]", "");
        return Math.round(Float.parseFloat(cleanString) * 100) / 10000;
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