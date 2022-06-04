package com.assessment.mobileengineerassesment.view.gallery

import android.graphics.Bitmap
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentGalleryParentBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import com.assessment.mobileengineerassesment.view.gallery.adapters.SlidingPageAdapter

class GalleryParentFragment : BaseFragment<FragmentGalleryParentBinding>() {
    private val viewModel: SharedGalleryVM by activityViewModels()

    override fun layoutId(): Int = R.layout.fragment_gallery_parent

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        val pageAdapter = SlidingPageAdapter(this)

        bindingView.galleryPager.apply {
            adapter = pageAdapter
        }

        bindingView.dotsIndicator.setViewPager2(bindingView.galleryPager)

        observeNavigationDestination()
    }

    private fun observeNavigationDestination() {
        viewModel.navigateToDestination.observe(viewLifecycleOwner) {
            if (!it.hasBeenHandled) {
                it.getContentIfNotHandled().let {
                    val imagePair = it?.second as Pair<ImageResponse, Bitmap>
                    val imageUrl = imagePair.first.imageUrls.regular
                    val bitmap = imagePair.second
                    val action =
                        GalleryParentFragmentDirections.actionGalleryFragmentToImageDetailsFragment(
                            bitmap, imageUrl
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }
}
