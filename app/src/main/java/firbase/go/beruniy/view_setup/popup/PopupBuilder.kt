package firbase.go.beruniy.view_setup.popup

import android.content.Context
import android.view.Menu
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.PopupMenu
import firbase.go.beruniy.view_setup.DialogBuilder
import firbase.go.beruniy.view_setup.listener.Command
import firbase.go.beruniy.view_setup.listener.CommandFacade
import firbase.go.beruniy.view_setup.listener.CommandPopup
import java.util.*

class PopupBuilder {
    private val options: MutableList<PopupItem> =
        ArrayList()

    private fun optionPrivate(
        name: Any,
        command: Command
    ): PopupBuilder {
        options.add(PopupItem(name, command))
        return this
    }

    fun option(
        name: CharSequence,
        command: Command
    ): PopupBuilder {
        return optionPrivate(name, command)
    }

    fun option(@StringRes stringId: Int, command: Command): PopupBuilder {
        return optionPrivate(stringId, command)
    }

    fun <T> option(
        values: Collection<T>,
        command: CommandFacade<T>
    ): PopupBuilder {
        for (value in values) {
            optionPrivate(
                command.getName(value),
                object : Command {
                    override fun apply() {
                        command.apply(value)
                    }
                })
        }
        return this
    }

    fun <T> option(
        values: Array<T>,
        command: CommandFacade<T>
    ): PopupBuilder {
        return option(listOf(*values), command)
    }

    fun show(view: View, command: CommandPopup): PopupMenu {
        val context = view.context
        val popup =
            PopupMenu(context, view)
        val menu = popup.menu
        menu.clear()
        var running: Boolean
        do {
            running = command.populateMenu(menu)
        } while (running)
        popup.show()
        return popup
    }

    fun show(view: View): PopupMenu {
        val context = view.context
        val popupMenu = show(view, object : CommandPopup {
            var position = 0
            override fun populateMenu(menu: Menu?): Boolean {
                if (options.size > 0 && position < options.size) {
                    val item = options[position]
                    item.id = position
                    menu!!.add(
                        Menu.NONE,
                        item.id,
                        Menu.NONE,
                        getString(context, item.title)
                    )
                    position++
                    return position < options.size
                }
                return false
            }
        })
        popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            val found = options[item.itemId]
            if (found != null) {
                if (found.command != null) {
                    found.command.apply()
                }
                return@OnMenuItemClickListener true
            }
            false
        })
        return popupMenu
    }

    private fun getString(
        context: Context,
        value: Any?
    ): CharSequence? {
        if (value == null) {
            return null
        }
        return if (value is Int) {
            context.getString((value as Int?)!!)
        } else value as CharSequence?
    }
}