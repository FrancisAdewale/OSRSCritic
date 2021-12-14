package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.repo.FirebaseRepo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference

class AccountFragmentViewModel(private val firebaseRepo: FirebaseRepo) : ViewModel() {

    private var _accountInfoLiveData = MutableLiveData<CollectionReference>()
    val accountInfoLiveData: LiveData<CollectionReference>
        get() = _accountInfoLiveData

    private var _accountImageLiveData = MutableLiveData<StorageReference>()
    val accountImageLiveData: LiveData<StorageReference>
        get() = _accountImageLiveData

    fun getFirebaseCollRef() {
        _accountInfoLiveData.postValue(firebaseRepo.ref)
    }

    fun getFirebaseStorageRef() {
        _accountImageLiveData.postValue(firebaseRepo.storageRef)
    }




}