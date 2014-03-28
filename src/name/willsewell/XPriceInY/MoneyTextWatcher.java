package name.willsewell.XPriceInY;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.NumberFormat;

/**
 * Text watcher to keep the money input field formatted correctly.
 */
public class MoneyTextWatcher implements TextWatcher{

    private EditText priceInput;
    private String current = "";

    public MoneyTextWatcher(EditText priceInput) {
        this.priceInput = priceInput;
    }

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
}