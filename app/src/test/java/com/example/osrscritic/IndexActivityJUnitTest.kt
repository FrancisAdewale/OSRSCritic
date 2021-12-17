package com.example.osrscritic

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class IndexActivityJUnitTest {

    lateinit var indexActivity: IndexActivity


    @Before
    fun setup(){
        indexActivity = IndexActivity()
    }

    @Test
    fun canReceiveUserCoordinates(){

        //given
        indexActivity.latTest = null
        indexActivity.lngTest = null

        //when
        indexActivity.getLastLocation()
        indexActivity.mLocationProviderClient = LocationServices.getFusedLocationProviderClient(indexActivity)


        //then

        assertNotNull(indexActivity.latTest)
        assertNotNull(indexActivity.lngTest)

    }

}
