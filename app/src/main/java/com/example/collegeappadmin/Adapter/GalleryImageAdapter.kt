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
import com.example.collegeappadmin.activity.FullImageActivity
import com.example.collegeappadmin.activity.UpdateFacultyActivity
import com.example.collegeappadmin.databinding.DeleteImageLayoutBinding
import com.example.collegeappadmin.databinding.FacultyExtraOptionsLayoutBinding
import com.example.collegeappadmin.databinding.GalleryImageBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class GalleryImageAdapter(val context: Context, val list: ArrayList<String>)
    : RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder>(){

    inner class GalleryImageViewHolder(val binding: GalleryImageBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageViewHolder {
        return GalleryImageViewHolder(GalleryImageBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: GalleryImageViewHolder, position: Int) {
        Glide.with(context).load(list[position]).into(holder.binding.image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullImageActivity::class.java)
            intent.putExtra("image",list[position])
            context.startActivity(intent)
        }

        holder.itemView.setOnClickListener {

            val view = LayoutInflater.from(context).inflate(R.layout.delete_image_layout,null)
            val binding: DeleteImageLayoutBinding = DeleteImageLayoutBinding.bind(view)

            val dialog  = AlertDialog.Builder(context)
                .setTitle("Delete")
                .setView(binding.root)
                .create()

            dialog.show()

            binding.cancel.setOnClickListener {
                dialog.dismiss()
            }

//            binding.delete.setOnClickListener {
//                Firebase.firestore.collection("Faculty")
//                    .document(list[position].)
//                    .delete()
//                    .addOnSuccessListener {
//                        Toast.makeText(context,"Faculty removed successfully", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()
//                    }
//                    .addOnFailureListener { Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
//                        dialog.dismiss()}
//            }


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}