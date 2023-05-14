package com.zak.storyappsubmission

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zak.storyappsubmission.response.ListStoryItem

class StoryAdapter(private val listStory: ArrayList<ListStoryItem>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivUser : ImageView = itemView.findViewById(R.id.iv_user)
        val tvUser : TextView = itemView.findViewById(R.id.tv_user)
        val tvUpload : TextView = itemView.findViewById(R.id.tv_upload)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
         StoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false))


    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.tvUser.text = listStory[position].name
        holder.tvUpload.text = listStory[position].createdAt
        Glide.with(holder.ivUser)
            .load(listStory[position].photoUrl)
            .into(holder.ivUser)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listStory.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }

}