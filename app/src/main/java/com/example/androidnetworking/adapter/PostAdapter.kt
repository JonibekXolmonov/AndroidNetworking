package com.example.androidnetworking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnetworking.MainActivity
import com.example.androidnetworking.R
import com.example.androidnetworking.model.Poster

class PostAdapter(private val posts: ArrayList<Poster>) :
    RecyclerView.Adapter<PostAdapter.PostVH>() {

    lateinit var itemClick: ((Poster) -> Unit)

    inner class PostVH(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind() {
            view.findViewById<TextView>(R.id.tvTitle).text =
                posts[adapterPosition].title.uppercase()
            view.findViewById<TextView>(R.id.tvBody).text = posts[adapterPosition].body
            view.findViewById<LinearLayout>(R.id.llPost).setOnLongClickListener {
                itemClick.invoke(posts[adapterPosition])
                false
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH = PostVH(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_post, parent, false
        )
    )

    override fun onBindViewHolder(holder: PostVH, position: Int) = holder.bind()

    override fun getItemCount(): Int = posts.size

}