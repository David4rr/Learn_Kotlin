package com.davidarrozaqi.storyapp.custom

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.davidarrozaqi.storyapp.R

class EmailTextField @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
AppCompatEditText(context, attrs){

    init {
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS or InputType.TYPE_CLASS_TEXT
        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty() && !Patterns.EMAIL_ADDRESS.matcher(p0).matches()) error =
                    context.getString(R.string.error_email)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
}