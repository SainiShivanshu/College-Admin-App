package com.example.collegeappadmin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.GatePassLayoutBinding
import com.example.collegeappadmin.databinding.LocalGatePassBinding
import com.example.collegeappadmin.databinding.OutOfStationGatePassBinding
import com.example.collegeappadmin.model.LocalGatePass
import com.example.collegeappadmin.model.OutOfStation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class OutOfStationAdapter (val context: Context, val list:ArrayList<OutOfStation>)
    : RecyclerView.Adapter<OutOfStationAdapter.OutOfStationViewHolder>(){

    inner class OutOfStationViewHolder(val binding : GatePassLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutOfStationViewHolder {
        return OutOfStationViewHolder(GatePassLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder:OutOfStationViewHolder, position: Int) {
        holder.binding.name.text=list[position].name
        holder.binding.rollNo.text=list[position].rollNo
        holder.binding.status.text=list[position].status

if(list[position].status=="Pending")      holder.binding.indicator.visibility=View.VISIBLE
        holder.itemView.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.out_of_station_gate_pass,null)
            val binding: OutOfStationGatePassBinding = OutOfStationGatePassBinding.bind(view)

            when(list[position].status){
                "Granted"->{     binding.buttons.visibility= View.GONE
                }
                "Ungranted"->{     binding.buttons.visibility= View.GONE
                }

            }


            val dialog  = AlertDialog.Builder(context)
                .setTitle("Description")
                .setView(binding.root)
                .create()
            dialog.setCancelable(true)
            dialog.show()
            binding.name.text=list[position].name
            binding.MobileNo.text=list[position].rollNo
            binding.rollNo.text=list[position].rollNo
            binding.branch.text=list[position].branch
            binding.programme.text=list[position].programme
            binding.date.text=list[position].date
            binding.TimeOut.text=list[position].timeOut
            binding.place.text=list[position].place
            binding.overnightStay.text=list[position].overNightStayInformation
            binding.status.text=list[position].status
            binding.TimeIn.text=list[position].timeIn


            binding.proceedButton.setOnClickListener {

                var map =HashMap<String,Any>()
                map["status"]="Granted"
                Firebase.firestore.collection("Out Of Station Gate Pass")
                    .document(list[position].id!!)
                    .update(map).addOnSuccessListener {
                        Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                        binding.buttons.visibility= View.GONE
                        binding.status.text="Granted"
                        holder.binding.status.text="Granted"
                        list[position].status="Granted"
                        holder.binding.indicator.visibility=View.GONE
                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"something went wrong", Toast.LENGTH_SHORT).show()
                    }


            }

            binding.cancelButton.setOnClickListener {
                var map2 =HashMap<String,Any>()

                map2["status"]="Ungranted"
                Firebase.firestore.collection("Out Of Station Gate Pass")
                    .document(list[position].id!!)
                    .update(map2).addOnSuccessListener {

                        Toast.makeText(context, "Ungranted", Toast.LENGTH_SHORT).show()
                        binding.buttons.visibility= View.GONE
                        binding.status.text="Ungranted"
                        holder.binding.status.text="Ungranted"
                        list[position].status="Ungranted"
                        holder.binding.indicator.visibility=View.GONE
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