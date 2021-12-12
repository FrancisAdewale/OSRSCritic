package com.example.osrscritic.model

import java.io.Serializable

data class User(
    val uid: String,
    val email: String?,
    val firstName: String,
    val secondName: String,
    val password: String,
    var lng: Double = 0.0,
    var lat: Double = 0.0,
    val completedRegistration: Boolean = false,
) : Serializable {
    constructor() : this("","","","","")

}
