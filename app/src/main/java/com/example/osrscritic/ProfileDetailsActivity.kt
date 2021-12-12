package com.example.osrscritic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import com.example.osrscritic.databinding.ActivityProfileDetailsBinding
import com.example.osrscritic.viewmodel.ProfileDetailsViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileDetailsBinding
    val profileDetailsViewModel : ProfileDetailsViewModel by viewModel()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileDetailsViewModel.getFirebaseRef()
        Log.d("currentUser", currentUser?.email.toString())


        profileDetailsViewModel.profileDetailsLiveData.observe(this, {

            it.document(currentUser?.email!!).get()
                .addOnCompleteListener(object: OnCompleteListener<DocumentSnapshot>{
                override fun onComplete(p0: Task<DocumentSnapshot>) {

                    if (p0.isSuccessful) {

                        val document = p0.result

                        if(document.exists()) {

                            binding.tvFirstName.setText(document["firstName"].toString())
                            binding.tvSecondName.setText(document["secondName"].toString())

                        } else  {
                            Log.d("DocumentNull", "document doesn't exist")
                        }

                    } else {
                        Log.d("Exception error", "failed with " + p0.exception)
                    }
                }
            })
        })
    }
}