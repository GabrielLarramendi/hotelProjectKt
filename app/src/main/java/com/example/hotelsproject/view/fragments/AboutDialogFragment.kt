package com.example.hotelsproject.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AboutDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val listener = DialogInterface.OnClickListener {_, i ->
            if(i == DialogInterface.BUTTON_NEGATIVE) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/gabriel-larramendi/"))
                startActivity(intent)
            }
        }
        return AlertDialog.Builder(requireContext())
            .setTitle("Projeto de estudos")
            .setMessage("Desenvolvido por Gabriel Larramendi")
            .setPositiveButton(android.R.string.ok, null)
            .setNegativeButton("Site", listener)
            .create()
    }
}