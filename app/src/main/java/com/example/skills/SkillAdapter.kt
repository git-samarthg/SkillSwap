package com.example.skills

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SkillsAdapter(private val skills: List<Skill>) : RecyclerView.Adapter<SkillsAdapter.SkillViewHolder>() {

    // ViewHolder class to hold the views for each skill item
    class SkillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val skillName: TextView = itemView.findViewById(R.id.skillName)
        val skillLevel: TextView = itemView.findViewById(R.id.skillLevel)
        val skillExperience: TextView = itemView.findViewById(R.id.skillExperience)
        val skillHours: TextView = itemView.findViewById(R.id.skillHours)
        val skillContact: TextView = itemView.findViewById(R.id.skillContact)  // New field for contact number
    }

    // Called when the adapter needs a new ViewHolder to represent an item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false)
        return SkillViewHolder(view)
    }

    // Called to display data at a specified position
    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = skills[position]

        // Bind the skill data to the views
        holder.skillName.text = "Skill: ${skill.skillType}"  // Correctly displaying the skill type
        holder.skillLevel.text = "Level: ${skill.level}"
        holder.skillExperience.text = "Years of Experience: ${skill.yearsOfExperience}"
        holder.skillHours.text = "Hours Available: ${skill.hoursAvailable}"
        holder.skillContact.text = "Contact Number: ${skill.contactNumber}"  // Display contact number
    }

    // Returns the total number of items in the data set
    override fun getItemCount() = skills.size
}
