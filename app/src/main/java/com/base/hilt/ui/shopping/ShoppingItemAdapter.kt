package com.base.hilt.ui.shopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.base.hilt.R
import com.base.hilt.databinding.ItemShoppingBinding
import com.base.hilt.model.local.ShoppingItem
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var shoppingItems: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)



    override fun getItemCount(): Int {
        return shoppingItems.size
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val binding = DataBindingUtil.inflate<ItemShoppingBinding>(
            LayoutInflater.from(parent.context), R.layout.item_shopping, parent, false
        )
        vh = MainViewHolder(binding)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]
        if (holder is MainViewHolder) {
            if (shoppingItem != null) {
                holder.bind(shoppingItem)

            }
        }
    }

    inner class MainViewHolder(var binding: ItemShoppingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingItem: ShoppingItem) {

            binding.tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            binding.tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}€"
            binding.tvShoppingItemPrice.text = priceText
            binding.executePendingBindings()
        }
    }
}