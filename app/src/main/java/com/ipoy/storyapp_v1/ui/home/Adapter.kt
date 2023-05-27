package com.ipoy.storyapp_v1.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ipoy.storyapp_v1.R
import com.ipoy.storyapp_v1.databinding.RvItemBinding
import com.ipoy.storyapp_v1.story.Stories

class Adapter : PagingDataAdapter<Stories, Adapter.MyViewHolder>(DIFF_CALLBACK){

    private lateinit var onItemClickCallback: OnItemClickCallback


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val stories = getItem(position)
        if (stories != null) {
            holder.bind(stories)
        }
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClick(stories!!, holder.binding)
        }
    }

    inner class MyViewHolder(val binding: RvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Stories) {
            Glide.with(binding.root)
                .load(data.photo)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_image_24)
                )
                .centerCrop()
                .into(binding.imgStory)
            binding.tvName.text = data.name
            binding.tvDesc.text = data.description
            binding.tvDate.text = data.date

            binding.tvName.transitionName = data.name
            binding.tvDate.transitionName = data.date
            binding.tvDesc.transitionName = data.description
            binding.imgStory.transitionName = data.photo
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClick(stories : Stories, card: RvItemBinding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Stories>() {
            override fun areItemsTheSame(oldItem: Stories, newItem: Stories): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Stories, newItem: Stories): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}