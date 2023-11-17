package com.migualador.cocktails.presentation.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.migualador.cocktails.presentation.splash.ui_states.SplashUiState

/**
 * SplashViewModel
 *
 */
class SplashViewModel: ViewModel() {

    private val _initializationSuccessLiveData = MutableLiveData<SplashUiState>()
    val initializationSuccessLiveData: LiveData<SplashUiState> = _initializationSuccessLiveData

    fun startInitialization() {
        Handler(Looper.getMainLooper()).postDelayed({
            _initializationSuccessLiveData.value = SplashUiState(true)
        }, 5500)

    }

}