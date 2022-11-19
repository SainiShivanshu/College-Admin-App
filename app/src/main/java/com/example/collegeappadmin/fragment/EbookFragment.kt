package com.example.collegeappadmin.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import com.example.collegeappadmin.Adapter.EbookAdapter
import com.example.collegeappadmin.R
import com.example.collegeappadmin.databinding.FragmentEbookBinding
import com.example.collegeappadmin.model.AddEbookModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


class EbookFragment : Fragment() {

private lateinit var binding:FragmentEbookBinding
    private lateinit var temp:ArrayList<AddEbookModel>
    private lateinit var pdf:ArrayList<AddEbookModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEbookBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.title="Ebooks"


        getEbookList()
        setHasOptionsMenu(true)
        temp= ArrayList()

binding.AddEbook.setOnClickListener {
    findNavController().navigate(R.id.action_ebookFragment_to_uploadEbookFragment)
}
        return binding.root
    }


    private fun getEbookList() {
        pdf =ArrayList<AddEbookModel>()

        Firebase.firestore.collection("Ebooks")
            .orderBy("ebookTitle", Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                pdf.clear()
                for (doc in it) {
                    val data = doc.toObject(AddEbookModel::class.java)
                    pdf.add(data)
                }
                temp.addAll(pdf)
                binding.ebookRecycler.adapter= EbookAdapter(requireContext(),temp)
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerView.visibility=View.GONE
            }
            .addOnFailureListener(OnFailureListener {
                Log.d("error",it.toString())
            })



    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search,menu)
        val item = menu!!.findItem(R.id.search_action)

        val searchView=item!!.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                temp.clear()
                val searchText=newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){

                    pdf.forEach {
                        if(it.ebookTitle!!.toLowerCase(Locale.getDefault()).contains(searchText)){
                            temp.add(it)
                        }
                    }

                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()

                }
                else {
                    temp.clear()
                    temp.addAll(pdf)
                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()
                }


                return false
            }


        })
     return   super.onCreateOptionsMenu(menu, inflater)
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//       requireContext(). menuInflater.inflate(R.menu.search,menu)
//        val item = menu!!.findItem(R.id.search_action)
//
//        val searchView=item!!.actionView as SearchView
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                temp.clear()
//                val searchText=newText!!.toLowerCase(Locale.getDefault())
//                if(searchText.isNotEmpty()){
//
//                    pdf.forEach {
//                        if(it.ebookTitle!!.toLowerCase(Locale.getDefault()).contains(searchText)){
//                            temp.add(it)
//                        }
//                    }
//
//                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()
//
//                }
//                else {
//                    temp.clear()
//                    temp.addAll(pdf)
//                    binding.ebookRecycler.adapter!!.notifyDataSetChanged()
//                }
//
//
//                return false
//            }
//
//
//        })
//        return super.onCreateOptionsMenu(menu)
//    }


    override fun onPause() {

        binding.shimmerViewContainer.stopShimmer()
        super.onPause()
    }

    override fun onResume() {
        binding.shimmerViewContainer.startShimmer()
        super.onResume()
    }


}