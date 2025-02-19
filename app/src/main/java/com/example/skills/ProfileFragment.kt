package com.example.skills

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private lateinit var welcomeTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var motivationTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize TextViews
        welcomeTextView = view.findViewById(R.id.welcomeTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        motivationTextView = view.findViewById(R.id.motivationTextView)

        // Get user data from Firebase Auth
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val username = it.displayName ?: "User" // You can also retrieve this from your database if stored
            val email = it.email ?: "No email available"

            // Update UI with user data
            welcomeTextView.text = "Welcome, $username!"
            emailTextView.text = "Email: $email"
        } ?: run {
            // Handle case where user is not signed in
            welcomeTextView.text = "Welcome, Guest!"
            emailTextView.text = "Email: Not available"
        }

        motivationTextView.text = "Why are you waiting? Start exchanging your skills!"

        return view
    }
}
