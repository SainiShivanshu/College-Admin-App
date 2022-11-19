package com.example.collegeappadmin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.collegeappadmin.Adapter.GatePassAdapter
import com.example.collegeappadmin.databinding.ActivityLocalGatePassBinding
import com.example.collegeappadmin.model.AddNoticeModel
import com.example.collegeappadmin.model.LocalGatePass
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LocalGatePassActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLocalGatePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLocalGatePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="Local Gate Pass"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
var list = ArrayList<LocalGatePass>()

        Firebase.firestore.collection("Local Gate Pass")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(LocalGatePass::class.java)
                    list.add(data)
                }
binding.LocalGatePassRecycler.adapter=GatePassAdapter(this,list)

            }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}