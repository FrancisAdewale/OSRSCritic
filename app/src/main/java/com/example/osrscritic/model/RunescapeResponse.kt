package com.example.osrscritic.model

data class RunescapeResponse(
    val rank: String,
    val combatlevel: Int,
    val skillvalues: List<Skillvalue>,
    val totalxp: Int,
    val totalskill: Int,
    val name: String
)
