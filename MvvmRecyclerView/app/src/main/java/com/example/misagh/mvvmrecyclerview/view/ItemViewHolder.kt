package com.example.misagh.mvvmrecyclerview.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.misagh.mvvmrecyclerview.data.Item
import com.example.misagh.mvvmrecyclerview.databinding.ListItemBinding

class ItemViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item, viewModel: MainViewModel) {

        with(binding) {
            this.item = item
            executePendingBindings()
        }

        binding.toggleButton.setOnClickListener {
            binding.item?.let { item ->
                //viewModel.toggle(item)
                viewModel.toggleSingleItem(item)
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
        ): ItemViewHolder = ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(
                    parent
                        .context
                ), parent, false
            )
        )
    }
}