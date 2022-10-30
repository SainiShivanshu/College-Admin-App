package com.example.collegeappadmin.activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.R
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide


import com.example.collegeappadmin.databinding.ActivityUpdateFacultyBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UpdateFacultyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateFacultyBinding
private var email:String?=null
    private lateinit var items: ArrayList<String>
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUpdateFacultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

         email = intent.getStringExtra("email")

binding.facultyEmail.setText(email)

        supportActionBar?.title="Update Info"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dialog= ProgressDialog(this)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Faculty Info being uploaded")
        dialog.setCancelable(false)

        items= arrayListOf("Select Department","Chemical Engineering",
            "Computer Science & Engineering","Electronics Engineering",
            "Petroleum Engineering & Geoengineering","Mathematical Science","Management Studies",
            "Science and Humanities")
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item,items)
        binding.facultyDepartment.adapter=arrayAdapter

        binding.facultyDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                department= binding.facultyDepartment.selectedItem.toString()
            }

        }
        loadUserInfo()


        binding.facultyImage.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchGalleryActivity.launch(intent)
        }
        binding.AddFacultyBtn.setOnClickListener {
            validateData()
        }

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
            Toast.makeText(this,"Select Department", Toast.LENGTH_SHORT).show()
        }
        else if(facultyImage==null){
            InsertData()
   
        }
        else{
            uploadImage()
        }
    }

    private fun InsertData() {
        val map = hashMapOf<String,Any>()
        map["name"]=binding.FacultyName.text.toString()
        map["post"]=binding.facultyPost.text.toString()
        map["department"]=department!!

        Firebase.firestore.collection("Faculty")
            .document(email!!)
            .update(map).addOnSuccessListener {
                Toast.makeText(this,"Profile Updated!!",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("Faculty/$fileName")
        refStorage.putFile(facultyImage!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { image->
                    updateData(image!!.toString())
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(this,"Something Went Wrong with storage" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateData(image: String) {
        val map = hashMapOf<String,Any>()
        map["name"]=binding.FacultyName.text.toString()
        map["post"]=binding.facultyPost.text.toString()
        map["image"]=image
        map["department"]=department!!

        Firebase.firestore.collection("Faculty")
            .document(email!!)
            .update(map).addOnSuccessListener {
                Toast.makeText(this,"Profile Updated!!",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }


    private fun loadUserInfo() {
        Firebase.firestore.collection("Faculty")
            .document(email!!)
            .get().addOnSuccessListener {
                binding.FacultyName.setText(it.getString("name"))
                binding.facultyPost.setText(it.getString("post"))
                Glide.with(this).load(it.getString("image")).into(binding.facultyImage)
                var i = 0

                when (it.getString("department")) {
                    "Chemical Engineering" -> {
                        i = 1
                        department="Chemical Engineering"
                    }
                    "Computer Science & Engineering" -> {
                        i = 2
                        department= "Computer Science & Engineering"
                    }
                    "Electronics Engineering" -> {
                        i = 3
                        department= "Electronics Engineering"
                    }
                    "Petroleum Engineering & Geoengineering" -> {
                        i = 4
                        department= "Petroleum Engineering & Geoengineering"
                    }
                    "Mathematical Science" -> {
                        i = 5
                        department="Mathematical Science"
                    }
                    "Management Studies" -> {
                        i = 6
                        department="Management Studies"
                    }
                    "Science and Humanities" -> {
                        i = 7
                        department="Science and Humanities"
                    }

                }
                binding.facultyDepartment.setSelection(i)


            }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}