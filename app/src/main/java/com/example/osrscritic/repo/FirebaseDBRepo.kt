package com.example.osrscritic.repo

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.CollectionReference

class FirebaseDBRepo {

    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    val ref: CollectionReference = db.collection("users")

}