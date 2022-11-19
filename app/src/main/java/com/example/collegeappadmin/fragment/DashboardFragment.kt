package com.example.collegeappadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentDashboardBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Dashboard"



        binding.UploadNotice.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_uploadNoticeFragment)
        }

        binding.UploadImage.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_galleryFragment)
        }

        binding.uploadEbook.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_ebookFragment)
        }

        binding.UpdateFaculty.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_uploadFacultyFragment)
        }
        binding.deleteNotice.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_deleteNoticeFragment)
        }
        binding.GatePass.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_gatePassFragment)
        }

        return binding.root
    }

}