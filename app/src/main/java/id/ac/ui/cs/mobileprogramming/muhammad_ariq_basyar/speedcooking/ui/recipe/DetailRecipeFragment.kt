package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.DetailRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.ElapsedTime
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.stopwatch.StopWatchFragment
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModels
import kotlinx.android.synthetic.main.detail_recipe_fragment.*


@AndroidEntryPoint
class DetailRecipeFragment: Fragment() {

    private val detailRecipeViewModels: DetailRecipeViewModels by activityViewModels()
    private lateinit var mContext: Context
    private lateinit var binding : DetailRecipeFragmentBinding

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
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
        binding.viewModel = detailRecipeViewModels
        binding.addNewRecordButton.setOnClickListener {
            fragmentManager!!.commit {
                replace(R.id.recipe_container, StopWatchFragment())
                addToBackStack(null)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailRecipeViewModels.recipe().observe(viewLifecycleOwner) { recipe ->
            recipe_name.text = recipe.name
            recipe_procedure.text = recipe.procedure
            Glide.with(this)
                .load(Drawable.createFromPath(recipe.imageUri.toString()))
                .placeholder(R.drawable.ic_menu_gallery)
                .into(selected_image_view)
        }

        detailRecipeViewModels.ingredients().observe(viewLifecycleOwner) { ingredientList ->
            val ingredientsArrayAdapter = ArrayAdapter(
                context!!,
                android.R.layout.simple_list_item_1,
                ingredientList.map { ingredient ->
                    ingredient.recipeIngredient
                }
            )

            ingredients_list_view.adapter = ingredientsArrayAdapter
        }

        detailRecipeViewModels.durations().observe(viewLifecycleOwner) { durationList ->
            durationList.forEachIndexed { index, duration ->
                val mDuration = duration.recipeDuration
                val parsedElapsedTime = ElapsedTime.parseToString(mDuration)
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

}