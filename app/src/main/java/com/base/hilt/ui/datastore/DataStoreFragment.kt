package com.base.hilt.ui.datastore

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentDataStoreBinding
import com.base.hilt.utils.DataStoreUtil
import com.base.hilt.utils.SecurityUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created By Richa Shah on 2/08/2022 at 2:00 PM
 * Data store Usage Example
 */

@AndroidEntryPoint
class DataStoreFragment : FragmentBase<DatastoreViewModel, FragmentDataStoreBinding>(),
    View.OnClickListener {


  /*  @Inject
    lateinit var dataStore: DataStoreUtil*/
  @Inject
  lateinit var dataStore: DataStoreUtil


    override fun getLayoutId(): Int {
        return R.layout.fragment_data_store
    }

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(
            isVisible = true,
            title = "DataStore",
            isBottomNavVisible = true
        ))
    }

    override fun initializeScreenVariables() {
        dataBinding.viewModel = viewModel
        dataBinding.click = this
//        dataStore = DataStoreUtil(requireContext(), SecurityUtil())

    }

    override fun onClick(v: View) {
        when (v.id) {
            dataBinding.btFetchData.id -> {
                lifecycleScope.launch {
                    dataStore.getSecuredData().collect {
                        dataBinding.tvData.text = it
                    }
                }
            }
            dataBinding.btStoreData.id -> {
                lifecycleScope.launch {
                    dataStore.setSecuredData(dataBinding.etData.text.toString())
                }
            }
            dataBinding.btFetchSecuredData.id -> {
                lifecycleScope.launch {
                    // Get our show completed value, defaulting to false if not set:
                    dataStore.getSecuredData().collect{
                        if (it.isNullOrEmpty().not())
                        {
                            dataBinding.tvSecuredData.text = it
                        }
                    }
                }
            }
            dataBinding.btStoreSecuredData.id -> {
                lifecycleScope.launch {
                    dataStore.setSecuredData(dataBinding.etSecuredData.text.toString())
                }
            }
        }
    }

    override fun getViewModelClass(): Class<DatastoreViewModel> =
        DatastoreViewModel::class.java

}