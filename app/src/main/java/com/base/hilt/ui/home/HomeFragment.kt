package com.base.hilt.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.bind.GenericRecyclerViewAdapter
import com.base.hilt.databinding.FragmentHomeBinding
import com.base.hilt.databinding.RowItemHomeListBinding
import com.base.hilt.model.response.Movie
import com.base.hilt.model.response.MovieListResponse
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : FragmentBase<HomeViewModel, FragmentHomeBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = getString(R.string.title_home),
                isBottomNavVisible = true
            )
        )
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    private var adapter: GenericRecyclerViewAdapter<Movie, RowItemHomeListBinding>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.callHomeScreenAPI()
    }

    override fun initializeScreenVariables() {
        observeData()
    }

    private fun observeData() {
        viewModel.responseLiveHomeVendorListResponse.observe(this, Observer {
            if (it == null) {
                return@Observer
            }
            when (it) {
                is ResponseHandler.Loading -> {
                    viewModel.showProgressBar(false)
                }
                is ResponseHandler.OnFailed -> {
                    viewModel.showProgressBar(false)
                    httpFailedHandler(it.code, it.message, it.messageCode)
                }
                is ResponseHandler.OnSuccessResponse<ResponseData<MovieListResponse>?> -> {
                    viewModel.showProgressBar(false)
                    when {
                        it.response?.data?.movieList?.isNotEmpty() == true -> {
                            setUpData(it.response.data?.movieList!!)
                        }
                        else -> {
                            dataBinding.rvMovieList.visibility = View.GONE
                        }
                    }

                }
            }
        })
    }

    private fun setUpData(mList: ArrayList<Movie>) {
        dataBinding.rvMovieList.visibility = View.VISIBLE
        adapter = object :
            GenericRecyclerViewAdapter<Movie, RowItemHomeListBinding>(
                requireContext(),
                mList
            ) {
            override val layoutResId: Int
                get() = R.layout.row_item_home_list

            override fun onBindData(
                model: Movie,
                position: Int,
                dataBinding: RowItemHomeListBinding
            ) {

                dataBinding.model = model
                Glide.with(requireContext())
                    .load(model.imageUrl)
                    .into(dataBinding.imageview)

                dataBinding.executePendingBindings()

            }

            override fun onItemClick(model: Movie, position: Int) {

            }

        }


        ViewCompat.setNestedScrollingEnabled(dataBinding.rvMovieList, false)
        dataBinding.rvMovieList.let {
            it.adapter = adapter
            it.itemAnimator = DefaultItemAnimator()

        }
    }
}