package com.example.osrscritic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.osrscritic.databinding.ActivityIndexBinding
import com.example.osrscritic.model.GoogleUser
import com.example.osrscritic.model.ResponseState
import com.example.osrscritic.viewmodel.GoogleLoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndexActivity : AppCompatActivity() {

    lateinit var googleSignInClient: GoogleSignInClient
    var lat: Double = 0.0
    var lng: Double = 0.0
    lateinit var mLocationProviderClient: FusedLocationProviderClient
    lateinit var binding: ActivityIndexBinding
    private val googleLoginViewModel : GoogleLoginViewModel by viewModel()
    lateinit var googleUser : GoogleUser

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityIndexBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        initGoogleSignInClient()
        googleLoginViewModel.getFirebaseDbRef()


        binding.googleSignIn.setOnClickListener {
            signInUsingGoogle()
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

    private fun signInUsingGoogle() {
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
                    googleUser = authenticatedUser.data
                    getFireBaseRef()
                }
                //update ui
            }
                is ResponseState.Loading -> {
                //show progress
            }
            }
        })

    }

    private fun getFireBaseRef() {
        googleLoginViewModel.firebaseDbLiveData.observe(this, {

            val u : MutableMap<String, Any> = mutableMapOf()
            u["email"] = googleUser?.email!!
            googleUser?.lat = lat
            googleUser?.lng = lng
            u["latitude"] = googleUser?.lat!!
            u["longitude"] = googleUser?.lng!!
            val fullName = googleUser?.name?.split(" ")
            val firstName = fullName?.get(0)
            val secondName = fullName?.get(1)
            u["firstName"] = firstName!!
            u["secondName"] = secondName!!
            u["completedRegistration"] = googleUser?.completedRegistration!!

            it.document(googleUser?.email!!).set(u)
            val profileDetailsIntent = Intent(this, ProfileDetailsActivity::class.java)
            startActivity(profileDetailsIntent)
        })
    }

    private fun requestPermissions() {

        Log.d("reqPermission", "Called")

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            ), REQUEST_CODE_ASK_PERMISSIONS
        )
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

    private fun checkPermissions(): Boolean {

        Log.d("checkPermissions", "Called")

        return  ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
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

}