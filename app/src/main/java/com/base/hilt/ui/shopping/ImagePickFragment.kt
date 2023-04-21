package com.base.hilt.ui.shopping

import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentImagePickBinding
import com.base.hilt.utils.Status
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ImagePickFragment : FragmentBase<ShoppingViewModel, FragmentImagePickBinding>() {
    lateinit var imageAdapter: ImageAdapter

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.title_shopping),
                isBottomNavVisible = true
            )
        )
    }

    override fun getViewModelClass(): Class<ShoppingViewModel> =
        ShoppingViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_image_pick
    }

    override fun initializeScreenVariables() {
        setupRecyclerView()
        subscribeToObservers()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }

        viewModel.searchForImage("a")

    }

    private fun subscribeToObservers() {
        viewModel.images.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        val urls = result.data?.hits?.map { imageResult -> imageResult.previewURL }
                        imageAdapter.images = urls ?: listOf()
                        dataBinding.progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        view?.let { it1 ->
                            Snackbar.make(
                                it1,
                                result.message ?: "An unknown error occurred.",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        dataBinding.progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        dataBinding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        val testUrl = "https://picsum.photos/200"
        imageAdapter = ImageAdapter(Glide.with(this))
//        imageAdapter.images= arrayListOf(testUrl,testUrl,testUrl,testUrl,testUrl)
        dataBinding.rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}