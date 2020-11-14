package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.manager.LocaleManager
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.home.RECIPE_KEY
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModels

@AndroidEntryPoint
class DetailRecipeActivity: AppCompatActivity() {

    private val detailRecipeViewModels: DetailRecipeViewModels by lazy {
        ViewModelProviders.of(this).get(DetailRecipeViewModels::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_recipe_activity)

        val recipeId = intent.getLongExtra(RECIPE_KEY, 1)
        detailRecipeViewModels.applyRecipeId(recipeId)

        supportFragmentManager.commit {
            replace(R.id.recipe_container, DetailRecipeFragment())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun attachBaseContext(base: Context?) {
        val localeManager = LocaleManager(base)
        super.attachBaseContext(base?.let { localeManager.setLocale(it) })
    }
}
