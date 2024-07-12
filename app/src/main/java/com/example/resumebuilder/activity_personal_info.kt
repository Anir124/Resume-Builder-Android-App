package com.example.resumebuilder

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PersonalInfoActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhone = findViewById(R.id.etPhone)
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        etName.setText(sharedPref.getString("name", ""))
        etEmail.setText(sharedPref.getString("email", ""))
        etPhone.setText(sharedPref.getString("phone", ""))
        setContentView(R.layout.activity_personal_info)

        setContentView(R.layout.activity_personal_info)

    }

    override fun onPause() {
        super.onPause()
        // Save data
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("name", etName.text.toString())
            putString("email", etEmail.text.toString())
            putString("phone", etPhone.text.toString())
            apply()
        }
    }

}
