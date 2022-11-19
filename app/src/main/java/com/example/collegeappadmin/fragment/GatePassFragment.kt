package com.example.collegeappadmin.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.collegeappadmin.R
import com.example.collegeappadmin.activity.LocalGatePassActivity
import com.example.collegeappadmin.activity.OutOfStationActivity
import com.example.collegeappadmin.databinding.FragmentGatePassBinding


class GatePassFragment : Fragment() {
    private lateinit var binding:FragmentGatePassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGatePassBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding.LocalGatePass.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(),LocalGatePassActivity::class.java))
        }
        binding.OutOfStation.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(),OutOfStationActivity::class.java))
        }
        return binding.root
    }


}