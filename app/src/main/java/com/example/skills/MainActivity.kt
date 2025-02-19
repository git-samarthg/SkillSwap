package com.example.skills

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth // Added FirebaseAuth import for authentication
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Set up drawer layout and navigation view
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Handle bottom navigation item clicks
        findViewById<BottomNavigationView>(R.id.bottomNav).setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_progress -> loadFragment(ProgressFragment())
                R.id.nav_share -> loadFragment(ShareFragment())
                R.id.nav_uskill -> loadFragment(USkillFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
                else -> false
            }
            true
        }

        // Handle navigation drawer item clicks (including logout)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_logout -> {
                    // Call the logOutUser function when logout is selected
                    logOutUser()
                    true
                }
                else -> false
            }
        }
    }

    // Function to log out the user and redirect to SignInActivity
    private fun logOutUser() {
        // Sign out the user using FirebaseAuth
        FirebaseAuth.getInstance().signOut()

        // Clear the back stack and redirect to SignInActivity
        val intent = Intent(this, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()  // Close MainActivity to prevent back navigation
    }

    // Function to load fragments dynamically
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Function to add skill to Firestore
    fun addSkill(skillName: String) {
        val skillData = hashMapOf(
            "name" to skillName
        )

        firestore.collection("skills")
            .add(skillData)
            .addOnSuccessListener { documentReference ->
                // Handle success
                println("Skill added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Handle failure
                println("Error adding skill: $e")
            }
    }
}
