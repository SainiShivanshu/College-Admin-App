package com.example.collegeappadmin.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collegeappadmin.activity.FullImageActivity
import com.example.collegeappadmin.databinding.NoticeImageLayoutBinding
import com.example.collegeappadmin.databinding.NoticeListBinding

class NoticeImageAdapter(val context: Context, val list: ArrayList<String>)
    :RecyclerView.Adapter<NoticeImageAdapter.NoticeImageViewHolder>(){

    inner class NoticeImageViewHolder(val binding: NoticeImageLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeImageViewHolder {

        return NoticeImageViewHolder(NoticeImageLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: NoticeImageViewHolder, position: Int) {
        Glide.with(context).load(list[position]).into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
          val intent = Intent(context,FullImageActivity::class.java)
            intent.putExtra("image",list[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return list.size
    }

}