package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.repo.FirebaseRepo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.StorageReference

class ProfileDetailsViewModel(
    private val firebaseRepo: FirebaseRepo
    ): ViewModel() {


    private var _profileDetailsLiveData = MutableLiveData<CollectionReference>()
    val profileDetailsLiveData: LiveData<CollectionReference>
        get() = _profileDetailsLiveData

    private var _profileImageLiveData = MutableLiveData<StorageReference>()
    val profileImageLiveData: LiveData<StorageReference>
        get() = _profileImageLiveData

    fun getFirebaseRef(){
        _profileDetailsLiveData.postValue(firebaseRepo.ref)
    }

    fun getFirebaseStorageRef() {
        _profileImageLiveData.postValue(firebaseRepo.storageRef)
    }







}