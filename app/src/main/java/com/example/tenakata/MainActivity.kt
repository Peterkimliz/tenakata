package com.example.tenakata

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.tenakata.consts.Constants.Companion.sharedname
import com.example.tenakata.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    private val LOCATION_PERMISSION_REQ_CODE = 1000;
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private val sharedPrefFile = "tenakata"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        binding.nextButton.setOnClickListener {
            varifyData()
        }
//        binding.location.setOnClickListener {
//            getCurrentLocation()
//        }
    }

    private fun varifyData() {
        val names = binding.names.text.toString()
        val age = binding.age.text.toString()
        val location = binding.location.text.toString()
        val height=binding.height.text.toString()
        val marital = binding.maritalStatus.text.toString()

        if (names.isEmpty()) {
            Toast.makeText(this, "please enter your name", Toast.LENGTH_SHORT).show()
        } else if (age.isEmpty()) {
            Toast.makeText(this, "please enter your age", Toast.LENGTH_SHORT).show()
        } else if (location.isEmpty()) {
            Toast.makeText(this, "please enter your location", Toast.LENGTH_SHORT).show()
        }
        else if (height.isEmpty()) {
            Toast.makeText(this, "please enter your height ", Toast.LENGTH_SHORT).show()
        }

        else if (marital.isEmpty()) {
            Toast.makeText(this, "please enter your marital status", Toast.LENGTH_SHORT).show()
        } else {
            var intent = Intent(this@MainActivity, ImagePageActivity::class.java)
            intent.putExtra("name", names)
            intent.putExtra("age", age)
            intent.putExtra("height", height)
            intent.putExtra("location", location);
            intent.putExtra("marital", marital)
            startActivity(intent)
        }

    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
            );
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                latitude = location.latitude
                longitude = location.longitude
                Log.i("lonitude",location.latitude.toString())
                val geoCoder = Geocoder(this, Locale.getDefault())
                val Adress = geoCoder.getFromLocation(latitude, longitude, 3)
                val cityName = Adress.get(0).locality
                var countryName = Adress.get(0).countryName

                binding.location.setText(cityName)

            }
            .addOnFailureListener {
                Toast.makeText(
                    this, "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun onStart() {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE)
        if (sharedPreferences.contains("name")){
            val intent=Intent(this,ViewDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onStart()
    }
}