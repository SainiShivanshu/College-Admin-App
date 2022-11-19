package com.example.collegeappadmin.Adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.ComplainBinding
import com.example.collegeappadmin.databinding.GatePassLayoutBinding
import com.example.collegeappadmin.databinding.LocalGatePassBinding
import com.example.collegeappadmin.model.Complain
import com.example.collegeappadmin.model.LocalGatePass
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComplainAdapter(val context: Context, val list:ArrayList<Complain>)
    :RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>(){

    inner class ComplainViewHolder(val binding : GatePassLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplainViewHolder {
        return ComplainViewHolder(GatePassLayoutBinding.inflate(LayoutInflater.from(context),parent,false))

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ComplainViewHolder, position: Int) {
        holder.binding.name.text=list[position].name
        holder.binding.rollNo.text=list[position].rollNo
        holder.binding.status.text=list[position].status

        if(list[position].status=="Pending")      holder.binding.indicator.visibility= View.VISIBLE


        if(list[position].status=="In process"){
            holder.binding.indicator.visibility= View.VISIBLE
            holder.binding.indicator.setCardBackgroundColor(R.color.green)
        }

        holder.itemView.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.complain,null)
            val binding: ComplainBinding = ComplainBinding.bind(view)
            when(list[position].status){
                "In Process"->{
                    binding.proceedButton.text="Completed"
                }
                "Cancelled"->{
                    binding.buttons.visibility= View.GONE
                }
                "Completed"->{
                    binding.buttons.visibility=View.GONE
                }
            }

            val dialog  = AlertDialog.Builder(context)
                .setTitle("Description")
                .setView(binding.root)
                .create()
            dialog.show()
            binding.name.text=list[position].name
            binding.MobileNo.text=list[position].rollNo
            binding.rollNo.text=list[position].rollNo
            binding.category.text=list[position].category
            binding.programme.text=list[position].programme
            binding.status.text=list[position].status
            binding.roomNo.text=list[position].roomNo
            binding.description.text=list[position].description

            binding.proceedButton.setOnClickListener {

                if(list[position].status=="Pending"){
                    var map =HashMap<String,Any>()
                    map["status"]="In process"

                    Firebase.firestore.collection("Complain")
                        .document(list[position].id!!)
                        .update(map).addOnSuccessListener {
                            Toast.makeText(context, "In process", Toast.LENGTH_SHORT).show()
                            binding.status.text="In process"
                            binding.proceedButton.text="Completed"
                            holder.binding.status.text="In process"
                            list[position].status="In process"
                            holder.binding.indicator.setCardBackgroundColor(R.color.green)
                        }

                        .addOnFailureListener {
                            Toast.makeText(context,"something went wrong", Toast.LENGTH_SHORT).show()
                        }
                }

                if(list[position].status=="In process"){
                    var map =HashMap<String,Any>()
                    map["status"]="Completed"

                    Firebase.firestore.collection("Complain")
                        .document(list[position].id!!)
                        .update(map).addOnSuccessListener {
                            Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show()
                            binding.status.text="Completed"
                            holder.binding.status.text="Completed"
                            list[position].status="Completed"
                            holder.binding.indicator.visibility= View.GONE
                            binding.buttons.visibility= View.GONE
                        }

                        .addOnFailureListener {
                            Toast.makeText(context,"something went wrong", Toast.LENGTH_SHORT).show()
                        }
                }

            }

            binding.cancelButton.setOnClickListener {
                var map2 =HashMap<String,Any>()

                map2["status"]="Cancelled"
                Firebase.firestore.collection("Complain")
                    .document(list[position].id!!)
                    .update(map2).addOnSuccessListener {

                        Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                        binding.buttons.visibility= View.GONE
                        binding.status.text="Cancelled"
                        holder.binding.status.text="Cancelled"
                        list[position].status="Cancelled"
                        holder.binding.indicator.visibility= View.GONE
                    }

                    .addOnFailureListener {
                        Toast.makeText(context,"something went wrong", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}