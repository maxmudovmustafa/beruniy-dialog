package firbase.go.beruniy.view_setup

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.widget.DatePicker
import android.widget.EditText
import firbase.go.beruniy.R
import firbase.go.beruniy.utils.DateUtil
import java.util.*

class MyDateTimePicker(var et: EditText) : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        val c = Calendar.getInstance()
        c.set(year, monthOfYear, dayOfMonth, 0, 0)
        val date = c.time
        val result = DateUtil().format(date, DateUtil().FORMAT_AS_DATE)
        et.setText(result)
    }

    fun parse(s: String): Calendar {
        val date = DateUtil().parse(s)
        val c = Calendar.getInstance()
        if (date != null)
            c.time = date
        return c
    }

    fun show(clearButton: Boolean) {
        val c = parse(et.text.toString())

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val context = et.context
        val dp = DatePickerDialog(
            context, R.style.AppCompatAlertDialogStyle,
            MyDateTimePicker(et), year, month, day
        )

        if (clearButton) {
            dp.setButton(
                DialogInterface.BUTTON_NEUTRAL,
                context.getString(R.string.clear)
            ) { dialog, _ ->
                et.setText("")
                dialog.dismiss()
            }
            dp.show()
        }
    }
}