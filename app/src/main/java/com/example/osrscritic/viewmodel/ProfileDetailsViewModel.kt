package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.repo.FirebaseDBRepo
import com.google.firebase.firestore.CollectionReference

class ProfileDetailsViewModel(
    private val firebaseDBRepo: FirebaseDBRepo
    ): ViewModel() {

        private var _profileDetailsLiveData = MutableLiveData<CollectionReference>()
        val profileDetailsLiveData: LiveData<CollectionReference>
            get() = _profileDetailsLiveData

        fun getFirebaseRef(){
            _profileDetailsLiveData.postValue(firebaseDBRepo.ref)
        }





}