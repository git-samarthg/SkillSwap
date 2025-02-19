package com.example.skills

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize buttons
        val addSkillButton = view.findViewById<Button>(R.id.button_add_skill)

        // Handle add skill button click
        addSkillButton.setOnClickListener {
            // Start AddSkillActivity
            val intent = Intent(activity, AddSkillActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
