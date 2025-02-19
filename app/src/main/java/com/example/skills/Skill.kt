package com.example.skills

data class Skill(
    val skillType: String = "",
    val level: Long = 0,
    val yearsOfExperience: Long = 0,
    val hoursAvailable: Long = 0,
    val contactNumber: String = ""  // Ensure this is a String
)
