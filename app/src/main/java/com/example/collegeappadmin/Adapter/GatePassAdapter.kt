package com.example.collegeappadmin.Adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FacultyExtraOptionsLayoutBinding
import com.example.collegeappadmin.databinding.GatePassLayoutBinding
import com.example.collegeappadmin.databinding.LocalGatePassBinding
import com.example.collegeappadmin.model.LocalGatePass
import com.example.collegeappadmin.model.UserModel
import com.example.collegeappadmin.notification.NotificationData
import com.example.collegeappadmin.notification.PushNotification
import com.example.collegeappadmin.notification.api.ApiUtilities
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent
import retrofit2.Call
import retrofit2.Callback

class GatePassAdapter(val context: Context, val list:ArrayList<LocalGatePass>)
    :RecyclerView.Adapter<GatePassAdapter.GatePassViewHolder>(){

    inner class GatePassViewHolder(val binding : GatePassLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GatePassViewHolder {
        return GatePassViewHolder(GatePassLayoutBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: GatePassViewHolder, position: Int) {
       holder.binding.name.text=list[position].name
        holder.binding.rollNo.text=list[position].rollNo
        holder.binding.status.text=list[position].status

        if(list[position].status=="Pending")      holder.binding.indicator.visibility= View.VISIBLE

        holder.itemView.setOnClickListener {
                    val view = LayoutInflater.from(context).inflate(R.layout.local_gate_pass,null)
        val binding: LocalGatePassBinding = LocalGatePassBinding.bind(view)
            when(list[position].status){
                "Granted"->{     binding.buttons.visibility= GONE}
                "Ungranted"->{     binding.buttons.visibility= GONE}
            }


        val dialog  = AlertDialog.Builder(context)
            .setTitle("Description")
            .setView(binding.root)
            .create()
        dialog.show()
       binding.name.text=list[position].name
            binding.email.text=list[position].emailId
        binding.MobileNo.text=list[position].rollNo
        binding.rollNo.text=list[position].rollNo
       binding.branch.text=list[position].branch
        binding.programme.text=list[position].programme
          binding.date.text=list[position].date
        binding.TimeOut.text=list[position].timeOut
        binding.place.text=list[position].place
        binding.status.text=list[position].status
        binding.TimeIn.text=list[position].timeIn

            binding.proceedButton.setOnClickListener {

                var map =HashMap<String,Any>()
                map["status"]="Granted"
                Firebase.firestore.collection("Local Gate Pass")
                    .document(list[position].id!!)
                    .update(map).addOnSuccessListener {


                        Toast.makeText(context, "Granted", Toast.LENGTH_SHORT).show()
                        binding.buttons.visibility= GONE
                        binding.status.text="Granted"
                        holder.binding.status.text="Granted"
                        list[position].status="Granted"
                        holder.binding.indicator.visibility=GONE

                        sendNotification(list[position].id,list[position].emailId)

                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show()
                    }


            }

            binding.cancelButton.setOnClickListener {
                var map2 =HashMap<String,Any>()

                map2["status"]="Ungranted"
                Firebase.firestore.collection("Local Gate Pass")
                    .document(list[position].id!!)
                    .update(map2).addOnSuccessListener {

                        Toast.makeText(context, "Ungranted", Toast.LENGTH_SHORT).show()
                        binding.buttons.visibility= GONE
                        binding.status.text="Ungranted"
                        holder.binding.status.text="Ungranted"
                        list[position].status="Ungranted"
                        holder.binding.indicator.visibility=GONE
                        sendNotification1(list[position].id,list[position].emailId)

                    }
                    .addOnFailureListener {
                        Toast.makeText(context,"something went wrong",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun sendNotification1(id: String?, emailId: String) {
        Firebase.firestore.collection("Users").document(emailId)
            .get().addOnSuccessListener {
                val   data=it.toObject(UserModel::class.java)


                val notificationData= PushNotification(
                    NotificationData("Local Gate Pass UnGranted", id),
                    data!!.token.toString()
                )

                ApiUtilities.getInstance().sendNotification(
                    notificationData
                ).enqueue(object : Callback<PushNotification> {
                    override fun onResponse(
                        call: Call<PushNotification>,
                        response: retrofit2.Response<PushNotification>
                    ) {
                        Toast.makeText(context,data.token.toString()
                            ,Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<PushNotification>, t: Throwable) {
                        Toast.makeText(context,"Something Went Wrong"
                            ,Toast.LENGTH_SHORT).show()
                    }

                })
            }

    }

    private fun sendNotification(id: String?, emailId: String) {

        Firebase.firestore.collection("Users").document(emailId)
            .get().addOnSuccessListener {
                val   data=it.toObject(UserModel::class.java)


                val notificationData= PushNotification(
                    NotificationData("Local Gate Pass Granted", id),
                   data!!.token.toString()
                )

                ApiUtilities.getInstance().sendNotification(
                    notificationData
                ).enqueue(object : Callback<PushNotification> {
                    override fun onResponse(
                        call: Call<PushNotification>,
                        response: retrofit2.Response<PushNotification>
                    ) {
                        Toast.makeText(context,data.token.toString()
                            ,Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<PushNotification>, t: Throwable) {
                        Toast.makeText(context,"Something Went Wrong"
                            ,Toast.LENGTH_SHORT).show()
                    }

                })
            }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}