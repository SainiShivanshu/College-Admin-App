package com.example.collegeappadmin.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.databinding.FacultyItemLayoutBinding
import com.example.collegeappadmin.databinding.NoticeListBinding
import com.example.collegeappadmin.model.AddFacultyModel

import com.example.collegeappadmin.model.AddNoticeModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NoticeListAdapter (val context:Context,val list:ArrayList<AddNoticeModel>)
    :RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder>()
    {

        inner class NoticeListViewHolder(val binding : NoticeListBinding)
            : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeListViewHolder {
            return NoticeListViewHolder(NoticeListBinding.inflate(LayoutInflater.from(context),parent,false))
        }

        override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {

            holder.binding.NoticeDesc.text=list[position].noticeDesc
            holder.binding.NoticeDate.text=list[position].date
            holder.binding.NoticeTime.text=list[position].time
            holder.binding.NoticeHeading.text=list[position].title
            if(list[position].image.size<1 || (list[position].image.size==1 && list[position].image[0]=="")){

            }
            else{
                holder.binding.NoticeImageRecycler.visibility=View.VISIBLE
                holder.binding.NoticeImageRecycler.adapter=NoticeImageAdapter(context,list[position].image)
            }



            holder.binding.deleteNotice.setOnClickListener {
                Firebase.firestore.collection("Notices")
                    .document(list[position].noticeId.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context, "Notice deleted successfully", Toast.LENGTH_SHORT)
                            .show()
                        holder.binding.notice.visibility=View.GONE

                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()

                    }
            }


        }

        override fun getItemCount(): Int {
            return list.size
        }
    }