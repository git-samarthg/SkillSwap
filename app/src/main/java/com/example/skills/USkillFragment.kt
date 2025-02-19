package com.example.skills

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class USkillFragment : Fragment() {

    private lateinit var skillsRecyclerView: RecyclerView
    private lateinit var skillsAdapter: SkillsAdapter
    private val skillsList = mutableListOf<Skill>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_uskill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        skillsRecyclerView = view.findViewById(R.id.skillsRecyclerView)
        skillsAdapter = SkillsAdapter(skillsList)
        skillsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        skillsRecyclerView.adapter = skillsAdapter

        fetchSkillsFromFirestore() // Fetch skills data
    }

    private fun fetchSkillsFromFirestore() {
        val userId = getCurrentUserId() // Get the current user's ID

        db.collection("skills")
            .whereEqualTo("userId", userId) // Filter by userId
            .get()
            .addOnSuccessListener { result ->
                skillsList.clear() // Clear the previous list
                for (document in result) {
                    val skillType = document.getString("type") ?: ""  // Use "type" to get the skill name
                    val level = document.getLong("level") ?: 0
                    val yearsOfExperience = document.getLong("yearsOfExperience") ?: 0
                    val hoursAvailable = document.getLong("hoursAvailable") ?: 0
                    val contactNumber = document.getString("contactNumber") ?: "" // Retrieve contact number

                    // Add the skill to the list
                    skillsList.add(Skill(skillType, level, yearsOfExperience, hoursAvailable,contactNumber))
                }
                skillsAdapter.notifyDataSetChanged() // Notify the adapter about data changes
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Error getting documents: ${exception.message}", Toast.LENGTH_SHORT).show()
                Log.e("USkillFragment", "Error getting documents: ", exception)
            }
    }

    private fun getCurrentUserId(): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.uid ?: "unknown" // Return the current user's ID or "unknown"
    }
}
