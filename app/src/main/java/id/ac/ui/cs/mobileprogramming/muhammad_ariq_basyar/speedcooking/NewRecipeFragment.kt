package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.NewRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.NewRecipeViewModels

const val PICK_IMAGE = 100

@AndroidEntryPoint
class NewRecipeFragment: Fragment() {
    private lateinit var binding: NewRecipeFragmentBinding
    private val newRecipeViewModels: NewRecipeViewModels by lazy {
        ViewModelProviders.of(this).get(NewRecipeViewModels::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.new_recipe_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = newRecipeViewModels

        binding.button.setOnClickListener {
            save()
        }
    }

    private fun save() {
        newRecipeViewModels.save()
        val backToMain = Intent(context, MainActivity::class.java)
        startActivity(backToMain)
    }
}