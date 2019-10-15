package firbase.go.beruniy.view_setup

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.widget.DatePicker
import android.widget.EditText
import firbase.go.beruniy.utils.DateUtil
import firbase.go.beruniy.R
import java.util.*

class MyDateTimePicker(public var et: EditText) : DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        var c = Calendar.getInstance()
        c.set(year, monthOfYear, dayOfMonth, 0, 0)
        var date = c.time
        var result = DateUtil().format(date, DateUtil().FORMAT_AS_DATE)
        et.setText(result)
    }

    fun parse(s: String): Calendar {
        var date = DateUtil().parse(s)
        var c = Calendar.getInstance()
        if (date != null)
            c.time = date
        return c
    }

    fun show(clearButton: Boolean) {
        var c = parse(et.text.toString())

        var year = c.get(Calendar.YEAR)
        var month = c.get(Calendar.MONTH)
        var day = c.get(Calendar.DAY_OF_MONTH)

        var context = et.context
        var dp = DatePickerDialog(
            context, R.style.AppCompatAlertDialogStyle,
            MyDateTimePicker(et), year, month, day
        )

        if (clearButton) {
            dp.setButton(DialogInterface.BUTTON_NEUTRAL, context.getString(R.string.clear)) { dialog, _ ->
                et.setText("")
                dialog.dismiss()
            }
            dp.show()
        }
    }
}