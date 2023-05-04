package com.ipoy.storyapp_v1.ui.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ipoy.storyapp_v1.databinding.ItemStoryUserBinding
import com.ipoy.storyapp_v1.model.ListStoryItem


class StoriesAdapter(private val onClickListener: OnClickListener, diffCallback: DiffUtil.ItemCallback<ListStoryItem>):
    PagingDataAdapter<ListStoryItem, StoriesAdapter.MyViewHolder>(
    diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(ItemStoryUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null) {
            holder.bind(data)
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(data!!)
        }
    }

    class MyViewHolder(private val binding: ItemStoryUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: ListStoryItem){
            Glide.with(itemView)
                .load(listStoryItem.photoUrl)
                .into(binding.imgProfile)
            binding.profileName.text = listStoryItem.name
        }
    }

    object ItemComparator: DiffUtil.ItemCallback<ListStoryItem>(){
        override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class OnClickListener(val clickListener: (listStoryItem: ListStoryItem) -> Unit) {
        fun onClick(listStoryItem: ListStoryItem) = clickListener(listStoryItem)
    }
}
