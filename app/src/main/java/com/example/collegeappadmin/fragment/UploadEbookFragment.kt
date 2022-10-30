package com.example.collegeappadmin.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.collegeappadmin.Adapter.AddNoticeAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentUploadEbookBinding
import com.example.collegeappadmin.model.AddEbookModel
import com.example.collegeappadmin.model.AddNoticeModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class UploadEbookFragment : Fragment() {

    private var pdfData : Uri?=null
    private lateinit var dialog: ProgressDialog

    private lateinit var binding: FragmentUploadEbookBinding

    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    private var launchGalleryActivity =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode== Activity.RESULT_OK){
           pdfData = it.data!!.data

            if(pdfData.toString().startsWith("content://")){
var cursor:Cursor?=null
                cursor=requireContext().contentResolver.query(pdfData!!,null,null,null,null)
                if(cursor!=null && cursor.moveToFirst()){
                    binding.pdfName.text=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }

            }
            else if(pdfData.toString().startsWith("file://")){
                binding.pdfName.text = File(pdfData.toString()).name
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
 binding=FragmentUploadEbookBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.title="Add Ebook"

        dialog= ProgressDialog(requireContext())
//        dialog.setContentView(R.layout.progress_layout)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Ebook is Uploading")
        dialog.setCancelable(false)

     binding.AddEbookPdf.setOnClickListener {
         val intent= Intent("android.intent.action.GET_CONTENT")
         intent.type="application/pdf"

             launchGalleryActivity.launch(intent)

     }

        binding.UploadEbookBtn.setOnClickListener {
            validateData()
        }

        return binding.root
    }

    private fun validateData() {
        if(binding.EbookTitle.text.toString().isEmpty()){
            binding.EbookTitle.requestFocus()
            binding.EbookTitle.error="Empty"
        }
        else{
            uploadPdf()
        }
    }

    private fun uploadPdf() {
        var pname:String=binding.pdfName.text.toString()
        dialog.show()
        if(binding.pdfName.text.toString().endsWith(".pdf")){
            pname=pname.removeSuffix(".pdf")
        }
        val refStorage = FirebaseStorage.getInstance().reference.child("Ebooks/"+pname+"-"+System.currentTimeMillis()+".pdf")
        refStorage.putFile(pdfData!!)
            .addOnSuccessListener {
                it.storage.downloadUrl.addOnSuccessListener { pdfUri->
                    storeData(pdfUri.toString())
                }
            }
            .addOnFailureListener{
                dialog.dismiss()
                Toast.makeText(requireContext(),"Something Went Wrong with storage" , Toast.LENGTH_SHORT).show()
            }
    }

    private fun storeData(pdfUri: String) {
        val db = Firebase.firestore.collection("Ebooks")

        val key = db.document().id

        val data = AddEbookModel(
            key,
            binding.EbookTitle.text.toString(),
            pdfUri
        )
        db.document(key).set(data).addOnSuccessListener {
            dialog.dismiss()
            Toast.makeText(requireContext(),"Ebook Uploaded",Toast.LENGTH_SHORT).show()
            binding.EbookTitle.text=null
            binding.pdfName.text="No file selected"
            pdfData=null

        }
            .addOnFailureListener {
                dialog.dismiss()
                Toast.makeText(requireContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }


}