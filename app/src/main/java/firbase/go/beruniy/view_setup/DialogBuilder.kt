package firbase.go.beruniy.view_setup

import android.app.Activity
import android.app.AlertDialog
import android.widget.ArrayAdapter
import firbase.go.beruniy.utils.Command
import firbase.go.beruniy.R
import firbase.go.beruniy.collection.MyArray
import java.util.ArrayList

class DialogBuilder {
    private var mTitle: Any? = null
    private var mMessage: Any? = null

    private var positiveName: Any? = null
    private var positiveCommand: Command? = null

    private var negativeName: Any? = null
    private var negativeCommand: Command? = null

    private var neutralName: Any? = null
    private var neutralCommand: Command? = null

    private var optionsName: ArrayList<Any>? = null
    private var optionsCommands: ArrayList<Command>? = null


    private var cancelable: Boolean = true

    fun cancelable(value: Boolean): DialogBuilder {
        cancelable = value
        return this
    }

    fun title(value: CharSequence): DialogBuilder {
        mTitle = value
        return this
    }

    fun title(value: Int): DialogBuilder {
        mTitle = value
        return this
    }

    fun message(value: CharSequence): DialogBuilder {
        mMessage = value
        return this
    }

    fun positive(value: CharSequence, command: Command): DialogBuilder {
        positiveName = value
        positiveCommand = command
        return this
    }

    fun positive(value: Int, command: Command): DialogBuilder {
        positiveName = value
        positiveCommand = command
        return this
    }


    fun positive(command: Command): DialogBuilder {
        positiveName = null
        positiveCommand = command
        return this
    }


    fun negative(value: CharSequence, command: Command): DialogBuilder {
        negativeName = value
        negativeCommand = command
        return this
    }

    fun negative(value: Int, command: Command): DialogBuilder {
        negativeName = value
        negativeCommand = command
        return this
    }


    fun negative(command: Command): DialogBuilder {
        negativeName = null
        negativeCommand = command
        return this
    }

    fun neutral(value: CharSequence, command: Command): DialogBuilder {
        neutralName = value
        neutralCommand = command
        return this
    }

    fun neutral(value: Int, command: Command): DialogBuilder {
        neutralName = value
        neutralCommand = command
        return this
    }


    fun neutral(command: Command): DialogBuilder {
        neutralName = null
        neutralCommand = command
        return this
    }

    private fun optionPrivate(name: Any, command: Command): DialogBuilder {
        if (optionsName == null) {
            optionsName = ArrayList<Any>()
            optionsCommands = ArrayList<Command>()
        }
        optionsName!!.add(name)
        optionsCommands!!.add(command)
        return this
    }

    fun option(name: CharSequence, command: Command): DialogBuilder {
        return optionPrivate(name, command)
    }

    fun option(stringId: Int, command: Command): DialogBuilder {
        return optionPrivate(stringId, command)
    }

    fun <T> option(values: MyArray<T>, command: CommandFacade<T>): DialogBuilder {
        for (value in values) {
            optionPrivate(command.getName(value), object : Command {
                override fun apply() {
                    command.apply(value)
                }
            })
        }
        return this
    }

    fun show(activity: Activity) {
        fixButtonNames()
        val b = AlertDialog.Builder(activity)
        b.setCancelable(cancelable)

        b.setTitle(getString(activity, mTitle))
        if (mMessage != null) {
            b.setMessage(getString(activity, mMessage))
        }

        if (optionsName != null) {
            val ns = ArrayList<CharSequence>()
            for (o in optionsName!!) {
                ns.add(getString(activity, o)!!)
            }
            val adapter = ArrayAdapter(activity, android.R.layout.select_dialog_item, ns)
            b.setAdapter(
                adapter
            ) { dialog, which -> optionsCommands!!.get(which).apply() }
        }

        if (positiveCommand != null) {
            b.setPositiveButton(
                getString(activity, positiveName)
            ) { dialog, which -> positiveCommand!!.apply() }
        }
        if (negativeCommand != null) {
            b.setNegativeButton(
                getString(activity, negativeName)
            ) { dialog, which -> negativeCommand!!.apply() }
        }
        if (neutralCommand != null) {
            b.setNeutralButton(
                getString(activity, neutralName)
            ) { _, _ -> neutralCommand!!.apply() }
        }

        try {
            b.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getString(activity: Activity, value: Any?): CharSequence? {
        if (value == null) {
            return null
        }
        return if (value is Int) {
            activity.getString((value as Int?)!!)
        } else value as CharSequence?
    }

    private fun setButtonNames(positiveId: Int, negativeId: Int, neutralId: Int) {
        if (positiveName == null) {
            this.positiveName = positiveId
        }
        if (negativeName == null) {
            this.negativeName = negativeId
        }
        if (neutralName == null) {
            this.neutralName = neutralId
        }
    }

    private fun fixButtonNames() {
        if (positiveCommand != null && negativeCommand == null && neutralCommand == null) {
            setButtonNames(R.string.ok, 0, 0)
        } else if (positiveCommand == null && negativeCommand != null && neutralCommand == null) {
            setButtonNames(0, R.string.cancel, 0)
        } else if (positiveCommand == null && negativeCommand == null && neutralCommand != null) {
            setButtonNames(0, 0, R.string.cancel)
        } else {
            setButtonNames(
                R.string.yes,
                R.string.no,
                R.string.cancel
            )
        }
    }

    interface CommandFacade<T> {

        fun getName(`val`: T): CharSequence

        fun apply(`val`: T)

    }
}