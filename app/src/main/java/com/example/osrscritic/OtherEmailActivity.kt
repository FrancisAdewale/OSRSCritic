package com.example.osrscritic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.osrscritic.databinding.ActivityOtherEmailBinding
import com.example.osrscritic.di.googleLoginViewModelModule
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.viewmodel.OtherLoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherEmailActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtherEmailBinding
    private val otherLoginViewModel : OtherLoginViewModel by viewModel()
    lateinit var email: String
    lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.emaillEditText.alpha = 0.8f
        binding.passwordEditText.alpha = 0.8f

        binding.emaillEditText.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                email = binding.emaillEditText.text.toString()
                Log.d("email textwatcher", email)

            }

        })

        binding.passwordEditText.addTextChangedListener(object: TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                password = binding.passwordEditText.text.toString()
                Log.d("password textwatcher", password)

            }

        })

        binding.otherLoginBtn.setOnClickListener {
            createUserWithEmailAndPass()
        }

    }



    private fun createUserWithEmailAndPass() {

        otherLoginViewModel.createOtherUser(email,password)
        otherLoginViewModel.authOtherUserLiveData.observe(this,{
            createdUser ->
            when(createdUser){
                is ResponseState.Error -> {
                    createdUser.message ?. let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                    }
                }
                is ResponseState.Success -> {
                    if(createdUser.data != null){

                    }
                }

                is ResponseState.Loading -> {
                    //show progress
                }

            }
        })

    }
}