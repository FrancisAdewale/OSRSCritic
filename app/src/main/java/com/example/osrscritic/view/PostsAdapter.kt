package com.example.osrscritic.view

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.osrscritic.databinding.PostRowItemBinding
import com.example.osrscritic.model.Skillvalue
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class PostsAdapter: RecyclerView.Adapter<PostsViewHolder>() {

    val postsMutableList : MutableList<DocumentSnapshot> = mutableListOf()

    fun setPostsList(statsList: List<DocumentSnapshot>) {
        postsMutableList.clear()
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
        holder.binding.tvCritic.text = post["critic"].toString()

//        val posts = post["posts"]

        holder.binding.tvActualPost.movementMethod = ScrollingMovementMethod()
        holder.binding.tvActualPost.text = post["posts"].toString()

    }

    override fun getItemCount() = postsMutableList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class PostsViewHolder(val binding: PostRowItemBinding) : RecyclerView.ViewHolder(binding.root)