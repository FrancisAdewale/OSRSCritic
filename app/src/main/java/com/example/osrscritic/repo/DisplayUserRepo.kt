package com.example.osrscritic.repo

import com.example.osrscritic.network.RetrofitService

class DisplayUserRepo(private val retrofitService: RetrofitService) {

    companion object{
        lateinit var user: String
    }

    fun getOSRSUser() = retrofitService.getOSRSPlayer(user)
}