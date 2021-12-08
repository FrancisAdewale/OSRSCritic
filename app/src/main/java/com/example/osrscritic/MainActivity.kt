package com.example.osrscritic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.osrscritic.databinding.ActivityMainBinding
import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import com.example.osrscritic.viewmodel.OnSignInStartedListener
import com.google.android.gms.common.SignInButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val googleLoginViewModel : GoogleLoginViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.googleSignIn.setOnClickListener {
            googleLoginViewModel.signIn()

        }

      configureObservers()

    }

    private fun configureObservers() {

        //googleLoginViewModel.

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == RC_SIGN_IN)
//    }


}