package com.example.collegeappadmin.Adapter

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.DeleteEbookBinding
import com.example.collegeappadmin.databinding.PdfListBinding
import com.example.collegeappadmin.model.AddEbookModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EbookAdapter (val context: Context, val list: ArrayList<AddEbookModel>)
    : RecyclerView.Adapter<EbookAdapter.EbookViewHolder>(){
    inner class EbookViewHolder(val binding: PdfListBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EbookViewHolder {
        return EbookViewHolder(PdfListBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: EbookViewHolder, position: Int) {

        holder.binding.ebookTitle.text=list[position].ebookTitle

        holder.binding.ebookDownload.setOnClickListener {

            val request = DownloadManager.Request(Uri.parse(list[position].ebookUrl))
                .setTitle(list[position].ebookTitle)
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)

            val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)

        }

        holder.itemView.setOnClickListener {

            val view = LayoutInflater.from(context).inflate(R.layout.delete_ebook,null)
            val binding: DeleteEbookBinding= DeleteEbookBinding.bind(view)

            val dialog  = AlertDialog.Builder(context)
                .setTitle("Options")
                .setView(binding.root)
                .create()

            dialog.show()


            binding.cancel.setOnClickListener {
                dialog.dismiss()
            }
            binding.delete.setOnClickListener {
                Firebase.firestore.collection("Ebooks")
                    .document(list[position].ebookId.toString())
                    .delete()
                    .addOnSuccessListener {
                        Toast.makeText(context,"Ebook Deleted", Toast.LENGTH_SHORT).show()

                        dialog.dismiss()
                    }
                    .addOnFailureListener { Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()}
            }
        }
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context,PdfViewerActivity::class.java)
//            intent.putExtra("title",list[position].ebookTitle)
//            intent.putExtra("url",list[position].ebookUrl)
//            context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}