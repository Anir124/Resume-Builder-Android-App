package com.example.resumebuilder

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ExperienceActivity : AppCompatActivity() {

    private lateinit var etProject: EditText
    private lateinit var etPreviousWork: EditText
    private lateinit var etSkills: EditText
    private lateinit var etLanguages: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_experience)

        etProject = findViewById(R.id.etProject)
        etPreviousWork = findViewById(R.id.etPreviousWork)
        etSkills = findViewById(R.id.etSkills)
        etLanguages = findViewById(R.id.etLanguages)

        // Load saved data
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        etProject.setText(sharedPref.getString("project", ""))
        etPreviousWork.setText(sharedPref.getString("previous_work", ""))
        etSkills.setText(sharedPref.getString("skills", ""))
        etLanguages.setText(sharedPref.getString("languages", ""))
    }

    override fun onPause() {
        super.onPause()
        // Save data
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("project", etProject.text.toString())
            putString("previous_work", etPreviousWork.text.toString())
            putString("skills", etSkills.text.toString())
            putString("languages", etLanguages.text.toString())
            apply()
        }
    }
}
