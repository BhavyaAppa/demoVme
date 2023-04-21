package com.base.hilt.ui.shopping

import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentAddShoppingItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddShoppingItemFragment :
    FragmentBase<ShoppingViewModel, FragmentAddShoppingItemBinding>() {

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(
            isVisible = true,
            title = getString(R.string.title_add_shopping),
            isBottomNavVisible = true
        ))
    }

    override fun getViewModelClass(): Class<ShoppingViewModel> =
        ShoppingViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_add_shopping_item
    }


    override fun initializeScreenVariables() {
        dataBinding.ivShoppingImg.setOnClickListener {
            findNavController().navigate(
                R.id.action_addShoppingItemFragment_to_imagePickFragment
            )
        }
        dataBinding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                dataBinding.etShoppingItemName.text.toString(),
                dataBinding.etShoppingItemAmount.text.toString(),
                dataBinding.etShoppingItemPrice.text.toString()
            )
            findNavController().popBackStack()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }
}