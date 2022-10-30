package com.example.collegeappadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.collegeappadmin.Adapter.FacultyAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentUploadFacultyBinding
import com.example.collegeappadmin.model.AddFacultyModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UploadFacultyFragment : Fragment() {

private lateinit var binding: FragmentUploadFacultyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentUploadFacultyBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Faculty"
        getCheInfo()
        getCsInfo()
        getElecInfo()
        getMathInfo()
        getMangInfo()
        getPetInfo()
        getScienInfo()

        binding.AddNewFaculty.setOnClickListener {
            findNavController().navigate(R.id.action_uploadFacultyFragment_to_addFacultyFragment)
        }

        return binding.root
    }

    private fun getScienInfo() {
        val  Scien = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Science and Humanities" )
            .get()
            .addOnSuccessListener {

                Scien.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Scien.add(data!!)
                }
                if(!Scien.isEmpty()){
                    binding.ScienceNoData.visibility=View.GONE
                    binding.ScienceDept.visibility=View.VISIBLE
                    binding.ScienceDept.adapter=FacultyAdapter(requireContext(),Scien)
                }

            }



    }

    private fun getPetInfo() {
        val  pet = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Petroleum Engineering & Geoengineering" )
            .get()
            .addOnSuccessListener {

                pet.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    pet.add(data!!)
                }
                if(!pet.isEmpty()){
                    binding.PetNoData.visibility=View.GONE
                    binding.PetDept.visibility=View.VISIBLE
                    binding.PetDept.adapter=FacultyAdapter(requireContext(),pet)
                }

            }

    }

    private fun getMangInfo() {
        val  Mang = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Management Studies" )
            .get()
            .addOnSuccessListener {

                Mang.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Mang.add(data!!)
                }
                if(!Mang.isEmpty()){
                    binding.MangNoData.visibility=View.GONE
                    binding.MangDept.adapter=FacultyAdapter(requireContext(),Mang)
                    binding.MangDept.visibility=View.VISIBLE
                }

            }
    }

    private fun getMathInfo() {
        val  Math = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Mathematical Science" )
            .get()
            .addOnSuccessListener {

                Math.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Math.add(data!!)
                }
                if(!Math.isEmpty()){
                    binding.MathNoData.visibility=View.GONE
                    binding.MathDept.adapter=FacultyAdapter(requireContext(),Math)
                    binding.MathDept.visibility=View.VISIBLE
                }

            }
    }

    private fun getElecInfo() {
        val Elec = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Electronics Engineering" )
            .get()
            .addOnSuccessListener {

                Elec.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Elec.add(data!!)
                }
                if(!Elec.isEmpty()){
                    binding.ElecNoData.visibility=View.GONE
                    binding.ElecDept.adapter=FacultyAdapter(requireContext(),Elec)
                    binding.ElecDept.visibility=View.VISIBLE
                }

            }
    }

    private fun getCheInfo() {
        val Che = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Chemical Engineering" )
            .get()
            .addOnSuccessListener {
                Che.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Che.add(data!!)
                }
                if(!Che.isEmpty()){
                    binding.CheNoData.visibility=View.GONE
                    binding.CheDept.visibility=View.VISIBLE
                    binding.CheDept.adapter=FacultyAdapter(requireContext(),Che)

                }

            }
    }

    private fun getCsInfo() {

        val   Cs = ArrayList<AddFacultyModel>()

        Firebase.firestore.collection("Faculty")
            .whereEqualTo("department","Computer Science & Engineering" )
            .get()
            .addOnSuccessListener {

                Cs.clear()
                for(doc in it){
                    val data = doc.toObject(AddFacultyModel::class.java)
                    Cs.add(data!!)
                }
                if(!Cs.isEmpty()){
                    binding.CsNoData.visibility=View.GONE
                    binding.CsDept.visibility=View.VISIBLE
                    binding.CsDept.adapter=FacultyAdapter(requireContext(),Cs)
                }

            }

            }

    }


