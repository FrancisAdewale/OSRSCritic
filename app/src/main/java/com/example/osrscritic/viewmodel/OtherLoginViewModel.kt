package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.User
import com.example.osrscritic.repo.AuthRepo
import com.example.osrscritic.repo.FirebaseDBRepo
import com.google.firebase.firestore.CollectionReference

class OtherLoginViewModel(
    private val authRepo: AuthRepo,
    private val firebaseDBRepo: FirebaseDBRepo
    ) : ViewModel() {

    private var _authOtherUserLiveData = MutableLiveData<ResponseState<User>>()
    val authOtherUserLiveData : LiveData<ResponseState<User>>
        get() = _authOtherUserLiveData

    private var _signInOtherUserLiveData = MutableLiveData<ResponseState<User>>()
    val signInOtherUserLiveData : LiveData<ResponseState<User>>
        get() = _signInOtherUserLiveData

    private var _firebaseDbLiveData: MutableLiveData<CollectionReference> = MutableLiveData()
    val firebaseDbLiveData : LiveData<CollectionReference>
        get() = _firebaseDbLiveData



    fun createOtherUser(email: String, password: String) {
        _authOtherUserLiveData = authRepo.firebaseCreateUserWithPassword(email, password)
    }

    fun signInOtherUser(email: String, password: String) {
        _signInOtherUserLiveData = authRepo.firebaseSignInUserWithPassword(email, password)
    }

    fun getFirebaseDbRef() {
        _firebaseDbLiveData.postValue(firebaseDBRepo.ref)

    }

}