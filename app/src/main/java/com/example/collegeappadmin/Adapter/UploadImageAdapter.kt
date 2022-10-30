package com.example.collegeappadmin.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.databinding.ImageItemBinding

class UploadImageAdapter(val list: ArrayList<Uri>)
    :RecyclerView.Adapter<UploadImageAdapter.UploadImageViewHolder>(){

    inner class UploadImageViewHolder(val binding: ImageItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UploadImageViewHolder {
        val binding=ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UploadImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UploadImageViewHolder, position: Int) {
        holder.binding.itemImg.setImageURI(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}