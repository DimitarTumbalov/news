package com.synergygfs.news

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import java.util.*

object UiUtils {
    @SuppressLint("ApplySharedPref")
    fun saveSharedPrefs(context: Context, name: String, data: String?) {
        val prefs: SharedPreferences? =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        val editor = prefs?.edit()
        editor?.putString("data", data)?.commit()
    }

    fun getSharedPrefs(context: Context, name: String): String? {
        val prefs: SharedPreferences? =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return prefs?.getString("data", null)
    }

    fun convertDateToString(date: Date): String {
        return Constants.formatter.format(date).toString()
    }
}