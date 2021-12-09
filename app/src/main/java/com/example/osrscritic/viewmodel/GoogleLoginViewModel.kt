package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.GoogleUser
import com.example.osrscritic.repo.AuthRepo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

class GoogleLoginViewModel(private val authRepo: AuthRepo ): ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _authenticateGoogleUserLiveData: MutableLiveData<ResponseState<GoogleUser>> = MutableLiveData()
    val authenticateGoogleUserLiveData: LiveData<ResponseState<GoogleUser>>
        get() = _authenticateGoogleUserLiveData

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        _authenticateGoogleUserLiveData = authRepo.firebaseSignInWithGoogle(googleAuthCredential)
    }


}