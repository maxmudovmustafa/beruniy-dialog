package firbase.go.beruniy

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import firbase.go.beruniy.utils.UI
import firbase.go.beruniy.view_setup.listener.Command
import firbase.go.beruniy.view_setup.listener.CommandFacade
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activity = this


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
            UI.popup()
                .option("Single Item", object : Command {
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                }).show(View.inflate(this, R.layout.item_popup, null))
        }


        val items = ArrayList<String>()
        items.add("First")
        items.add("Second")
        items.add("Third")
        items.add("Fourth")
        items.add("Fiveth")
        items.add("Six")

        // Option Dialog Builder
        btnDialogOption.setOnClickListener {



            UI.dialog()
                .title("Dialog Title")
                .message("Message")
                .option(items.toList(), object : CommandFacade<String> {
                    override fun apply(value: String) {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }

                    override fun getIcon(value: String): Any? {
                        return resources.getDrawable(android.R.drawable.ic_menu_search)
                    }

                    override fun getName(value: String) = value
                })
                .negative(object : Command {
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })
                .positive(object : Command {
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                }).show(this)
        }


        // Custom Dialog
        btnCustom.setOnClickListener {

            val dialog = UI.dialog().createDialog(
                this,
                R.layout.item_dialog,
                false
            )

            dialog.getButton(R.id.btnAppy).setOnClickListener {
                dialog.dismiss()
            }







            // Dialog Netural
            btnNeutral.setOnClickListener {

                UI.dialog().neutral("Neutral Btn", object : Command{
                    override fun apply() {
                        Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
                    }
                })

            }







            btnDialogView.setOnClickListener {

//                UI.bottomSheet(
//                    activity,
//                    "Bottom View Dialog",
//                    items,
//                    object: CommandFacade<String>{
//                        override fun apply(value: String) {
//                            Toast.makeText(activity, "Clicked", Toast.LENGTH_SHORT).show()
//                        }
//
//                        override fun getIcon(value: String): Any? {
//                            return resources.getDrawable(android.R.drawable.ic_menu_search)
//                        }
//
//                        override fun getName(value: String) = value
//                    })

            }







            // Calendar Dialog
            btnCalendar.setOnClickListener {
                UI.makeDateTimePicker(btnCalendar, true)

            }



        }
    }
}
