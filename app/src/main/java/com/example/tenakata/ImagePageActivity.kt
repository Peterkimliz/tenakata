package com.example.tenakata

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenakata.databinding.ActivityImagePageBinding


class ImagePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImagePageBinding;
    lateinit var uri: Uri
    private val sharedPrefFile = "tenakata"
    lateinit var name: String
    lateinit var age: String
    lateinit var location: String
    lateinit var height: String
    lateinit var marital: String
    private val CAMERA_REQUEST = 1888
    lateinit var photo:Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImagePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Image Page"
        val bundle: Bundle = intent.extras!!
        name = bundle.get("name").toString()
        age = bundle.get("age").toString()
        height=bundle.get("height").toString()
        location = bundle.get("location").toString()
        marital = bundle.get("marital").toString()

        binding.floatingButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )
        binding.finishButton.setOnClickListener {
            val testResult = binding.iqResult.text.toString()

                if (testResult.isEmpty()) {
                    Toast.makeText(this, "please enter Iq result", Toast.LENGTH_SHORT).show()
                }
                else {
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("name", name)
                    editor.putString("age", age)
                    editor.putString("location", location)
                    editor.putString("marital", marital)
                    editor.putString("iq", testResult)
                    editor.putString("height", height)
                    editor.putString("image",photo.toString())
                    editor.apply()
                    val intent = Intent(this, ViewDetailsActivity::class.java)
                    startActivity(intent)
                    finish()

                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST) {
           photo = (data?.extras!!["data"] as Bitmap?)!!
            binding.imageImage.setImageBitmap(photo)
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }




}