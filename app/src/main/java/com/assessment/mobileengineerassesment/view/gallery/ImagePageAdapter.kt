package com.assessment.mobileengineerassesment.view.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.ItemGalleryImageBinding
import com.assessment.mobileengineerassesment.model.ImageResponse

class ImagePageAdapter(private val imageClickCallback: (ImageResponse?) -> (Any?)) :
    PagingDataAdapter<ImageResponse, ImagePageAdapter.ImageResponseViewHolder>(ImageResponse.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageResponseViewHolder {
        val hotelBinding =
            DataBindingUtil.inflate<ItemGalleryImageBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_gallery_image, parent, false
            )
        return ImageResponseViewHolder(hotelBinding)
    }

    override fun onBindViewHolder(holder: ImageResponseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            imageClickCallback.invoke(getItem(position))
        }
    }

    inner class ImageResponseViewHolder(val bindingView: ItemGalleryImageBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(imageResponse: ImageResponse?) {
            // TODO:
        }
    }
}
