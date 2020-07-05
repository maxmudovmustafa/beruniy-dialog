package firbase.go.beruniy.view_setup.popup

import firbase.go.beruniy.view_setup.listener.Command

data class PopupItem(val title: Any, val command: Command) {
    @JvmField
    var id = 0

}