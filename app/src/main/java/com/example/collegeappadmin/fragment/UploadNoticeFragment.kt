package com.example.collegeappadmin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.example.collegeappadmin.Adapter.NoticeListAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentUploadNoticeBinding
import com.example.collegeappadmin.model.AddNoticeModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class UploadNoticeFragment : Fragment() {
   private lateinit var binding: FragmentUploadNoticeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUploadNoticeBinding.inflate(layoutInflater)

        (activity as AppCompatActivity).supportActionBar?.title = "Add Notice"

        val  notice = ArrayList<AddNoticeModel>()

        Firebase.firestore.collection("Notices")
            .orderBy("timestamp",Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {

                notice.clear()
                for(doc in it){
                    val data = doc.toObject(AddNoticeModel::class.java)
                    notice.add(data)
                }
                binding.noticeListRecycler.adapter=NoticeListAdapter(requireContext(),notice)

            }

      binding.AddNewNotice.setOnClickListener {
         findNavController().navigate(R.id.action_uploadNoticeFragment_to_addNoticeFragment)
      }

        return binding.root
    }






}