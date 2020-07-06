package firbase.go.beruniy.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import firbase.go.beruniy.R
import firbase.go.beruniy.view_setup.DialogBuilder
import firbase.go.beruniy.view_setup.MyDateTimePicker
import firbase.go.beruniy.view_setup.MyTimePickerDialog
import firbase.go.beruniy.view_setup.bottom.BottomSheetDialog
import firbase.go.beruniy.view_setup.listener.Command
import firbase.go.beruniy.view_setup.listener.CommandFacade
import firbase.go.beruniy.view_setup.listener.CommandPopup
import firbase.go.beruniy.view_setup.popup.PopupBuilder


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

        fun <T> popup(
            view: View,
            values: Collection<T>,
            command: CommandFacade<T>
        ) {
            popup().option(values, command).show(view)
        }

        fun popup(view: View, command: CommandPopup) {
            popup().show(view, command)
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

        //----------------------------------------------------------------------------------------------

        //----------------------------------------------------------------------------------------------
        fun bottomSheet(): BottomSheetDialog.Builder {
            return BottomSheetDialog.Builder()
        }

        //----------------------------------------------------------------------------------------------

        fun createBottomDialog(view: View, activity: Activity): Dialog {
            val dialog = Dialog(activity, R.style.MaterialDialogSheet)
            dialog.setContentView(view)
            dialog.setCancelable(true)
            dialog.window?.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            dialog.window?.setGravity(Gravity.BOTTOM)
            return dialog
        }


        fun bottomSheetDialog(context: Context, view: View): com.google.android.material.bottomsheet.BottomSheetDialog {
            val bottomSheetDialog =
                com.google.android.material.bottomsheet.BottomSheetDialog(context)
            bottomSheetDialog.setContentView(view)
            val bottomSheetBehavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(view.parent as View)
            bottomSheetBehavior.peekHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 100f, context.resources.displayMetrics
            ).toInt()

            return bottomSheetDialog
        }

        //----------------------------------------------------------------------------------------------
        fun <T> bottomSheet(
            activity: FragmentActivity,
            title: CharSequence,
            values: Collection<T>,
            command: CommandFacade<T>
        ) {
            bottomSheet().title(title).option(values, command).show(activity)
        }

        fun <T> bottomSheet(
            activity: FragmentActivity?, @StringRes resId: Int,
            values: Collection<T>?,
            command: CommandFacade<T>?
        ) {
            bottomSheet().title(resId).option(values, command).show(activity)
        }

        fun <T> bottomSheet(
            activity: FragmentActivity?,
            values: Collection<T>?,
            command: CommandFacade<T>?
        ) {
            bottomSheet().option(values, command).show(activity)
        }

        //----------------------------------------------------------------------------------------------

        //----------------------------------------------------------------------------------------------
        fun <T> bottomSheet(
            activity: FragmentActivity,
            title: CharSequence,
            values: Array<T>,
            command: CommandFacade<T>
        ) {
            bottomSheet().title(title).option(values, command).show(activity)
        }

        fun <T> bottomSheet(
            activity: FragmentActivity?, @StringRes resId: Int,
            values: Array<T>?,
            command: CommandFacade<T>?
        ) {
            bottomSheet().title(resId).option(values, command).show(activity)
        }

        fun <T> bottomSheet(
            activity: FragmentActivity?,
            values: Array<T>?,
            command: CommandFacade<T>?
        ) {
            bottomSheet().option(values, command).show(activity)
        }

        //----------------------------------------------------------------------------------------------

        //----------------------------------------------------------------------------------------------
//        fun bottomSheet(
//            activity: FragmentActivity,
//            title: CharSequence,
//            adapter: RecyclerView.Adapter
//        ) {
//            bottomSheet().title(title).adapter(adapter).show(activity)
//        }
//
//        fun bottomSheet(
//            activity: FragmentActivity?, @StringRes resId: Int,
//            adapter: RecyclerView.Adapter
//        ) {
//            bottomSheet().title(resId).adapter(adapter).show(activity)
//        }
//
//        fun <T> bottomSheet(
//            activity: FragmentActivity,
//            adapter: RecyclerView.Adapter<T>
//        ) {
//            bottomSheet().adapter(adapter).show(activity)
//        }

    }
}