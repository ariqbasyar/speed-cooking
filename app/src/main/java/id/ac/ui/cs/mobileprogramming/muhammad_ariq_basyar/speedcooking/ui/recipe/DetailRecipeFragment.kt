package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.DetailRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.StopWatchFragment
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.parser.Parser
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.utils.ImageUtils.Companion.saveToInternalStorage
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModel
import kotlinx.android.synthetic.main.detail_recipe_fragment.*
import java.io.File

const val WRITE_EXTERNAL_PERMISSION_CODE = 102

@AndroidEntryPoint
class DetailRecipeFragment: Fragment() {

    private val detailRecipeViewModel: DetailRecipeViewModel by activityViewModels()
    private lateinit var binding : DetailRecipeFragmentBinding
    private lateinit var downloadAbleFrameLayout: FrameLayout

    init {
        System.loadLibrary("converter-lib")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.detail_recipe_fragment,
            container,
            false
        )
        downloadAbleFrameLayout = binding.downloadableLayout
        binding.addNewRecordButton.setOnClickListener {
            fragmentManager!!.commit {
                replace(R.id.recipe_container, StopWatchFragment())
                addToBackStack(null)
            }
        }

        binding.shareButton.setOnClickListener {
            fragmentManager!!.commit {
                add(R.id.recipe_container, PopUpFragment(), "popup")
                addToBackStack("popup")
            }
            saveLayoutToInternal()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailRecipeViewModel.recipe().observe(viewLifecycleOwner) { recipe ->
            recipe_name.text = recipe.name
            recipe_procedure.text = recipe.procedure
            Glide.with(this)
                .load(Drawable.createFromPath(recipe.imageUri.toString()))
                .placeholder(R.drawable.ic_menu_gallery)
                .into(selected_image_view)
        }

        detailRecipeViewModel.ingredients().observe(viewLifecycleOwner) { ingredientList ->
            val ingredientsArrayAdapter = ArrayAdapter(
                context!!,
                android.R.layout.simple_list_item_1,
                ingredientList.map { ingredient ->
                    ingredient.recipeIngredient
                }
            )
            ingredients_list_view.adapter = ingredientsArrayAdapter
        }

        detailRecipeViewModel.durations().observe(viewLifecycleOwner) { durationList ->
            durationList.forEachIndexed { index, duration ->
                val mDuration = duration.recipeDuration
                val parsedElapsedTime = Parser.parseToString(mDuration)
                when(index) {
                    0 -> {
                        binding.bestRecordText.text = parsedElapsedTime
                    }
                    1 -> {
                        binding.secondFastest.text = parsedElapsedTime
                    }
                    2 -> {
                        binding.thirdFastest.text = parsedElapsedTime
                    }
                }
            }
        }
    }

    private fun saveLayoutToInternal() {
        downloadAbleFrameLayout.isDrawingCacheEnabled = true
        downloadAbleFrameLayout.buildDrawingCache()
        val bitmap: Bitmap = downloadAbleFrameLayout.getDrawingCache(true)
        val filePath = File(context?.cacheDir, "images")
        filePath.mkdirs()
        saveToInternalStorage(bitmap, filePath, "layout.jpg")
        detailRecipeViewModel.setRecipeName(binding.recipeName.text.toString())
    }
}