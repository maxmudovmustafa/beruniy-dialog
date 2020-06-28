package firbase.go.beruniy.utils

import android.util.Log
import firbase.go.beruniy.BuildConfig

class L_Log {
    private val TAG = "mgow"
    var DEBUG = BuildConfig.DEBUG

    fun log(message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun log(message: String, args: Any) {
        if (DEBUG) {
            Log.d(TAG, String.format(message, args))
        }
    }

    fun log(ex: Throwable) {
        if (DEBUG) {
            Log.d(TAG, ex.message, ex)
        }
    }
}