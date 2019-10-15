package firbase.go.beruniy.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtil {

    private static final ThreadLocal<DecimalFormat> MONEY_FORMAT = new ThreadLocal<DecimalFormat>() {
        @Override
        protected DecimalFormat initialValue() {
            DecimalFormat df = new DecimalFormat();
            df.setMinimumFractionDigits(0);
            df.setMaximumFractionDigits(2);
            df.setGroupingUsed(true);
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
            return df;
        }
    };

    public static String formatMoney(BigDecimal amount) {
        if (amount == null) amount = BigDecimal.ZERO;
        return MONEY_FORMAT.get().format(amount.setScale(2, BigDecimal.ROUND_DOWN));
    }

    public static Integer parse(String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        return Integer.parseInt(s);
    }

    public static Integer tryParse(String s) {
        try {
            return parse(s);
        } catch (Exception e) {
            return null;
        }
    }
}
