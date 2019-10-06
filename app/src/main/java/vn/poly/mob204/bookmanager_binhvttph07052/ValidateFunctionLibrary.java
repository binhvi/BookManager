package vn.poly.mob204.bookmanager_binhvttph07052;

import android.content.Context;
import android.widget.Toast;

public class ValidateFunctionLibrary {
    Context context;
    public static final boolean FAIL = false;
    public static final int SQLITE_CONSTRANT_EXCEPTION_ID = -999;

    public ValidateFunctionLibrary(Context context) {
        this.context = context;
    }

    public boolean isTextEmpty(String text, String field) {
        if (text.isEmpty()) {
            Toast.makeText(
                    context,
                    field + " không được để trống",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }
        return false;
    }

    public boolean canNotParseToInt(String text, String field) {
        try {
            Integer.parseInt(text);
            return false;
        } catch (NumberFormatException exc) {
            exc.printStackTrace();
            Toast.makeText(
                    context,
                    field+" phải là số nguyên",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }
    }

    public boolean canNotParseToDouble(String text, String field) {
        try {
            Double.parseDouble(text);
            return false;
        } catch (NumberFormatException exc) {
            exc.printStackTrace();
            Toast.makeText(
                    context,
                    field+ " phải là một số",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }
    }
}
