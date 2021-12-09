package com.example.osrscritic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.User
import com.example.osrscritic.repo.AuthRepo

class OtherLoginViewModel(private val authRepo: AuthRepo) : ViewModel() {

    private var _authOtherUserLiveData = MutableLiveData<ResponseState<User>>()
    val authOtherUserLiveData : LiveData<ResponseState<User>>
        get() = _authOtherUserLiveData



    fun createOtherUser(email: String, password: String) {
        _authOtherUserLiveData = authRepo.firebaseCreateUserWithPassword(email, password)
    }

}