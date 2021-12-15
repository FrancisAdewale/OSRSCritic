package com.example.osrscritic.view

import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.osrscritic.databinding.FragmentAccountBinding
import com.example.osrscritic.viewmodel.AccountFragmentViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.osrscritic.DashboardActivity
import java.util.*


class AccountFragment: Fragment() {

    lateinit var binding: FragmentAccountBinding
    private val accountFragmentViewModel: AccountFragmentViewModel by viewModel()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)

        accountFragmentViewModel.getFirebaseCollRef()
        accountFragmentViewModel.getFirebaseStorageRef()

        accountFragmentViewModel.accountImageLiveData.observe(viewLifecycleOwner, {

            it.child(currentUser?.email!!).child("profile/profile.jpg")
                .downloadUrl.addOnSuccessListener {

                    Glide.with(binding.ivAccountAvatar)
                        .load(it)
                        .into(binding.ivAccountAvatar);


            }.addOnFailureListener {
                Toast.makeText(activity,it.message,Toast.LENGTH_LONG).show()
            }

        })

        accountFragmentViewModel.accountInfoLiveData.observe(viewLifecycleOwner, {
            it.document(currentUser?.email!!).get()
                .addOnCompleteListener(object: OnCompleteListener<DocumentSnapshot>{
                    override fun onComplete(p0: Task<DocumentSnapshot>) {
                        if (p0.isSuccessful) {
                            val document = p0.result

                            var fullName = document["firstName"].toString() + " "
                            fullName += document["secondName"].toString()

                            binding.tvFullName.text = fullName
                            binding.tvAccountOsrsName.text = document["osrsAccName"].toString()

                            val lng  = document["longitude"]
                            val lat = document["latitude"]

                            val geocoder = Geocoder(activity, Locale.getDefault())
                            val addresses: List<Address> =
                                geocoder.getFromLocation(lat as Double, lng as Double, 1)

                            Log.d("addresses", addresses.toString())
                            val cityName: String = addresses[0].locality
                            val countryName: String = addresses[0].countryName

                            val loc = "$cityName, $countryName"

                            binding.tvLocation.text = loc

                        }

                    }

                })
        })

        return binding.root
    }
}