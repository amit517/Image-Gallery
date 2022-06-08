package com.assessment.mobileengineerassesment.view.imagedetails

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.assessment.base.utils.BlurHashDecoder
import com.assessment.base.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*

class ImageDetailsVM : BaseViewModel() {

    fun saveImageToGallery(contentResolver: ContentResolver, imageName: String, bitmap: Bitmap?) {
        val imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$imageName.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            bitmap?.let {
                put(MediaStore.Images.Media.WIDTH, bitmap.width)
                put(MediaStore.Images.Media.HEIGHT, bitmap.height)
            }
        }

        imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator.toString() + "Unsplash")
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        try {
            val uri = contentResolver.insert(imageUri, contentValues)
            val fos = uri?.let { contentResolver.openOutputStream(it) }
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            Objects.requireNonNull(fos)
            emitMessage("Image saved to gallery")
        } catch (e: Exception) {
            emitMessage("Image not saved")
        }
    }

    suspend fun get(blurHash: String, width: Int, height: Int): Bitmap? =
        withContext(Dispatchers.IO) {
            val bitmap = BlurHashDecoder.decode("LJCGPn-;0L9FxutSa#ROo}%Mf8IA", width, height)
            bitmap
        }
}

