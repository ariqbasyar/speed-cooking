package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.PopUpFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.reason.ReasonWritePermissionActivity
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.utils.ImageUtils.Companion.getBitmapFromCacheDirs
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModel
import java.io.File
import java.io.IOException

class PopUpFragment : Fragment() {

    private val detailRecipeViewModel: DetailRecipeViewModel by activityViewModels()
    private lateinit var binding: PopUpFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.pop_up_fragment,
            container,
            false
        )
        binding.outer.setOnClickListener { onOuterOnClick() }
        binding.back.setOnClickListener { onBackClick() }
        binding.share.setOnClickListener { onShareClick() }
        binding.saveToGallery.setOnClickListener { onSaveImageToGalleryClick() }
        Glide.with(this)
            .load(getLayoutFromCacheDirAsBitmap())
            .placeholder(R.drawable.ic_menu_gallery)
            .into(binding.previewLayout)
        return binding.root
    }

    private fun onOuterOnClick() {
        onBackClick()
    }

    private fun onBackClick() {
        fragmentManager?.popBackStack()
    }

    private fun onShareClick() {
        shareImage()
    }

    private fun onSaveImageToGalleryClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PermissionChecker.checkSelfPermission(
                    context!!,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PermissionChecker.PERMISSION_DENIED) {
                val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permissions, WRITE_EXTERNAL_PERMISSION_CODE)
            } else {
                saveLayoutToGallery()
            }
        } else {
            saveLayoutToGallery()
        }
    }

    private fun shareImage() {
        try {
            val path = File(context?.cacheDir, "images")
            val file = File(path, "layout.jpg")
            val contentUri = FileProvider.getUriForFile(
                context!!,
                activity?.packageName!! + ".fileprovider",
                file
            )
            if (contentUri != null) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                shareIntent.setDataAndType(
                    contentUri,
                    context!!.contentResolver.getType(contentUri)
                )
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
                startActivity(
                    Intent.createChooser(shareIntent, resources.getString(R.string.choose_an_app))
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        fragmentManager?.popBackStack()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            WRITE_EXTERNAL_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                ) {
                    saveLayoutToGallery()
                } else {
                    Toast.makeText(context, "Permission denied.", Toast.LENGTH_LONG).show()
                    val reasonIntent = Intent(context, ReasonWritePermissionActivity::class.java)
                    startActivity(reasonIntent)
                }
            }
        }
    }

    private fun getLayoutFromCacheDirAsBitmap(): Bitmap? {
        val filename = Uri.parse("layout.jpg")
        return context?.let { getBitmapFromCacheDirs("images", filename, it) }
    }

    private fun saveLayoutToGallery() {
        val imageName = detailRecipeViewModel.getRecipeName()
        val bitmap = getLayoutFromCacheDirAsBitmap()
        if (bitmap != null) {
            saveBitmapToGallery(bitmap, imageName)
        }
        Toast.makeText(
            context,
            resources.getString(R.string.toast_image_saved),
            Toast.LENGTH_LONG
        ).show()
        fragmentManager?.popBackStack()
    }

    private fun saveBitmapToGallery(bitmap: Bitmap, imageName: String): Uri {
        val savedImageUri = MediaStore.Images.Media.insertImage(
            activity!!.contentResolver,
            bitmap,
            imageName,
            "Image of $imageName"
        )
        return Uri.parse(savedImageUri)
    }
}