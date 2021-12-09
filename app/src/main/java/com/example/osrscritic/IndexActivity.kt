package com.example.osrscritic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.osrscritic.databinding.ActivityIndexBinding
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndexActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient

    lateinit var binding: ActivityIndexBinding
    private val googleLoginViewModel : GoogleLoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initGoogleSignInClient()

        binding.googleSignIn.setOnClickListener {
            signInUsingGoolge()
        }



        binding.otherEmailBtn.setOnClickListener {
            val createUserIntent = Intent(this,OtherEmailActivity::class.java)
            startActivity(createUserIntent)
        }

    }


    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(googleClientId)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInUsingGoolge() {
        val signInGoogleIntent = googleSignInClient.signInIntent
        startActivityForResult(signInGoogleIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent? ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java) !!
                if (account != null) {
                    getGoogleAuthCredential(account)
                }

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun getGoogleAuthCredential(account: GoogleSignInAccount) {
        //binding.progressBar.visible()
        val googleTokeId = account.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokeId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }


    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {

        googleLoginViewModel.signInWithGoogle(googleAuthCredential)
        googleLoginViewModel.authenticateGoogleUserLiveData.observe(this, {
            authenticatedUser ->
            when(authenticatedUser) {
                is ResponseState.Error -> {
                authenticatedUser.message ?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    //context?.toast(it)
                }
            }
                is ResponseState.Success -> {
                if (authenticatedUser.data != null){

                }
                //update ui
            }
                is ResponseState.Loading -> {
                //show progress
            }
            }
        })

    }



}