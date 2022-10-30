package com.example.collegeappadmin.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.databinding.ImageItemBinding

class AddNoticeAdapter(val list: ArrayList<Uri>)
    :RecyclerView.Adapter<AddNoticeAdapter.AddNoticeViewHolder>(){

    inner class AddNoticeViewHolder(val binding: ImageItemBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddNoticeViewHolder {
        val binding=ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AddNoticeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddNoticeViewHolder, position: Int) {
        holder.binding.itemImg.setImageURI(list[position])
    }

    override fun getItemCount(): Int {
      return list.size
    }

}