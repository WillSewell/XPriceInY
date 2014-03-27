package name.willsewell.XPriceInY;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Properties;

public class XPriceInY extends Activity {

    private Properties properties;
    private ArrayList<String[]> cPIList;
    private String current = "";

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
        Spinner spinner = (Spinner) findViewById(R.id.homeSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        spinner.setAdapter(adapter);

        final EditText priceInput = (EditText) findViewById(R.id.priceInput);
        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    priceInput.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[£$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    priceInput.setText(formatted);
                    priceInput.setSelection(formatted.length());

                    priceInput.addTextChangedListener(this);
                }
            }
        });
    }
}
