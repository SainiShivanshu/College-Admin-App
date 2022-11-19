package com.example.collegeappadmin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.collegeappadmin.Adapter.GalleryImageAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentGalleryBinding
import com.example.collegeappadmin.model.UploadImageModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GalleryFragment : Fragment() {

private lateinit var binding:FragmentGalleryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentGalleryBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Gallery"

        getRepublic()
        getIndependence()
        getConvocation()
        getGandhiJayanti()
        getUrjostav()
        getOthers()

        binding.AddNewImage.setOnClickListener {
            findNavController().navigate(R.id.action_galleryFragment_to_uploadImageFragment)
        }

        return binding.root
    }

    private fun getOthers() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Others")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){
                    binding.Others.visibility=View.VISIBLE
                    binding.otherRecycler.adapter= GalleryImageAdapter(requireContext(),oth)
                    binding.otherRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })

    }

    private fun getUrjostav() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Urjostav")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){
                    binding.Urjostsav.visibility=View.VISIBLE
                    binding.UrjosatavRecycler.adapter=GalleryImageAdapter(requireContext(),oth)
                    binding.UrjosatavRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun getGandhiJayanti() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Gandhi Jayanti")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){
                    binding.GandhiJayanti.visibility=View.VISIBLE
                    binding.GandhiRecycler.adapter=GalleryImageAdapter(requireContext(),oth)
                    binding.GandhiRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun getConvocation() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Convocation")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){
                    binding.Convocation.visibility=View.VISIBLE
                    binding.ConvoRecycler.adapter=GalleryImageAdapter(requireContext(),oth)
                    binding.ConvoRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun getIndependence() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Independence Day")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){
                    binding.IndependenceDay.visibility=View.VISIBLE
                    binding.independenceRecycler.adapter=GalleryImageAdapter(requireContext(),oth)
                    binding.independenceRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })
    }

    private fun getRepublic() {
        val oth = ArrayList<String>()

        Firebase.firestore.collection("Gallery")
            .whereEqualTo("category", "Republic Day")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get().addOnSuccessListener {
                oth.clear()
                for (doc in it) {
                    val data = doc.toObject(UploadImageModel::class.java)
                    for (i in data.image) {
                        oth.add(i)
                    }
                }
                if(!oth.isEmpty()){

                    binding.Republicday.visibility=View.VISIBLE
                    binding.republicRecycler.adapter=GalleryImageAdapter(requireContext(),oth)
                    binding.republicRecycler.layoutManager= GridLayoutManager(requireContext(),3)
                }

            }
            .addOnFailureListener(OnFailureListener {
                Toast.makeText(requireContext(), "Something went wrong!!", Toast.LENGTH_SHORT)
                    .show()
            })
    }


}