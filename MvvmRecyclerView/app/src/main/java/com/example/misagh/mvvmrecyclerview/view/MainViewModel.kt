package com.example.misagh.mvvmrecyclerview.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.misagh.mvvmrecyclerview.data.Item
import com.example.misagh.mvvmrecyclerview.data.ItemRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(itemRepository: ItemRepository) : ViewModel() {

    private val _items = MutableStateFlow(listOf<Item>())
    val items: StateFlow<List<Item>> = _items

    private val _updatingItem = MutableSharedFlow<Item>()
    val updatingItem: Flow<Item> = _updatingItem

    init {
        viewModelScope.launch {
            val items = itemRepository.getItems()
            _items.value = items
        }
    }

    fun toggle(item: Item) {

        val currentItems = mutableListOf<Item>()
        currentItems.addAll(items.value)
        val index = currentItems.indexOfFirst { it.id == item.id }
        if (index == -1) return

        val updatedItem = item.copy(toggle = !item.toggle)
        currentItems[index] = updatedItem
        _items.update { currentItems }
    }

    fun toggleSingleItem(item: Item) {

        item.toggle = !item.toggle
        viewModelScope.launch {
            _updatingItem.emit(item)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val itemRepository: ItemRepository) :
    ViewModelProvider.NewInstanceFactory
        () {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(itemRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}