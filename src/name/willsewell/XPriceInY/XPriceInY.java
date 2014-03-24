package name.willsewell.XPriceInY;

import android.app.Activity;
import android.os.Bundle;
import au.com.bytecode.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class XPriceInY extends Activity {

    private Properties properties;
    private List cPIList;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        properties = new Properties();
        try {
            properties.load(new BufferedReader(new FileReader("config.properties")));
            cPIList = parseCSV(properties.getProperty("cpi-csv-path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List parseCSV(String fname) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(fname));
        return reader.readAll();
    }
}
