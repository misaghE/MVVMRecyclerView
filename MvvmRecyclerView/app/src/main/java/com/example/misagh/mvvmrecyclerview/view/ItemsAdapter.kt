package com.example.misagh.mvvmrecyclerview.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.misagh.mvvmrecyclerview.data.Item

class ItemsAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Item, ItemViewHolder>(ItemDiffUtils) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.bind(getItem(position), viewModel)
    }
}

object ItemDiffUtils : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean = oldItem == newItem
}