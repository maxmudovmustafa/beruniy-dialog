package firbase.go.beruniy

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import firbase.go.beruniy.utils.UI
import firbase.go.beruniy.view_setup.MaterialDialog
import firbase.go.beruniy.view_setup.MaterialDialog.*
import firbase.go.beruniy.view_setup.choise.ShareAdapter
import firbase.go.beruniy.view_setup.choise.utils.Utils
import firbase.go.beruniy.view_setup.listener.Command
import firbase.go.beruniy.view_setup.listener.CommandFacade
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activity = this


        val items = ArrayList<String>()
        items.add("First")
        items.add("Second")
        items.add("Third")
        items.add("Fourth")
        items.add("Fiveth")
        items.add("Six")

        // Calendar Dialog
        UI.makeDateTimePicker(btnCalendar, true)


        // Alert Dialog
        btnAlert.setOnClickListener {
            UI.alert(this, "Alert Dialog", "This is the Message")
        }


        // Confirm Dialog
        btnConfirm.setOnClickListener {
            UI.confirm(this, "Confirm", "This is the Body Message", object : Command {
                override fun apply() {
                    Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                }
            })
        }


        // Dialog 1
        btnDialog.setOnClickListener {
            UI.dialog()
                .title("Dialog Title")
                .message("Message")
                .negative(object : Command {
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })
                .positive(object : Command {
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })
                .show(this)
        }


        // Pop Up dialog
        btnDialogPopUP.setOnClickListener {
            UI.popup(btnDialogPopUP, items, object : CommandFacade<String> {
                override fun getIcon(value: String): Any? {
                    return resources.getDrawable(android.R.drawable.ic_menu_search)
                }

                override fun getName(value: String): CharSequence {
                    return value
                }

                override fun apply(value: String) {
                    Toast.makeText(activity, value, Toast.LENGTH_SHORT).show()
                }
            })
        }


        // Option Dialog Builder
        btnDialogOption.setOnClickListener {

//            UI.dialog()
//                .title("Dialog Option")
//                .message("Options")
//                .positive(object : Command {
//                    override fun apply() {
//                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
//                    }
//                })
//                .negative(object : Command {
//                    override fun apply() {
//                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
//                    }
//                }).option(MyArray.from(items.toList()), object : CommandFacade<String> {
//                    override fun apply(value: String) {
//                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun getIcon(value: String): Any? {
//                        return resources.getDrawable(android.R.drawable.ic_menu_search)
//                    }
//
//                    override fun getName(value: String): String {
//                        return value
//                    }
//                }).show(this)
            val sa = ShareAdapter(this)


            MaterialDialog.Builder(this)
                .title("Title")
                .message("Message")
                .positiveText("Positive")
                .negativeText("negative")
                .listItems(
                    true,
                    items[0],
                    items[1],
                    items[2],
                    items[3],
                    items[4]
                )
                .adapter(
                    true, sa
                ) { parent, view, position, id ->
                    Toast.makeText(
                        activity,
                        "clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .listItemsMultiChoice(
                    items[0],
                    items[1],
                    items[2],
                    items[3],
                    items[4]
                )
                .show()
        }


        // Custom Dialog
        btnCustom.setOnClickListener {

            val vsRoot = layoutInflater.inflate(R.layout.item_dialog, null)
            val dialog = UI.dialog().createDialog(
                this,
                vsRoot,
                false
            )

            vsRoot.findViewById<Button>(R.id.btnAppy).setOnClickListener {
                dialog.dismiss()
            }


            // Dialog Netural
            btnNeutral.setOnClickListener {

                UI.dialog()
                    .title("Dialog Neutral")
                    .message("This is demo")
                    .neutral("Click Ok", object : Command {
                        override fun apply() {
                            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                        }
                    }).show(this)

            }


            btnBottom.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.bottom_view, null)
                val dialog = UI.bottomSheetDialog(
                    activity,
                    view
                )
                dialog.create()
                dialog.show()

            }




            btnDialogView.setOnClickListener {
                val view = layoutInflater.inflate(R.layout.item_dialog, null)
                val bottom = UI.createBottomDialog(
                    view,
                    activity
                )
                bottom.show()

            }

        }
    }

    private val TAG = "MainActivity"
    private val REQUEST_EXTERNAL_STORAGE = 1

    private val PERMISSIONS_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val ITEMS = arrayOf(
        "Car", "Plane", "Bike", "Skateboard", "Rocket",
        "Paper plane", "Boat", "Train",
        "Hovercraft", "Space shuttle", "Jet", "Truck", "Elephant"
    )

    private val mSharedPreferences: SharedPreferences? = null


    private fun showMaterialDialog() {
        Builder(this)
            .title("MaterialDialog")
            .message("A simple dialog.")
            .positiveText("OK")
            .neutralText("NOT NOW")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .showListener(object : ShowListener() {
                fun onShow(d: AlertDialog?) {
//                    super.onShow(d)
                }
            })
            .dismissListener(object : DismissListener() {
            })
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("Ok")
                }

                override fun onNeutral(dialog: MaterialDialog) {
                    super.onNeutral(dialog)
                    showToast("Not now")
                }

                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Cancel")
                }
            })
            .show()
    }


    private fun showMaterialDialogIgnoredButtonCallback() {
        Builder(this)
            .title("MaterialDialog")
            .message("Ignored Buttons.")
            .positiveText("OK")
            .neutralText("NOT NOW")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .showListener(object : ShowListener() {
                override fun onShow(dialog: android.app.AlertDialog) {
                    super.onShow(dialog)
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                        .setOnClickListener { showToast("Not closed...") }
                }
            })
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("Ok")
                }

                override fun onNeutral(dialog: MaterialDialog) {
                    super.onNeutral(dialog)
                    showToast("Not now")
                }

                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Cancel")
                }
            })
            .show()
    }


    private fun showMaterialDialogDelayedButton() {
        Builder(this)
            .title("Important")
            .message("Please read our terms of use.")
            .positiveText("DONE")
            .positiveColor(R.color.green_700)
            .positiveDelayed(3000, 1000, "OK")
            .cancelable(false)
            .canceledOnTouchOutside(false)
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("Ok")
                }
            })
            .dismissListener(object : DismissListener() {
            })
            .show()
    }


    private fun showMaterialDialogCustomView() {
        Builder(this)
            .title("MaterialDialog")
            .message("Icon above and a custom view below...")
            .positiveText("AGREE")
            .negativeText("DISAGREE")
            .customView(R.layout.item_dialog)
            .positiveColor(R.color.green_700)
            .neutralColor(R.color.yellow_700)
            .negativeColor(R.color.pink_700)
            .icon(R.drawable.ic_launcher_foreground)
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("Agreed")
                }

                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Disagreed")
                }
            })
            .show()
    }


    private fun showMaterialDialogListNoTitle() {
        Builder(this)
            .title(null)
            .listItems(true, "HTC", "Samsung", "LG", "Huawei")
            .itemClickListener(object : ItemClickListener() {
                override fun onClick(v: View, position: Int, id: Long) {
                    super.onClick(v, position, id)
                    showToast(
                        "onClick (" + ((v as LinearLayout).getChildAt(0) as TextView).text
                                + ")"
                    )
                    Log.d(
                        TAG,
                        "onClick (" + (v.getChildAt(0) as TextView).text
                                + ")"
                    )
                }
            })
            .show()
    }


    private fun showMaterialDialogList() {
        Builder(this)
            .title("MaterialDialog")
            .negativeText("CANCEL")
            .negativeColor(R.color.pink_500)
            .listItems(false, *ITEMS)
            .itemSelectedListener(object : ItemSelectedListener() {
            })
            .itemClickListener(object : ItemClickListener() {
                override fun onClick(v: View?, position: Int, id: Long) {
                    super.onClick(v, position, id)
                    showToast("onClick (" + ITEMS[position] + ")")
                }
            })
            .buttonCallback(object : ButtonCallback() {
                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Cancel")
                }
            })
            .show()
    }


    private fun showMaterialDialogListSingleChoice() {
        Builder(this)
            .title("List dialog")
            .message(null)
            .positiveText("OK")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .negativeColor(R.color.pink_700)
            .listItemsSingleSelection(false, *ITEMS)
            .selection(2)
            .itemClickListener(object : ItemClickListener() {
                override fun onClick(v: View?, position: Int, id: Long) {
                    super.onClick(v, position, id)
                    showToast("onClick (" + ITEMS[position] + ")")
                }
            })
            .itemLongClickListener(object : ItemLongClickListener() {
                override fun onLongClick(view: View?, position: Int, id: Long) {
                    super.onLongClick(view, position, id)
                    showToast("onLongClick (" + ITEMS[position] + ")")
                }
            })
            .showListener(object : ShowListener() {
                fun onShow(dialog: AlertDialog?) {
//                    super.onShow(dialog)
                }
            })
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("OK")
                }

                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Cancel")
                }
            })
            .show()
    }


    private fun showMaterialDialogListMultiChoice() {
        Builder(this)
            .title("MaterialDialog")
            .message(null)
            .positiveText("OK")
            .negativeText("CANCEL")
            .positiveColor(R.color.green_700)
            .negativeColor(R.color.pink_700)
            .listItemsMultiChoice(*ITEMS)
            .selection(0, 2)
            .itemClickListener(object : ItemClickListener() {
                override fun onClick(v: View?, position: Int, id: Long) {
                    super.onClick(v, position, id)
                    showToast("onClick (" + ITEMS[position] + ")")
                }
            })
            .buttonCallback(object : ButtonCallback() {
                override fun onPositive(dialog: MaterialDialog) {
                    super.onPositive(dialog)
                    showToast("Ok")
                }

                override fun onNegative(dialog: MaterialDialog) {
                    super.onNegative(dialog)
                    showToast("Canceled")
                }
            }).build().show()
    }


    private fun showMaterialDialogBaseAdapter() {
        val sa = ShareAdapter(this)
        Builder(this)
            .title("Base Adapter")
            .negativeText("Cancel")
            .icon(R.drawable.ic_launcher_foreground)
            .adapter(true, sa,
                OnItemClickListener { parent, view, position, id ->
                    showToast(
                        "onClick (" + ((view as LinearLayout).getChildAt(1) as TextView).text
                                + ")"
                    )
                })
            .show()
    }


    private fun showMaterialDialogShareApp() {
        Builder(this)
            .title("Share app")
            .negativeText("Cancel")
            .icon(R.drawable.ic_launcher_foreground)
            .shareAppDialog(true, "Hey, check out this app:\n")
            .show()
    }


    private fun showDialogScaled() {
        Builder(this)
            .title("Scaled dialog")
            .message(
                """
                    Lorem ipsum dolor sit amet, eu vitae petentium eam. Prima nominavi eloquentiam pri ne, vix eu case dicant persequeris. Tale natum molestie vel no, civibus appareat nominati no eum. Mediocrem sententiae usu in, vix dicunt conclusionemque ne, ut mel error insolens. Pro at fugit legendos, per ne fastidii maiestatis. Eum tale idque ex. Veri autem aliquip nam et, numquam albucius recusabo per cu.
                    
                    Vis id aperiam habemus imperdiet, eu tollit sensibus accommodare vix. Ea decore epicurei eam. Te vitae saperet appareat eam, modus ullum expetendis mei cu. Omnes viderer vis eu, eam ei ornatus intellegebat. Vim posse liberavisse an, oratio accumsan euripidis ex duo, eu fabellas platonem mandamus eum.
                    
                    Ea usu tota tempor comprehensam, ea essent nominavi eam. An apeirian comprehensam vix, oblique dolorum sed eu, at vis nullam mandamus corrumpit. Ei est nominati urbanitas, appetere complectitur quo eu, doming erroribus mei id. In nihil tincidunt duo, has illud graeco partiendo te. Mea eu hinc contentiones, sed no natum salutatus. Per vero essent voluptatibus ut, eu duo laudem singulis, ex erat zril dolorum mea. Te pri justo tation graecis.
                    
                    Tollit ornatus persequeris ei mea, vix et rebum invenire. No ancillae conclusionemque vis. Nec dico splendide dissentiunt in, aeque iusto ne has. Illud integre vix ne, mei cu agam maiestatis, ut nec meliore percipitur.
                    
                    Clita democritum persequeris et duo, ut has nibh iudicabit, id eam volutpat instructior. Vel ea appareat deseruisse, eu est graeco feugiat, nam eu iusto oratio animal. Ad eum saepe dictas efficiendi, postea pertinax mei eu, sit ad quod quodsi meliore. Cu cum lobortis platonem erroribus, eu suas sanctus constituto vis, regione accommodare ei usu. Ut consul melius senserit cum, sit regione minimum scribentur cu, in hinc delenit habemus eum. Pro in semper torquatos referrentur. Ut mel mazim fierent, vix no nibh ornatus salutatus.
                    """.trimIndent()
            )
            .scale(60, 100)
            .positiveText(R.string.ok)
            .show()
    }


    private fun showCustomTypefaceDialog() {
        val tf: Typeface = Utils.resolveTypeface(this, "hacked.ttf")
        Builder(this)
            .title("Custom font")
            .message("This font looks nice.")
            .positiveText("OK")
            .negativeText("CANCEL")
            .font(tf)
            .show()
    }


    private fun showDialogDimmed() {
        val seekBar = findViewById<View>(R.id.seekBar) as SeekBar
        Builder(this)
            .title("MaterialDialog")
            .message("Dimmed MaterialDialog.")
            .positiveText("OK")
            .positiveColor(R.color.purple_700)
            .dim(seekBar.progress)
            .show()
    }


    fun onDialog(view: View?) {
        showMaterialDialog()
    }

    fun onDialogIgnoredButtonCallback(view: View?) {
        showMaterialDialogIgnoredButtonCallback()
    }


    fun onDialogDelayedButton(view: View?) {
        showMaterialDialogDelayedButton()
    }


    fun onDialogCustomView(view: View?) {
        showMaterialDialogCustomView()
    }

    fun onDialogListNoTitle(view: View?) {
        showMaterialDialogListNoTitle()
    }


    fun onDialogList(view: View?) {
        showMaterialDialogList()
    }


    fun onDialogListSingleChoice(view: View?) {
        showMaterialDialogListSingleChoice()
    }


    fun onDialogListMultiChoice(view: View?) {
        showMaterialDialogListMultiChoice()
    }


    fun onDialogBaseAdapter(view: View?) {
        showMaterialDialogBaseAdapter()
    }


    fun onDialogShare(view: View?) {
        showMaterialDialogShareApp()
    }


    fun onDialogScaled(view: View?) {
        showDialogScaled()
    }


    fun onDialogCustomTypeface(view: View?) {
        showCustomTypefaceDialog()
    }

    fun onDialogDimmed(view: View?) {
        showDialogDimmed()
    }


    private fun showToast(text: String) {
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
    }


    private fun checkPermission(): Boolean {
        val permission = ActivityCompat
            .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            false
        } else {
            true
        }
    }

}
