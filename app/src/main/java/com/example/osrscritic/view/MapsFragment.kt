package com.example.osrscritic.view

import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.osrscritic.DisplayUserActivity
import com.example.osrscritic.R
import com.example.osrscritic.databinding.FragmentMapsBinding
import com.example.osrscritic.repo.DisplayUserRepo
import com.example.osrscritic.viewmodel.MapsFragmentViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QueryDocumentSnapshot
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.nio.MappedByteBuffer

class MapsFragment : Fragment() {

    lateinit var binding: FragmentMapsBinding
    private val mapsFragmentViewModel: MapsFragmentViewModel by viewModel()
    val currentUser = FirebaseAuth.getInstance().currentUser

    private val callback = OnMapReadyCallback { googleMap ->

        mapsFragmentViewModel.mapsLocationLiveData.observe(viewLifecycleOwner, {
            it.get().addOnSuccessListener{
                for(document in it.documents) {
                    val data = document.data

                    val email = data?.get("email") as String

                    if(email != currentUser?.email) {

                        val long = data?.get("longitude") as? Double
                        val lat = data?.get("latitude") as? Double
                        val osrsName = data?.get("osrsAccName") as? String
                        val userLoc = LatLng(lat!!, long!!)
                        googleMap.addMarker(MarkerOptions().position(userLoc)
                            .title(osrsName)
                            .snippet(email))
                    }
                }

                googleMap.setOnInfoWindowClickListener { marker ->

                    DisplayUserRepo.user = marker.title.toString()
                    val b = Bundle()
                    b.putString("c",marker.snippet.toString())

                    Log.d("MarkerSnippet", marker.snippet.toString())
                    val b2 = Bundle()
                    b.putString("acc",marker.title.toString())
                    val intent = Intent(activity, DisplayUserActivity::class.java)
                    intent.putExtras(b)
                    intent.putExtras(b2)
                    startActivity(intent)

                }
            }.addOnFailureListener {
                Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMapsBinding.inflate(layoutInflater)
        mapsFragmentViewModel.getFirebaseRef()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}