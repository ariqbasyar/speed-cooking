package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.DetailRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.NewRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModels

@AndroidEntryPoint
class DetailRecipeFragment: Fragment() {

    private val detailRecipeViewModels: DetailRecipeViewModels by lazy {
        ViewModelProviders.of(this).get(DetailRecipeViewModels::class.java)
    }

    private lateinit var binding : DetailRecipeFragmentBinding
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailRecipeViewModels.getRecipe().observe(viewLifecycleOwner) {
            Log.d("ASDASD", "onActivityCreated: ${it.name}")
            Log.d("ASDASD", "onActivityCreated: ${it.procedure}")
            Log.d("ASDASD", "onActivityCreated: ${it.imageUri}")
        }
    }

}