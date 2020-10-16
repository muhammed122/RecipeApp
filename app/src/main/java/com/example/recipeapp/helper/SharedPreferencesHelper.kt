package com.example.recipeapp.helper

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(private val context: Context) {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("file", Context.MODE_PRIVATE)


    fun saveDataInShared(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun getDataFromShared(key: String): String? {

        return sharedPreferences.getString(key, null)
    }
}