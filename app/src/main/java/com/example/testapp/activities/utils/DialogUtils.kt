package com.example.testapp.activities.utils

import android.content.Context
import android.widget.TextView
import com.example.testapp.R
import android.app.Dialog
import android.content.Intent
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.DialogTitle
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.activities.ui.login.LoginActivity

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
    }
}