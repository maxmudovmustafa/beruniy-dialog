package firbase.go.beruniy.view_setup.listener

import android.view.View

interface Callback {
    fun onStateChanged(bottomSheet: View, newState: Int): Boolean
    fun onSlide(bottomSheet: View, slideOffset: Float): Boolean
}