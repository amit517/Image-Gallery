package com.assessment.mobileengineerassesment.view.gallery

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {
    private val imageClickCallback = { imageresponse: ImageResponse? ->

    }
    private lateinit var imagePageAdapter: ImagePageAdapter

    private val viewModel: GalleryFragmentVM by viewModels()

    override fun layoutId(): Int = R.layout.fragment_gallery

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        imagePageAdapter = ImagePageAdapter(imageClickCallback)
        bindingView.imageRecyclerViewView.adapter = imagePageAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.imageDataList.collectLatest {
                imagePageAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                Log.d("TAG", "initOnCreateView: " + it.toString())
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            imagePageAdapter.loadStateFlow.collect {
                Log.d("TAG", "initOnCreateView: ${it.toString()}")
            }
        }
    }
}
