package com.base.hilt.ui.shopping

import androidx.lifecycle.LiveData
import com.base.hilt.model.local.ShoppingItem
import com.base.hilt.model.response.ImageResponse
import com.base.hilt.utils.Resource

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}