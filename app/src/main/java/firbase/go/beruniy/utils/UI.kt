package firbase.go.beruniy.utils

import android.app.Activity
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import firbase.go.beruniy.R
import firbase.go.beruniy.view_setup.DialogBuilder
import firbase.go.beruniy.view_setup.MyDateTimePicker
import firbase.go.beruniy.view_setup.MyTimePickerDialog
import firbase.go.beruniy.view_setup.PopupBuilder

class UI {
    companion object {
        fun makeDateTimePicker(et: EditText, clearButton: Boolean) {
            et.hint = et.context.getString(R.string.date_format)
            et.setOnLongClickListener(null)
            et.keyListener = null
            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mygowlib_datepicker, 0)
            et.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    MyDateTimePicker(et).show(clearButton)
                }
                return@setOnTouchListener false
            }
        }

        fun makeTimePicker(et: EditText) {
            et.hint = et.context.getString(R.string.date_format)
            et.setOnLongClickListener(null)
            et.keyListener = null
            et.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.mygowlib_datepicker, 0)
            et.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    MyTimePickerDialog(et).show()
                }
                return@setOnTouchListener false
            }
        }

        fun dialog(): DialogBuilder {
            return DialogBuilder()
        }

        fun createDialog(
            context: Activity,
            layout: Int,
            cancelable: Boolean
        ) {

            val dialogBuilder = AlertDialog.Builder(context)
            val dialog = dialogBuilder.create()
            val view = context.layoutInflater.inflate(layout, null)


            dialog.setCancelable(cancelable)
            dialog.setView(view)
            dialog.setCanceledOnTouchOutside(cancelable)


            dialog.show()
        }

        fun popup(): PopupBuilder {
            return PopupBuilder()
        }

        fun alert(
            activity: Activity,
            title: CharSequence,
            message: CharSequence
        ) {
            dialog().title(title)
                .message(message)
                .negative(Util.NOOP)
                .show(activity)
        }

        fun confirm(
            activity: Activity,
            title: CharSequence,
            message: CharSequence,
            command: Command
        ) {
            dialog().title(title)
                .message(message)
                .negative(Util.NOOP)
                .show(activity)
        }
    }
}