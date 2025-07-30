package com.example.testapp.activities.utils

import android.app.Activity
import android.content.Context
import android.widget.TextView
import com.example.testapp.R
import android.app.Dialog
import android.view.ViewGroup
import android.widget.Button

object DialogUtils {

    fun showCustomDialog(context: Context, title: String, message: String, btnClickListener: () -> Unit) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val titleTv = dialog.findViewById<TextView>(R.id.dialogTitle)
        val messageTv = dialog.findViewById<TextView>(R.id.dialogMessage)
        val buttonBtn = dialog.findViewById<Button>(R.id.dialogButton)

        titleTv.text = title
        messageTv.text = message

        buttonBtn.setOnClickListener {
            btnClickListener()
            dialog.dismiss()
        }

        dialog.show()

        dialog.setCanceledOnTouchOutside(true)

        dialog.setOnCancelListener {
            if (context is Activity) {
                context.finish()
            }
        }
    }

    fun showCustomDialogProfileOptions(context: Context) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog_profile_image_options)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val titleTv = dialog.findViewById<TextView>(R.id.dialogTitle)
        val messageTv = dialog.findViewById<TextView>(R.id.dialogMessage)
        val buttonBtn = dialog.findViewById<Button>(R.id.dialogButton)

        titleTv.text = ""
        messageTv.text = ""

        buttonBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        dialog.setCanceledOnTouchOutside(true)

        dialog.setOnCancelListener {
            if (context is Activity) {
            }
        }
    }
}