package firbase.go.beruniy

import android.app.Activity
import android.os.Bundle
import firbase.go.beruniy.view_setup.slider.SlidingSquareLoaderView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<SlidingSquareLoaderView>(R.id.view)
        button1.setOnClickListener {
            view.show()
        }
    }
}
