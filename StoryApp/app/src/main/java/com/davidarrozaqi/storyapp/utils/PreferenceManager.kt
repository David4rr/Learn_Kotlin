package com.davidarrozaqi.storyapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.davidarrozaqi.storyapp.data.dto.auth.LoginResult
import com.davidarrozaqi.storyapp.utils.ConstVal.KEY_NAME
import com.davidarrozaqi.storyapp.utils.ConstVal.KEY_TOKEN
import com.davidarrozaqi.storyapp.utils.ConstVal.PREFS_NAME

class PreferenceManager(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    private fun setStringPreference(prefKey: String, value: String) {
        editor.putString(prefKey, value)
        editor.apply()
    }

    fun setLoginPrefs(loginResult: LoginResult) {
        loginResult.let {
            setStringPreference(KEY_TOKEN, it.token)
            setStringPreference(KEY_NAME, it.name)
        }
    }

    fun clearAllPreferences() {
        editor.remove(KEY_NAME)
        editor.remove(KEY_TOKEN)
        editor.apply()
    }

    val getToken = prefs.getString(KEY_TOKEN, "")?: ""
    val getName = prefs.getString(KEY_NAME, "")?: ""
}