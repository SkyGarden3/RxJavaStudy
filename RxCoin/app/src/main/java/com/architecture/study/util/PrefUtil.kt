package com.architecture.study.util

import android.content.Context
import android.content.SharedPreferences

object PrefUtil {
    private const val DEF_PREF_NAME = "Rx_Pref"

    fun clearAll(context: Context) {
        val sp = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }

    private fun getPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE or 0x4)
        //0x4 = Context.MODE_MULTI_PROCESS
    }

    fun setStrValue(editor: SharedPreferences.Editor, name: String, value: String) {
        editor.remove(name)
        editor.putString(name, value)
        editor.apply()
        //editor.commit()
    }

    fun setStrValue(context: Context, name: String, value: String) {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = pref.edit()
        editor.putString(name, value)
        editor.apply()
        //editor.commit()
    }


    fun setIntValue(context: Context, name: String, value: Int) {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = pref.edit()
        editor.putInt(name, value)
        editor.apply()
        //editor.commit()
    }

    fun setBoolValue(context: Context, name: String, value: Boolean) {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = pref.edit()
        editor.putBoolean(name, value)
        editor.apply()
        //editor.commit()
    }

    fun setFloatValue(context: Context, name: String, value: Float) {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = pref.edit()
        editor.putFloat(name, value)
        editor.apply()
        //editor.commit()
    }

    fun getStrValue(context: Context, name: String, defValue: String): String? {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        return pref.getString(name, defValue)
    }

    fun getIntValue(context: Context, name: String, defValue: Int): Int {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        return pref.getInt(name, defValue)
    }

    fun getBoolValue(context: Context, name: String, defValue: Boolean): Boolean {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        return pref.getBoolean(name, defValue)
    }

    fun getFloatValue(context: Context, name: String, defValue: Float): Float {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        return pref.getFloat(name, defValue)
    }

    fun removeValue(context: Context, name: String) {
        val pref = getPref(
            context,
            DEF_PREF_NAME
        )
        val editor = pref.edit()
        editor.remove(name)
        editor.apply()
        //editor.commit()
    }
}