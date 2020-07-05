package firbase.go.beruniy.view_setup.listener

import android.view.Menu

interface CommandPopup {
    fun populateMenu(menu: Menu?): Boolean
}