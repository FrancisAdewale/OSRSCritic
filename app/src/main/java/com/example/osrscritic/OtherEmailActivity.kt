package com.example.osrscritic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.osrscritic.databinding.ActivityOtherEmailBinding
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.model.User
import com.example.osrscritic.viewmodel.OtherLoginViewModel
import com.google.android.gms.location.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OtherEmailActivity : AppCompatActivity() {

    lateinit var binding : ActivityOtherEmailBinding
    private val otherLoginViewModel : OtherLoginViewModel by viewModel()
    lateinit var email: String
    lateinit var password: String
    lateinit var otherUser: User
    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var mLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

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

        otherLoginViewModel.getFirebaseDbRef()

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
                        otherUser = createdUser.data!!
                        getFireBaseRef()
                        signInOtherUser()
                    }
                }

                is ResponseState.Loading -> {
                    //show progress
                }
            }
        })

    }

    private fun signInOtherUser() {
        otherLoginViewModel.signInOtherUser(email, password)

        otherLoginViewModel.signInOtherUserLiveData.observe(this, {
            signInUser ->

            when(signInUser) {
                is ResponseState.Error -> {
                    signInUser.message ?. let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                    }
                }

                is ResponseState.Success -> {
                    if(signInUser != null){
                        val profileDetailsIntent = Intent(this, ProfileDetailsActivity::class.java)
                        startActivity(profileDetailsIntent)

                    }
                }
            }

        })
    }

    private fun getLastLocation() {

        Log.d("getLastLoc", "Called")

        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object

                mLocationProviderClient.lastLocation
                    .addOnCompleteListener { task ->
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            lat = location.latitude
                            lng = location.longitude

                            Log.d("getlastlocCoords","${lat} + ${lng}")
                        }
                    }
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG)
                    .show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions()
        }
    }
    private fun requestPermissions() {

        Log.d("reqPermission", "Called")

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_ASK_PERMISSIONS
        )
    }

    private fun checkPermissions(): Boolean {

        Log.d("checkPermissions", "Called")

        return  ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        Log.d("isLocEnabled", "Called")

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestNewLocationData() {

        Log.d("requestNewLoc", "Called")


        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient

        mLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            val lat = mLastLocation.latitude.toString()
            val lng = mLastLocation.longitude.toString()
            Log.d("mlocation", lat + " " + lng)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("onRequestPermissionRes", "Called")
                getLastLocation()
            }
        }
    }

    private fun getFireBaseRef() {
        otherLoginViewModel.firebaseDbLiveData.observe(this, {

            Log.d("firebaseRefFun","Called from otherVM")
            Log.d("userObj", otherUser.toString())
            val u : MutableMap<String, Any> = mutableMapOf()
            u["email"] = otherUser?.email!!
            otherUser?.lat = lat
            otherUser?.lng = lng
            u["firstName"] = otherUser.firstName
            u["secondName"] = otherUser.firstName
            u["latitude"] = otherUser?.lat!!
            u["longitude"] = otherUser?.lng!!
            u["completedRegistration"] = otherUser?.completedRegistration!!

            it.document(otherUser?.email!!).set(u)

        })
    }
}