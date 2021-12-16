package com.example.osrscritic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osrscritic.databinding.PostRowItemBinding
import com.example.osrscritic.model.Skillvalue
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class PostsAdapter: RecyclerView.Adapter<PostsViewHolder>() {

    private val postsMutableList : MutableList<CollectionReference> = mutableListOf()

    fun setPostsList(statsList: List<CollectionReference>) {
        postsMutableList.addAll(statsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {

        val binding = PostRowItemBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)

        return PostsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val post = postsMutableList[position]



    }

    override fun getItemCount() = postsMutableList.size
}

class PostsViewHolder(val binding: PostRowItemBinding) : RecyclerView.ViewHolder(binding.root)