package com.example.tenakata

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.tenakata.databinding.ActivityViewDetailsBinding
import kotlinx.android.synthetic.main.activity_view_details.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class ViewDetailsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityViewDetailsBinding
    private val sharedPrefFile = "tenakata"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityViewDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
             val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        val name = sharedPreferences.getString("name","defaultname")
        val age = sharedPreferences.getString("age","defaultname")
        val location = sharedPreferences.getString("location","defaultname")
        val marital = sharedPreferences.getString("marital","defaultname")
        val iq = sharedPreferences.getString("iq","defaultname")
        val height = sharedPreferences.getString("height","defaultname")
        val image=sharedPreferences.getString("image","defaultname")
         binding.txtName.text = name
        binding.txtAge.text = age
        binding.txtLocation.text =location
        binding.txtMarital.text = marital
        binding.txtiQ.text = iq
        binding.txtheight.text=height

        binding.generatePdf.setOnClickListener {
            generatesPdf(name,age,location,marital,iq)
        }

    }

    private fun generatesPdf(name: String?, age: String?, location: String?, marital: String?, iq: String?) {
     var pdf:String= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()
     var file:File= File(pdf,"tenakata.pdf")
     var outputStream:OutputStream=FileOutputStream(file)

//      var pdfWritter:Pdfwriter=Pdfwriter(file)
//      var pdfDocument:PdfDocument= PdfDocument()
//      var documents:Document= DocumentsContract.Document(pdfDocument)



    }
}