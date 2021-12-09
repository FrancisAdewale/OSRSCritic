package com.example.osrscritic.model

import java.io.Serializable

data class User(
    val uid: String,
    val email: String?,
    val password: String,
) : Serializable {
    constructor() : this("","","")

}
