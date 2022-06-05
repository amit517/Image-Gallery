package com.assessment.mobileengineerassesment.view.imagedetails

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.assessment.base.view.BaseFragment
import com.assessment.base.viewmodel.BaseViewModel
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.FragmentImageDetailsBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class ImageDetailsFragment : BaseFragment<FragmentImageDetailsBinding>() {
    private val args: ImageDetailsFragmentArgs by navArgs()
    val zoomView = ZoomInOutView(null)

    override fun layoutId(): Int = R.layout.fragment_image_details

    override fun getViewModel(): BaseViewModel? = null

    override fun initOnCreateView() {
        bindingView.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        Glide.with(requireContext()).load(args.imageUrls.regular)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    bindingView.loading.isLoading = false
                    zoomView.setOriginalValue()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    bindingView.loading.isLoading = false
                    return false
                }
            })
            .placeholder(BitmapDrawable(resources, args.bitmapImage))
            .into(bindingView.visibleImageView)
        zoomView.setView(bindingView.visibleImageView)
    }
}
