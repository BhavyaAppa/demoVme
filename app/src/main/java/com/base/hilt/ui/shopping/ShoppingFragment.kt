package com.base.hilt.ui.shopping

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentShoppingBinding
import com.base.hilt.utils.CustomDividerItemDecoration
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingFragment :
    FragmentBase<ShoppingViewModel, FragmentShoppingBinding>() {
    lateinit var shoppingItemAdapter: ShoppingItemAdapter

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.tab_shopping),
                isBottomNavVisible = true
            )
        )
    }

    override fun getViewModelClass(): Class<ShoppingViewModel> =
        ShoppingViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_shopping
    }


    override fun initializeScreenVariables() {

        dataBinding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )

        }
        subscribeToObservers()
        setupRecyclerView()
    }


    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModel.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    viewModel.insertShoppingItemIntoDb(item)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.shoppingItems.observe(viewLifecycleOwner) {
            shoppingItemAdapter.shoppingItems = it
        }
        viewModel.totalPrice.observe(viewLifecycleOwner) {
            val price = it ?: 0f
            val priceText = "Total Price: $price€"
            dataBinding.tvShoppingItemPrice.text = priceText
        }
    }

    private fun setupRecyclerView() {
        shoppingItemAdapter = ShoppingItemAdapter(Glide.with(requireContext()))
        val drDivider = ContextCompat.getDrawable(requireContext(), R.drawable.divider)
        val itemDecoration = CustomDividerItemDecoration(
            drDivider,
            0,
            0, true
        )
        dataBinding.rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}
















