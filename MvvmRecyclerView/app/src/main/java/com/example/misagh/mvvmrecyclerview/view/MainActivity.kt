package com.example.misagh.mvvmrecyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.misagh.mvvmrecyclerview.R
import com.example.misagh.mvvmrecyclerview.data.Item
import com.example.misagh.mvvmrecyclerview.data.ServiceLocator
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(ServiceLocator.provideItemsRepository()) }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = ItemsAdapter(viewModel)
        recyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.collect { items ->
                    adapter.submitList(items)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.updatingItem.collect { item ->
                    updateItem(item)
                }
            }
        }
    }

    private fun updateItem(item: Item) {

        val index = adapter.currentList.indexOfFirst { it.id == item.id }
        if (index == -1) return

        val viewHolder = recyclerView.findViewHolderForAdapterPosition(index) as? ItemViewHolder
        viewHolder?.bind(item, viewModel)
    }
}