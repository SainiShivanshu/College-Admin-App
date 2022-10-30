package com.example.collegeappadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.collegeappadmin.databinding.ActivityFullImageBinding

class FullImageActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFullImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFullImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val image = intent.getStringExtra("image")
        Glide.with(this).load(image).into(binding.fullImage)
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}