package com.example.osrscritic

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import com.example.osrscritic.databinding.ActivityProfileDetailsBinding
import com.example.osrscritic.viewmodel.ProfileDetailsViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.UploadTask
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.net.URI
import kotlin.math.PI

class ProfileDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileDetailsBinding
    private val profileDetailsViewModel : ProfileDetailsViewModel by viewModel()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    lateinit var firstName: String
    lateinit var secondName: String
    private var imageUri : Uri = Uri.EMPTY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileDetailsViewModel.getFirebaseRef()
        profileDetailsViewModel.getFirebaseStorageRef()

        binding.uploadAvatarBtn.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Choose Avatar"), PICK_IMAGE)
        }

        getName()

        binding.doneProfileBtn.setOnClickListener {
            profileDetailsViewModel.profileDetailsLiveData.observe(this, {
                val details : MutableMap<String, Any> = mutableMapOf()

                details["firstName"] = binding.tvFirstName.text.toString()
                details["secondName"] = binding.tvSecondName.text.toString()
                details["osrsAccName"] = binding.tvOsrsAccName.text.toString()
                details["completedRegistration"] = true

                it.document(currentUser?.email!!).set(details, SetOptions.merge())
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {
            Log.d("resultCode", resultCode.toString())
            imageUri = data?.data!!
           uploadImage()
        }
    }

    private fun uploadImage() {
        profileDetailsViewModel.profileImageLiveData.observe(this, {
            val imageRef = it.child(currentUser?.email!!)
                .child("profile/profile.jpg")

            binding.ivAvatar.setImageURI(imageUri)

            imageRef.putFile(imageUri).addOnSuccessListener {

            }.addOnFailureListener{
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun getName() {
        profileDetailsViewModel.profileDetailsLiveData.observe(this, {

            it.document(currentUser?.email!!).get()
                .addOnCompleteListener(object: OnCompleteListener<DocumentSnapshot>{
                    override fun onComplete(p0: Task<DocumentSnapshot>) {

                        if (p0.isSuccessful) {

                            val document = p0.result

                            if(document.exists()) {

                                if(document["firstName"] == "" && document["secondName"] == "") {
                                    binding.tvFirstName.setHint(R.string.first_name)
                                    binding.tvSecondName.setHint(R.string.second_name)

                                    binding.tvFirstName.addTextChangedListener(object: TextWatcher{
                                        override fun beforeTextChanged(
                                            p0: CharSequence?,
                                            p1: Int,
                                            p2: Int,
                                            p3: Int
                                        ) {
                                        }

                                        override fun onTextChanged(
                                            p0: CharSequence?,
                                            p1: Int,
                                            p2: Int,
                                            p3: Int
                                        ) {
                                        }

                                        override fun afterTextChanged(p0: Editable?) {
                                        }

                                    })

                                    binding.tvSecondName.addTextChangedListener(object: TextWatcher{
                                        override fun beforeTextChanged(
                                            p0: CharSequence?,
                                            p1: Int,
                                            p2: Int,
                                            p3: Int
                                        ) {

                                        }

                                        override fun onTextChanged(
                                            p0: CharSequence?,
                                            p1: Int,
                                            p2: Int,
                                            p3: Int
                                        ) {
                                        }

                                        override fun afterTextChanged(p0: Editable?) {
                                        }

                                    })

                                }
                                firstName = document["firstName"].toString()
                                secondName = document["secondName"].toString()

                                binding.tvFirstName.setText(firstName)
                                binding.tvSecondName.setText(secondName)

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