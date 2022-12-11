package com.example.collegeappadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.collegeappadmin.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener {
                if(!it.isSuccessful)
                    return@OnCompleteListener


                val map = hashMapOf<String,Any>()

                map["token"]=it.result
                Firebase.firestore.collection("Admin")
                    .document("token")
                    .set(map).addOnSuccessListener {

                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                    }

            })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.About-> {
                Toast.makeText(this,"About Us",Toast.LENGTH_SHORT).show()
            }
            R.id.logout-> {
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}