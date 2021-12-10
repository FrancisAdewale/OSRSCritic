package com.example.osrscritic.model

import java.io.Serializable

data class GoogleUser(
    val uid: String,
    val name: String?,
    val email: String?,
    var lng: Double = 0.0,
    var lat: Double = 0.0,
    val completedRegistration: Boolean = false,
    var isAuthenticated: Boolean = false,
    var isNew: Boolean? = false,
    var isCreated: Boolean = false
) : Serializable {

    constructor() : this("","","")

}
