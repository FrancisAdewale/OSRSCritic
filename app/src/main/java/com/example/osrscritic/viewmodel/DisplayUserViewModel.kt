package com.example.osrscritic.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.osrscritic.model.RunescapeResponse
import com.example.osrscritic.repo.DisplayUserRepo
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DisplayUserViewModel(private val displayUserRepo: DisplayUserRepo) : ViewModel() {

    private var _displayUserLiveData = MutableLiveData<RunescapeResponse>()
    val displayerUserLiveData : LiveData<RunescapeResponse>
        get() = _displayUserLiveData

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState : LiveData<Boolean>
        get() = _loadingState

    private val _errorData = MutableLiveData<String>()
    val errorData : LiveData<String>
        get() = _errorData

    private var job: Job? = null

    fun getOSRSPlayer() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            withContext(Dispatchers.Main){
                displayUserRepo.getOSRSUser().enqueue(object: Callback<RunescapeResponse> {
                    override fun onResponse(call: Call<RunescapeResponse>,
                                            response: Response<RunescapeResponse>) {
                        if(response.isSuccessful) {
                            _displayUserLiveData.postValue(response.body())
                            _loadingState.postValue(false)


                        }
                    }

                    override fun onFailure(call: Call<RunescapeResponse>, t: Throwable) {
                        onError("Error: ${t.message}")
                    }

                })
            }
        }
    }



    private val exceptionHandler = CoroutineExceptionHandler {
            coroutineContext, throwable ->
        onError("Exception handled ${throwable.localizedMessage}")
    }

    private fun onError(s: String) {
        _errorData.postValue(s)
        _loadingState.postValue(false)

    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()

    }

}