package firbase.go.beruniy.view_setup

import android.app.TimePickerDialog
import android.widget.EditText
import android.widget.TimePicker
import firbase.go.beruniy.utils.L_Log
import firbase.go.beruniy.R
import java.util.*

class MyTimePickerDialog(private var et: EditText) : TimePickerDialog.OnTimeSetListener {
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        et.setText(format(hourOfDay, minute))
    }

    fun show() {
        var c = Calendar.getInstance()
        var hour = c.get(11)
        var minute = c.get(12)
        var time = et.text.toString()
        if (time.length > 0) {
            hour = hourOfTime(time)
            minute = minuteOfTime(time)
        }

        var tp =
            TimePickerDialog(et.context,
                R.style.AppCompatAlertDialogStyle,
                MyTimePickerDialog(et), hour, minute, true)
        tp.show()
    }

    private fun padTime(time: Int): String {
        if (time >= 0) {

            var r = time.toString()
            when (r.length) {
                1 -> return "0$r"
                2 -> return r
            }
        }
        L_Log().log(MyTimePickerDialog::class.java.name)
        return ""
    }

    private fun format(hourOfDay: Int, minute: Int): String {
        return padTime(hourOfDay) + ":" + padTime(minute)
    }

    private fun hourOfTime(time: String): Int {
        return Integer.parseInt(time.substring(0, 2))
    }

    private fun minuteOfTime(time: String): Int {
        return Integer.parseInt(time.substring(3))
    }
}