package com.migualador.cocktails.other.logging

import android.content.Context
import android.util.Log
import com.migualador.cocktails.BuildConfig
import javax.inject.Inject

/**
 * Logger
 *
 * Class for logging in debug build type
 */
class Logger @Inject constructor(val context: Context) {
    fun log(text: String) {
        if (BuildConfig.DEBUG) {
            Log.d("CocktailsApp", text)
        }
    }
}