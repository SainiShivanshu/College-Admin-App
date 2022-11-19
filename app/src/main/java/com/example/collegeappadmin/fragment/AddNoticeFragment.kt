package com.example.collegeappadmin.fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.collegeappadmin.Adapter.AddNoticeAdapter
import com.example.collegeappadmin.databinding.FragmentAddNoticeBinding
import com.example.collegeappadmin.model.AddNoticeModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FieldValue

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AddNoticeFragment : Fragment() {
 private lateinit var binding:FragmentAddNoticeBinding

    private lateinit var dialog: ProgressDialog
    private lateinit var adapter: AddNoticeAdapter
    private lateinit var list: ArrayList<Uri>
    private lateinit var listImages : ArrayList<String>

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
      binding=FragmentAddNoticeBinding.inflate(layoutInflater)

        (activity as AppCompatActivity).supportActionBar?.title="Add Notice"

        list=ArrayList()
        listImages= ArrayList()

        dialog= ProgressDialog(requireContext())
//        dialog.setContentView(R.layout.progress_layout)
        dialog.setTitle("Please Wait")
        dialog.setMessage("Notice is Uploading")
        dialog.setCancelable(false)

        binding.AddNoticeImg.setOnClickListener {
            val intent= Intent("android.intent.action.GET_CONTENT")
            intent.type="image/*"
            launchProductActivity.launch(intent)
        }

        adapter= AddNoticeAdapter(list)
        binding.NoticeRecylerView.adapter=adapter
        binding.UploadNoticeBtn.setOnClickListener {
            validateData()
        }
        return binding.root
    }

    private fun validateData() {
        if(binding.NoticeTitle.text.toString().isEmpty()){
            binding.NoticeTitle.requestFocus()
            binding.NoticeTitle.error="Empty"
        }
        else if(list.size<1){
            storeData()
        }

        else{
            uploadImage()
        }
    }
    private var i=0
    private fun uploadImage() {
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("Notices/$fileName")
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
        val db = Firebase.firestore.collection("Notices")
        val key = db.document().id

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yy")
        val date:String= formatter.format(time)

        val formatter1= SimpleDateFormat("hh:mm a")
        val time1 :String = formatter1.format(time)

        if(list.size<1){
            listImages.add("")
        }

            val data = AddNoticeModel(
                key,
                date,
                time1,
                binding.NoticeTitle.text.toString(),
                binding.NoticeDesc.text.toString(),
                listImages,
                FieldValue.serverTimestamp()
            )
            db.document(key).set(data).addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(requireContext(),"Notice Uploaded", Toast.LENGTH_SHORT).show()
                binding.NoticeTitle.text=null
                binding.NoticeDesc.text=null
                i=0
                list.clear()
                listImages.clear()
                adapter.notifyDataSetChanged()



            }
                .addOnFailureListener {
                    dialog.dismiss()
                    Toast.makeText(requireContext(),"Something Went Wrong", Toast.LENGTH_SHORT).show()
                }



    }

}