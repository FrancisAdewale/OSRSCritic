package com.example.osrscritic.viewmodel

import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.di.OSRSCriticApp
import com.example.osrscritic.googleClientId
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleLoginViewModel(private val app: OSRSCriticApp, private val listener: OnSignInStartedListener ): ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentUserLiveData = MutableLiveData<FirebaseUser>()
    val currentUserLiveData : LiveData<FirebaseUser>
        get() = _currentUserLiveData


    val gso =  GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(googleClientId)
        .requestEmail()
        .build()


    val googleSignInClient = GoogleSignIn.getClient(app, gso)

    fun signIn() {
        listener.onSignInStarted(googleSignInClient)
    }


    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {

            if (it.isSuccessful) {
                _currentUserLiveData.value = auth.currentUser
            } else {
                _currentUserLiveData.value = null
            }
        }
    }





}