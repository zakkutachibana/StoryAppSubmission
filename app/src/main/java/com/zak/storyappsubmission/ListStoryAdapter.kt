package com.zak.storyappsubmission

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zak.storyappsubmission.databinding.ItemStoryBinding
import com.zak.storyappsubmission.response.ListStoryItem
import com.zak.storyappsubmission.ui.detail.DetailActivity

class ListStoryAdapter :
    PagingDataAdapter<ListStoryItem, ListStoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.tvUser.text = data.name
            binding.tvUpload.text = data.createdAt.withDateFormat()
            Glide.with(binding.ivUser).load(data.photoUrl).into(binding.ivUser)
            binding.cardView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_NAME, data.name)
                intent.putExtra(DetailActivity.EXTRA_DESC, data.description)
                intent.putExtra(DetailActivity.EXTRA_IMG, data.photoUrl)
                intent.putExtra(DetailActivity.EXTRA_UPLOAD, data.createdAt)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}