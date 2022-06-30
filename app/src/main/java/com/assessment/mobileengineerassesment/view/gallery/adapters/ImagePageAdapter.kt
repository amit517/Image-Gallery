package com.assessment.mobileengineerassesment.view.gallery.adapters

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assessment.mobileengineerassesment.R
import com.assessment.mobileengineerassesment.databinding.ItemGalleryImageBinding
import com.assessment.mobileengineerassesment.model.ImageResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImagePageAdapter(private val imageClickCallback: (ImageResponse?, Bitmap?) -> (Any?)) :
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
            holder.bindingView.image.invalidate()
            var bitmap: Bitmap? = null
            when (holder.bindingView.image.drawable) {
                is BitmapDrawable -> {
                    val drawable = holder.bindingView.image.drawable as BitmapDrawable
                    bitmap = drawable.bitmap
                }
            }
            imageClickCallback.invoke(getItem(position), bitmap)
        }
        CoroutineScope(Dispatchers.Default).launch {
            holder.itemView.post {
                val item = try {
                    getItem(position)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
                item?.let {
                    val cellWidth = holder.itemView.width
                    val imageLayoutParams: ViewGroup.LayoutParams =
                        holder.bindingView.image.layoutParams
                    imageLayoutParams.width = cellWidth
                    imageLayoutParams.height = item.height * cellWidth / item.width
                    holder.bindingView.image.layoutParams = imageLayoutParams
                }
            }
        }
    }

    inner class ImageResponseViewHolder(val bindingView: ItemGalleryImageBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(imageResponse: ImageResponse?) {
            bindingView.imageResponse = imageResponse
        }
    }
}
