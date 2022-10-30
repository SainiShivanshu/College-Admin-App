package com.example.collegeappadmin.fragment

import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.collegeappadmin.databinding.FragmentAddFacultyBinding
import com.example.collegeappadmin.model.AddFacultyModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import java.util.*


class AddFacultyFragment : Fragment() {

private lateinit var binding:FragmentAddFacultyBinding
    private lateinit var items:ArrayList<String>
    private var department:String?=null
    private var facultyImage: Uri?=null
    private lateinit var dialog: ProgressDialog

    private var launchGalleryActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode== Activity.RESULT_OK){
            facultyImage=it.data!!.data
            binding.facultyImage.setImageURI(facultyImage)

        }
    }
//   private val imageUri = Uri.parse(
//        ContentResolver.SCHEME_ANDROID_RESOURCE +
//                "://" + resources.getResourcePackageName(com.example.collegeappadmin.R.drawable.profile)
//                + '/' + resources.getResourceTypeName(com.example.collegeappadmin.R.drawable.profile) + '/' + resources.getResourceEntryName(
//            com.example.collegeappadmin.R.drawable.profile
//        )
//    )
//var uri: Uri? = Uri.parse("android.resource://"+resources.getResourcePackageName(com.example.collegeappadmin.R.drawable.profile)+"/drawable/profile")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddFacultyBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Add Faculty"

        items= arrayListOf("Select Department","Chemical Engineering",
            "Computer Science & Engineering","Electronics Engineering",
            "Petroleum Engineering & Geoengineering","Mathematical Science","Management Studies",
            "Science and Humanities")
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item,items)
       binding.facultyDepartment.adapter=arrayAdapter

        binding.facultyDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                department= binding.facultyDepartment.selectedItem.toString()
            }

        }

        dialog= ProgressDialog(requireContext())
        dialog.setTitle("Please Wait")
        dialog.setMessage("Faculty Info being uploaded")
        dialog.setCancelable(false)

        binding.facultyImage.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchGalleryActivity.launch(intent)
        }

        binding.AddFacultyBtn.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private fun validateData() {
        if(binding.FacultyName.text.toString().isEmpty()){
            binding.FacultyName.requestFocus()
            binding.FacultyName.error="Empty"
        }
        else if(binding.facultyEmail.text.toString().isEmpty()){
            binding.facultyEmail.requestFocus()
            binding.facultyEmail.error="Empty"
        }
        else if(binding.facultyPost.text.toString().isEmpty()){
            binding.facultyPost.requestFocus()
            binding.facultyPost.error="Empty"
        }
        else if(department==null || department=="Select Department"){
            Toast.makeText(requireContext(),"Select Department", Toast.LENGTH_SHORT).show()
        }
        else if(facultyImage==null){
//            InsertData()
            Toast.makeText(requireContext(),"Select Faculty Image", Toast.LENGTH_SHORT).show()
        }
        else{
            uploadImage()
        }
    }

//    private fun InsertData() {
//        val db = Firebase.firestore.collection("Faculty")
//        val key = db.document().id
//
//        val data =AddFacultyModel(
//            key,
//            binding.FacultyName.text.toString(),
//            binding.facultyEmail.text.toString(),
//            binding.facultyPost.text.toString(),
//            department,
//            imageUri.toString()
//        )
//        db.document(key).set(data).addOnSuccessListener {
//            dialog.dismiss()
//            Toast.makeText(requireContext(),"Faculty Info Uploaded",Toast.LENGTH_SHORT).show()
//            binding.FacultyName.text=null
//            binding.facultyEmail.text=null
//            binding.facultyPost.text=null
//            binding.facultyDepartment.setSelection(0)
//            department=null
//
//        }
//            .addOnFailureListener {
//                dialog.dismiss()
//                Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()
//            }
//    }

    private fun uploadImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("Faculty/$fileName")
        refStorage.putFile(facultyImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image->
                        storeData(image!!.toString())
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(),"Something Went Wrong with storage" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(image: String) {
        val db = Firebase.firestore.collection("Faculty")
        val key = db.document().id

        val data =AddFacultyModel(
            key,
            binding.FacultyName.text.toString(),
            binding.facultyEmail.text.toString(),
            binding.facultyPost.text.toString(),
            department,
            image
        )
        db.document(binding.facultyEmail.text.toString()).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"Faculty Info Uploaded",Toast.LENGTH_SHORT).show()
            binding.FacultyName.text=null
            binding.facultyEmail.text=null
            binding.facultyPost.text=null
            binding.facultyDepartment.setSelection(0)
            department=null
//           binding.facultyImage.setImageURI(uri)

        }
            .addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()
        }
    }

}






