package com.assessment.mobileengineerassesment.view.imagedetails

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ImageDetailsFragment : BaseFragment<FragmentImageDetailsBinding>() {
    private val args: ImageDetailsFragmentArgs by navArgs()
    private val viewModel: ImageDetailsVM by viewModels()
    private val zoomView = ZoomInOutView(null)
    private var bitmap: Bitmap? = null

    private val askPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isGranted = permissions.entries.all {
                it.value == true
            }

            if (isGranted) {
                downloadImage()
            } else {
                Toast.makeText(requireContext(),
                    getString(R.string.permission_required),
                    Toast.LENGTH_SHORT).show()
            }
        }

    override fun layoutId(): Int = R.layout.fragment_image_details

    override fun getViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView() {
        loadImageUsingGlide()

        bindingView.downloadImageView.setOnClickListener {
            requestPermission()
        }

        bindingView.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        bindingView.shareImageView.setOnClickListener {
            attemptToShareImage()
        }
    }

    private fun loadImageUsingGlide() {
        Glide.with(requireContext())
            .asBitmap()
            .load(args.imageUrls.regular)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean,
                ): Boolean {
                    bindingView.loading.isLoading = false
                    zoomView.setOriginalValue()
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean,
                ): Boolean {
                    bitmap = resource
                    bindingView.loading.isLoading = false
                    return false
                }
            })
            .placeholder(BitmapDrawable(resources, args.bitmapImage))
            .into(bindingView.visibleImageView)
        zoomView.setView(bindingView.visibleImageView)
    }

    private fun requestPermission() {
        val permissionRequest = arrayListOf<String>()

        val minSDK = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
        val isWritePermissionGranted = (ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) || minSDK
        if (!isWritePermissionGranted) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissionRequest.isNotEmpty()) {
            askForWritePermission(permissionRequest)
        } else {
            downloadImage()
        }
    }

    private fun askForWritePermission(permissionRequest: ArrayList<String>) {
        askPermissions.launch(permissionRequest.toTypedArray())
    }

    private fun downloadImage() {
        viewModel.saveImageToGallery(requireContext().contentResolver,
            args.imageId,
            bitmap)
    }

    private fun attemptToShareImage() {
        if (bitmap != null) {
            val bmpUri =
                getBitmapFromDrawable()
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, bmpUri)
                type = "image/*"
            }, resources.getText(R.string.send_to)))
        } else {
            Toast.makeText(requireContext(),
                getString(R.string.image_not_ready),
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun getBitmapFromDrawable(): Uri? {
        var bmpUri: Uri? = null
        try {
            val file =
                File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    "unsplash ${args.imageId}" + ".png")
            val out = FileOutputStream(file)
            bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(requireContext(),
                "com.assessment.mobileengineerassesment",
                file)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }
}
