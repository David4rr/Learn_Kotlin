package com.davidarrozaqi.storyapp.utils.ext

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showChooserDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    onPositiveClick: () -> Unit,
    negativeButtonText: String,
) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButtonText) { _, _ ->
            onPositiveClick()
        }
        setNegativeButton(negativeButtonText) { p0, _ ->
            p0.dismiss()
        }
    }.create().show()
}

fun Fragment.showDialog(
    title: String,
    message: String,
    positiveButtonText: String,
    onPositiveClick: () -> Unit,
) {
    AlertDialog.Builder(requireContext()).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(positiveButtonText) { _, _ ->
            onPositiveClick()
        }
    }.create().show()
}