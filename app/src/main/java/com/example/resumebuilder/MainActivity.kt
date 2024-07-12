package com.example.resumebuilder

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPersonalInfo: LinearLayout = findViewById(R.id.btnPersonalInfo)
        val btnEducation: LinearLayout = findViewById(R.id.btnEducation)
        val btnExperience: LinearLayout = findViewById(R.id.btnExperience)
        val btnGeneratePdf: Button = findViewById(R.id.btnGeneratePdf)

        btnPersonalInfo.setOnClickListener {
            val intent = Intent(this, PersonalInfoActivity::class.java)
            startActivity(intent)
        }
        btnEducation.setOnClickListener {
            val intent = Intent(this, EducationActivity::class.java)
            startActivity(intent)
        }
        btnExperience.setOnClickListener {
            val intent = Intent(this, ExperienceActivity::class.java)
            startActivity(intent)
        }
        btnGeneratePdf.setOnClickListener {
            generatePDF()
        }
    }

    private fun generatePDF() {
        val sharedPref = getSharedPreferences("ResumeData", Context.MODE_PRIVATE)
        val name = sharedPref.getString("name", "")
        val email = sharedPref.getString("email", "")
        val phone = sharedPref.getString("phone", "")
        val school = sharedPref.getString("school", "")
        val degree = sharedPref.getString("degree", "")
        val graduationYear = sharedPref.getString("graduation_year", "")
        val project = sharedPref.getString("project", "")
        val previousWork = sharedPref.getString("previous_work", "")
        val skills = sharedPref.getString("skills", "")
        val languages = sharedPref.getString("languages", "")

        val pdfDocument = PdfDocument()
        val paint = Paint()
        val title = Paint()
        val subheading = Paint()

        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        canvas.drawColor(Color.WHITE)

        paint.color = Color.rgb(255, 165, 0) // Orange color
        paint.style = Paint.Style.FILL
        canvas.drawRect(0F, 0F, 10F, canvas.height.toFloat(), paint)

        title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        title.textSize = 36F
        title.color = Color.BLACK // Set text color to black
        canvas.drawText(name ?: "Resume", 40F, 80F, title)

        subheading.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        subheading.textSize = 24F
        subheading.color = Color.BLUE // Set text color to blue
        canvas.drawText("Personal Information", 40F, 160F, subheading)

        paint.textSize = 18F
        paint.color = Color.BLACK // Set text color to black
        canvas.drawText("Name: $name", 40F, 200F, paint)
        canvas.drawText("Email: $email", 40F, 240F, paint)
        canvas.drawText("Phone: $phone", 40F, 280F, paint)

        canvas.drawText("Education", 40F, 340F, subheading)
        canvas.drawText("School: $school", 40F, 380F, paint)
        canvas.drawText("Degree: $degree", 40F, 420F, paint)
        canvas.drawText("Graduation Year: $graduationYear", 40F, 460F, paint)

        canvas.drawText("Experience", 40F, 520F, subheading)
        canvas.drawText("Project: $project", 40F, 560F, paint)
        canvas.drawText("Previous Work: $previousWork", 40F, 600F, paint)
        canvas.drawText("Skills: $skills", 40F, 640F, paint)
        canvas.drawText("Languages Known: $languages", 40F, 680F, paint)

        pdfDocument.finishPage(page)

        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "Resume.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "PDF generated successfully!", Toast.LENGTH_SHORT).show()
            viewPDF(file)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Error generating PDF", Toast.LENGTH_SHORT).show()
        }

        pdfDocument.close()
    }

    private fun viewPDF(file: File) {
        val uri: Uri = FileProvider.getUriForFile(this, "com.example.resumebuilder.provider", file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(uri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

        val chooser = Intent.createChooser(intent, "Open PDF")
        try {
            startActivity(chooser)
        } catch (e: Exception) {
            Toast.makeText(this, "No PDF viewer found", Toast.LENGTH_SHORT).show()
        }
    }
}
