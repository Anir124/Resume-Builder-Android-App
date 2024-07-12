package com.example.resumebuilder

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EducationActivity : AppCompatActivity() {

    private lateinit var etSchool: EditText
    private lateinit var etDegree: EditText
    private lateinit var etGraduationYear: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education)

        etSchool = findViewById(R.id.etSchool)
        etDegree = findViewById(R.id.etDegree)
        etGraduationYear = findViewById(R.id.etGraduationYear)

        // Load saved data
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        etSchool.setText(sharedPref.getString("school", ""))
        etDegree.setText(sharedPref.getString("degree", ""))
        etGraduationYear.setText(sharedPref.getString("graduation_year", ""))
    }

    override fun onPause() {
        super.onPause()
        // Save data
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("school", etSchool.text.toString())
            putString("degree", etDegree.text.toString())
            putString("graduation_year", etGraduationYear.text.toString())
            apply()
        }
    }
}
