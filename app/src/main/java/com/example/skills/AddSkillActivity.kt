package com.example.skills

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddSkillActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skill)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize EditTexts
        val skillNameEditText = findViewById<EditText>(R.id.edit_text_skill_name)
        val hoursAvailableEditText = findViewById<EditText>(R.id.edit_text_hours_available)
        val levelEditText = findViewById<EditText>(R.id.edit_text_level)
        val typeEditText = findViewById<EditText>(R.id.edit_text_type)
        val yearsOfExperienceEditText = findViewById<EditText>(R.id.edit_text_years_of_experience)
        val contactNumberEditText =
            findViewById<EditText>(R.id.edit_text_contact_number)  // New field

        // Initialize the Add Skill button
        val addSkillButton = findViewById<Button>(R.id.button_add_skill)

        // Handle add skill button click
        addSkillButton.setOnClickListener {
            val skillName = skillNameEditText.text.toString().trim()
            val hoursAvailable = hoursAvailableEditText.text.toString().toIntOrNull() ?: 0
            val level = levelEditText.text.toString().toIntOrNull()?.coerceIn(1, 10) ?: 1
            val type = typeEditText.text.toString().trim()
            val yearsOfExperience = yearsOfExperienceEditText.text.toString().toIntOrNull() ?: 0
            val contactNumber = contactNumberEditText.text.toString().trim()  // New input

            if (skillName.isNotEmpty() && type.isNotEmpty() && contactNumber.isNotEmpty()) {
                addSkill(
                    skillName,
                    hoursAvailable,
                    level,
                    type,
                    yearsOfExperience,
                    contactNumber
                )  // Pass contact number
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addSkill(
        skillName: String,
        hoursAvailable: Int,
        level: Int,
        type: String,
        yearsOfExperience: Int,
        contactNumber: String
    ) {
        val userId = getCurrentUserId()
        val skillData = hashMapOf(
            "name" to skillName,
            "userId" to userId,
            "hoursAvailable" to hoursAvailable,
            "level" to level,
            "type" to type,
            "yearsOfExperience" to yearsOfExperience,
            "contactNumber" to contactNumber  // Add contact number to the data
        )

        firestore.collection("skills")
            .add(skillData)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    this,
                    "Skill added with ID: ${documentReference.id}",
                    Toast.LENGTH_SHORT
                ).show()
                // Optionally finish the activity or navigate back to HomeFragment
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding skill: $e", Toast.LENGTH_SHORT).show()
            }
    }

    private fun getCurrentUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: "unknown"
    }
}