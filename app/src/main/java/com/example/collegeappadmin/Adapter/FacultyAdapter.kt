package com.example.collegeappadmin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent

import android.view.LayoutInflater

import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeappadmin.R
import com.example.collegeappadmin.activity.UpdateFacultyActivity
import com.example.collegeappadmin.databinding.FacultyExtraOptionsLayoutBinding

import com.example.collegeappadmin.databinding.FacultyItemLayoutBinding
import com.example.collegeappadmin.model.AddFacultyModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FacultyAdapter(val context:Context,val list:ArrayList<AddFacultyModel>)
    :RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder>(){

    inner class FacultyViewHolder(val binding : FacultyItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyViewHolder {
        return FacultyViewHolder(FacultyItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: FacultyViewHolder, position: Int) {


        holder.binding.facultyName.text=list[position].name
        holder.binding.facultyPost.text=list[position].post
        holder.binding.facultyEmail.text=list[position].email
        holder.binding.facultyMobileNo.text=list[position].mobileNo
        Glide.with(context).load(list[position].image).into(holder.binding.facultyImage)



        holder.itemView.setOnClickListener {

            val view = LayoutInflater.from(context).inflate(R.layout.faculty_extra_options_layout,null)
            val binding:FacultyExtraOptionsLayoutBinding= FacultyExtraOptionsLayoutBinding.bind(view)

            val dialog  = AlertDialog.Builder(context)
                .setTitle("Options")
                .setView(binding.root)
                .create()

            dialog.show()

            binding.update.setOnClickListener {
                val intent = Intent(context,UpdateFacultyActivity::class.java)
                intent.putExtra("email",list[position].email)
                context.startActivity(intent)
                dialog.dismiss()
            }
            binding.cancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.remove.setOnClickListener {
                Firebase.firestore.collection("Faculty")
                .document(list[position].email.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context,"Faculty removed successfully",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    .addOnFailureListener { Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()}
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}