package name.willsewell.XPriceInY;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.*;
import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class XPriceInY extends Activity {

    private Properties properties;
    private ArrayList<String[]> cPIList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // load the app config and load the CPI CSV file
        AssetManager am = getAssets();
        properties = new Properties();
        try {
            properties.load(getAssets().open("config.properties"));
            InputStream is = am.open(properties.getProperty("cpi-csv-path"));
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            cPIList = (ArrayList<String[]>) reader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // extract just the countries into their own array
        ArrayList<String> countries = new ArrayList<>();
        for (String[] item : cPIList) {
            countries.add(item[0]);
        }

        // create a new spinner to attach the array of countries to
        AutoCompleteTextView homeLocation = (AutoCompleteTextView) findViewById(R.id.homeInput);
        ArrayAdapter<String> homeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                countries);
        homeLocation.setAdapter(homeAdapter);


        // create a new spinner to attach the array of countries to
        AutoCompleteTextView awayLocation = (AutoCompleteTextView) findViewById(R.id.awayInput);
        ArrayAdapter<String> awayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                countries);
        awayLocation.setAdapter(awayAdapter);

        EditText priceInput = (EditText) findViewById(R.id.priceInput);

        TextView textView = (TextView) findViewById(R.id.priceOutput);
        AwayPriceUpdater awayPriceUpdater = new AwayPriceUpdater(priceInput, textView, homeLocation, awayLocation,
                cPIList);

        // add the listeners to keep the price updated when any of the parameters change
        CountryWatcher countryWatcher = new CountryWatcher(awayPriceUpdater);
        homeLocation.addTextChangedListener(countryWatcher);
        awayLocation.addTextChangedListener(countryWatcher);
        priceInput.addTextChangedListener(new HomePriceWatcher(priceInput, awayPriceUpdater));
    }
}
