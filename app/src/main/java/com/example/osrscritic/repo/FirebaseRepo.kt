package com.example.osrscritic.repo

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage

class FirebaseRepo {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val ref: CollectionReference = db.collection("users")
    private val storage : FirebaseStorage = FirebaseStorage.getInstance()

    val storageRef = storage.reference




}