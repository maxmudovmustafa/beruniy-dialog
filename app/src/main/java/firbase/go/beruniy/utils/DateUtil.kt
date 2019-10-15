package firbase.go.beruniy.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    val FORMAT_AS_NUMBER: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMdd", Locale.US)
        }
    }
    val FORMAT_AS_DATE: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd.MM.yyyy", Locale.US)
        }
    }
    val FORMAT_AS_DATETIME: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US)
        }
    }
    val YYYYMMDDHHMMSS: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("yyyyMMddHHmmss", Locale.US)
        }
    }
    val FORMAT_AS_WEEK_DATE: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
        override fun initialValue(): SimpleDateFormat {
            return SimpleDateFormat("EEEE, dd.MM.yyyy", Locale.getDefault())
        }
    }

    fun parse(s: String): Date? {
        return if (TextUtils.isEmpty(s)) {
            null
        } else {
            try {
                when (s.length) {
                    8 -> (FORMAT_AS_NUMBER.get() as SimpleDateFormat).parse(s)
                    10 -> (FORMAT_AS_DATE.get() as SimpleDateFormat).parse(s)
                    14 -> (YYYYMMDDHHMMSS.get() as SimpleDateFormat).parse(s)
                    else -> (FORMAT_AS_DATETIME.get() as SimpleDateFormat).parse(s)
                }
            } catch (var2: ParseException) {
                throw IllegalArgumentException("Date time format error:$s")
            }

        }
    }

    fun format(date: Date?, fmt: SimpleDateFormat?): String {
        try {
            return fmt!!.format(date)
        } catch (var3: Exception) {
            L_Log().log(var3)
        }
        return ""
    }

    fun format(date: Date, fmt: ThreadLocal<SimpleDateFormat>): String {
        return format(date, fmt.get())
    }

    fun convert(s: String, fmt: SimpleDateFormat?): String {
        return format(parse(s), fmt)
    }

    fun convert(s: String, fmt: ThreadLocal<SimpleDateFormat>): String {
        return convert(s, fmt.get())
    }
}