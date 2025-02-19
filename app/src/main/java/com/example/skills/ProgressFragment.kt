package com.example.skills

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.view.animation.DecelerateInterpolator

class ProgressFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var progressText: TextView
    private lateinit var firestore: FirebaseFirestore

    private val maxSkills = 10 // Maximum number of skills for 100% progress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        // Initialize views
        progressBar = view.findViewById(R.id.progressBar)
        progressText = view.findViewById(R.id.progressText)

        firestore = FirebaseFirestore.getInstance()

        // Calculate user's skill progress and update the progress bar
        calculateUserSkillProgress()

        return view
    }

    // Function to calculate and update the user's skill progress
    private fun calculateUserSkillProgress() {
        val userId = getCurrentUserId()

        firestore.collection("skills")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val skillCount = result.size() // Get the number of skills the user has

                // Calculate progress as a percentage out of 10
                val progress = (skillCount.coerceAtMost(maxSkills) * 100) / maxSkills

                // Set animated progress
                setProgressWithAnimation(progressBar, progress)
                progressText.text = "Skill Meter"
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error fetching skills: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("ProgressFragment", "Error fetching skills: ", exception)
                progressText.text = "Error fetching skills"
            }
    }

    // Function to get the current user ID from Firebase Auth
    private fun getCurrentUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: "unknown" // Return the current user's ID or "unknown" if not available
    }

    // Function to animate the progress bar smoothly
    private fun setProgressWithAnimation(progressBar: ProgressBar, progress: Int) {
        val animation = ObjectAnimator.ofInt(progressBar, "progress", 0, progress)
        animation.duration = 1000 // Duration of 1 second for the animation
        animation.interpolator = DecelerateInterpolator()
        animation.start()
    }
}
