package com.example.collegeappadmin.fragment

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.example.collegeappadmin.Adapter.UploadImageAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentUploadImageBinding

import com.example.collegeappadmin.model.UploadImageModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import java.util.*
import kotlin.collections.ArrayList


class UploadImageFragment : Fragment() {
    private lateinit var binding: FragmentUploadImageBinding
    private lateinit var list:ArrayList<Uri>
    private lateinit var listImages : ArrayList<String>
    private lateinit var dialog: ProgressDialog
    private  var coverImgUrl : String?=null
    private lateinit var categoryList:ArrayList<String>
    private lateinit var items:ArrayList<String>
    private var category:String?=null
    private lateinit var adapter: UploadImageAdapter

    private var launchProductActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode== Activity.RESULT_OK){
            val imageUrl=it.data!!.data
            list.add(imageUrl!!)
            adapter.notifyDataSetChanged()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentUploadImageBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Add Images"

        items= arrayListOf("Select Category","Convocation","Republic Day","Independence Day","Gandhi Jayanti","Urjostav","Others")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item_layout,items)
        binding.dropdown.adapter=arrayAdapter



       binding.dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              category=binding.dropdown.selectedItem.toString()
            }

        }


        list=ArrayList()
        listImages= ArrayList()

        dialog= ProgressDialog(requireContext())
       dialog.setTitle("Please Wait")
        dialog.setMessage("Images being uploaded")
        dialog.setCancelable(false)

        binding.AddGalleryImg.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchProductActivity.launch(intent)
        }
        adapter= UploadImageAdapter(list)
        binding.ImageRecylerView.adapter=adapter
        binding.UploadImageBtn.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private fun validateData() {
        if(category==null || category=="Select Category"){
            Toast.makeText(requireContext(),"Select Category",Toast.LENGTH_SHORT).show()
        }
        else if(list.size<1){
            Toast.makeText(requireContext(),"Select notice image", Toast.LENGTH_SHORT).show()

        }
        else{
            uploadImage()
        }
    }
    private var i=0
    private fun uploadImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("Gallery/$category/$fileName")
        refStorage.putFile(list[i]!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image->
                    listImages.add(image!!.toString())
                    if (list.size==listImages.size)
                    {
                        storeData()
                    }
                    else{
                        i++
                        uploadImage()
                    }
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(),"Something Went Wrong with storage" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData() {
        val db = Firebase.firestore.collection("Gallery")
        val key = db.document().id

        val data = UploadImageModel(
            category,
            listImages
        )

        db.document(key).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"Image Uploaded",Toast.LENGTH_SHORT).show()
            category=null
            i=0
            binding.dropdown.setSelection(0)
            list.clear()
            listImages.clear()
            adapter.notifyDataSetChanged()

        }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }
    }




