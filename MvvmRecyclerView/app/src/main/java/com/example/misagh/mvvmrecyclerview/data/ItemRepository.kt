package com.example.misagh.mvvmrecyclerview.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ItemRepository(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getItems(): List<Item> = withContext(dispatcher) {

        val items = mutableListOf<Item>()

        for (i in 1..30) {
            items.add(Item(i, "Item number $i", false))
        }

        return@withContext items
    }
}