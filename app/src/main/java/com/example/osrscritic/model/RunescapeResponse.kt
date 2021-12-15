package com.example.osrscritic.model

data class RunescapeResponse(
    val rank: String,
    val combatLevel: Int,
    val skillvalues: List<Skillvalue>,
    val totalxp: Int,
    val totalskill: Int
)
