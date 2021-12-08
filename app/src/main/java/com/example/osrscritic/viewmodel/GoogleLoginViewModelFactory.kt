package com.example.osrscritic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.osrscritic.di.OSRSCriticApp

class GoogleLoginViewModelFactory(
    private val app: OSRSCriticApp,
    private val listener: OnSignInStartedListener
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return GoogleLoginViewModel(app, listener) as T

    }
}