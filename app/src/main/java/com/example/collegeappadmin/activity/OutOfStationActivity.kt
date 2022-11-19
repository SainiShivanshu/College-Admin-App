package com.example.collegeappadmin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.collegeappadmin.Adapter.GatePassAdapter
import com.example.collegeappadmin.Adapter.OutOfStationAdapter
import com.example.collegeappadmin.databinding.ActivityOutOfStationBinding
import com.example.collegeappadmin.model.LocalGatePass
import com.example.collegeappadmin.model.OutOfStation
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OutOfStationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOutOfStationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOutOfStationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.title="Out of Station Gate Passes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var list = ArrayList<OutOfStation>()

        Firebase.firestore.collection("Out Of Station Gate Pass")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(OutOfStation::class.java)
                    list.add(data)
                }
                binding.OutOfStationRecycler.adapter= OutOfStationAdapter(this,list)
            }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}