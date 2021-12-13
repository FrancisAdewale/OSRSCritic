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

        configureObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {
            Log.d("resultCode", resultCode.toString())
            imageUri = data?.data!!
            profileDetailsViewModel.profileImageLiveData.observe(this, {
                val imageRef = it.child(currentUser?.email + "_avatar")
                    .child("profile/profile.jpg")

                binding.ivAvatar.setImageURI(imageUri)



                imageRef.putFile(imageUri).addOnSuccessListener {

                }.addOnFailureListener{
                    Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()

                }

            })
        }
    }


    private fun configureObservers() {
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

//    fun getFileFromUri(uri: Uri): File? {
//        if (uri.path == null) {
//            return null
//        }
//        var realPath = String()
//        val databaseUri: Uri
//        val selection: String?
//        val selectionArgs: Array<String>?
//        if (uri.path!!.contains("/document/image:")) {
//            databaseUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            selection = "_id=?"
//            selectionArgs = arrayOf(DocumentsContract.getDocumentId(uri).split(":")[1])
//        } else {
//            databaseUri = uri
//            selection = null
//            selectionArgs = null
//        }
//        try {
//            val column = "_data"
//            val projection = arrayOf(column)
//            val cursor = this.contentResolver.query(
//                databaseUri,
//                projection,
//                selection,
//                selectionArgs,
//                null
//            )
//            cursor?.let {
//                if (it.moveToFirst()) {
//                    val columnIndex = cursor.getColumnIndexOrThrow(column)
//                    realPath = cursor.getString(columnIndex)
//                }
//                cursor.close()
//            }
//        } catch (e: Exception) {
//            Log.i("GetFileUri Exception:", e.message ?: "")
//        }
//        val path = if (realPath.isNotEmpty()) realPath else {
//            when {
//                uri.path!!.contains("/document/raw:") -> uri.path!!.replace(
//                    "/document/raw:",
//                    ""
//                )
//                uri.path!!.contains("/document/primary:") -> uri.path!!.replace(
//                    "/document/primary:",
//                    "/storage/emulated/0/"
//                )
//                else -> return null
//            }
//        }
//        return File(path)
//    }
}