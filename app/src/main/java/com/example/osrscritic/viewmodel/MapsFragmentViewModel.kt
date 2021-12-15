package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.repo.FirebaseRepo
import com.google.firebase.firestore.CollectionReference

class MapsFragmentViewModel(private val firebaseRepo: FirebaseRepo) : ViewModel() {

    private var _mapsLocationsLiveData = MutableLiveData<CollectionReference>()
    val mapsLocationLiveData : LiveData<CollectionReference>
        get() = _mapsLocationsLiveData

    fun getFirebaseRef() {
        _mapsLocationsLiveData.postValue(firebaseRepo.ref)
    }


}