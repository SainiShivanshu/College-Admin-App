package com.example.collegeappadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.collegeappadmin.Adapter.ComplainAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentComplainBinding
import com.example.collegeappadmin.model.Complain
import com.example.collegeappadmin.model.OutOfStation
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ComplainFragment : Fragment() {

private lateinit var binding:FragmentComplainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentComplainBinding.inflate(layoutInflater)


        (activity as AppCompatActivity).supportActionBar?.title="Complains"


        var list = ArrayList<Complain>()

        Firebase.firestore.collection("Complain")
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it) {
                    val data = doc.toObject(Complain::class.java)
                    list.add(data)
                }
binding.ComplainRecycler.adapter=ComplainAdapter(requireContext(),list)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_SHORT).show()
            }

        return binding.root

    }


}