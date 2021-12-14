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

        accountFragmentViewModel.accountInfoLiveData.observe(viewLifecycleOwner, {
            it.document(currentUser?.email!!).get()
                .addOnCompleteListener(object: OnCompleteListener<DocumentSnapshot>{
                    override fun onComplete(p0: Task<DocumentSnapshot>) {
                        if (p0.isSuccessful) {
                            val document = p0.result

                            var fullName = document["firstName"].toString() + " "
                            fullName += document["secondName"].toString()

                            binding.tvFullName.text = fullName
                            binding.accountTitle.text = document["osrsAccName"].toString()

                            val lng = document["lng"] as Double
                            val lat = document["lat"] as Double

                            val geocoder = Geocoder(activity, Locale.getDefault())
                            val addresses: List<Address> =
                                geocoder.getFromLocation(lat, lng, 1)
                            val cityName: String = addresses[0].getAddressLine(0)
                            val stateName: String = addresses[0].getAddressLine(1)
                            val countryName: String = addresses[0].getAddressLine(2)

                            val loc = "$cityName, $countryName"

                            binding.tvLocation.text = loc

                        }

                    }

                })
        })

        return binding.root
    }
}