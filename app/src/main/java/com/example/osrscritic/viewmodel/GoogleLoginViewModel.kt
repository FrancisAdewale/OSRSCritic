package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.GoogleUser
import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.repo.FirebaseRepo
import com.google.firebase.auth.AuthCredential
import com.google.firebase.firestore.CollectionReference

class GoogleLoginViewModel(
    private val authRepo: AuthRepo,
    private val firebaseRepo: FirebaseRepo
): ViewModel() {


    private var _authenticateGoogleUserLiveData:
            MutableLiveData<ResponseState<GoogleUser>> = MutableLiveData()
    val authenticateGoogleUserLiveData: LiveData<ResponseState<GoogleUser>>
        get() = _authenticateGoogleUserLiveData

    private var _firebaseDbLiveData: MutableLiveData<CollectionReference> = MutableLiveData()
    val firebaseDbLiveData : LiveData<CollectionReference>
        get() = _firebaseDbLiveData


    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        _authenticateGoogleUserLiveData = authRepo.firebaseSignInWithGoogle(googleAuthCredential)
    }

    fun getFirebaseDbRef() {
        _firebaseDbLiveData.postValue(firebaseRepo.ref)
    }


}