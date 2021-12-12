package com.example.osrscritic.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.GoogleUser
import com.example.osrscritic.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class AuthRepo {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    lateinit var otherUser: User

    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<ResponseState<GoogleUser>> {
        val authenticatedGoogleUserMutableLiveData: MutableLiveData<ResponseState<GoogleUser>> =
            MutableLiveData()

        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                var isNewUser = authTask.result?.additionalUserInfo?.isNewUser
                val firebaseUser: FirebaseUser? = firebaseAuth.currentUser
                if (firebaseUser != null) {
                    val uid = firebaseUser.uid
                    val name = firebaseUser.displayName
                    val email = firebaseUser.email
                    val user = GoogleUser(uid = uid, name = name, email = email)
                    user.isNew = isNewUser

                    authenticatedGoogleUserMutableLiveData.value = ResponseState.Success(user)
                }

            } else {
                authenticatedGoogleUserMutableLiveData.value = authTask.exception?.message?.let {
                    ResponseState.Error(it)
                }

            }
        }
        return authenticatedGoogleUserMutableLiveData
    }

    fun firebaseCreateUserWithPassword(email: String, password: String): MutableLiveData<ResponseState<User>> {
        val authenticatedOtherUserMutableLiveData: MutableLiveData<ResponseState<User>> =
            MutableLiveData()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("createemail/pass", "createUserWithEmail:success")
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        val uid = user.uid
                        val email = email
                        val password = password
                        otherUser = User(uid = uid, email = email, password = password)
                        authenticatedOtherUserMutableLiveData.value =
                            ResponseState.Success(otherUser)
                    }

                } else {
                    authenticatedOtherUserMutableLiveData.value = task.exception?.message?.let {
                        ResponseState.Error(it)
                    }
                }
            }

        return authenticatedOtherUserMutableLiveData
    }

    fun firebaseSignInUserWithPassword(email: String, password: String): MutableLiveData<ResponseState<User>> {
        val signInOtherUserMutableLiveData: MutableLiveData<ResponseState<User>> =
            MutableLiveData()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("signedin email/password", "signInWithEmail:success")
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        signInOtherUserMutableLiveData.value =
                            ResponseState.Success(otherUser)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Sign in fail email/pass", "signInWithEmail:failure", task.exception)
//                    Toast.makeText(baseContext, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }

        return signInOtherUserMutableLiveData
    }


}