package com.example.osrscritic.viewmodel

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.di.OSRSCriticApp
import com.example.osrscritic.googleClientId
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.User
import com.example.osrscritic.repo.AuthRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLoginViewModel(private val authRepo: AuthRepo ): ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private var _authenticateUserLiveData: MutableLiveData<ResponseState<User>> = MutableLiveData()
    val authenticateUserLiveData: LiveData<ResponseState<User>> get() = _authenticateUserLiveData

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        _authenticateUserLiveData = authRepo.firebaseSignInWithGoogle(googleAuthCredential)
    }


}