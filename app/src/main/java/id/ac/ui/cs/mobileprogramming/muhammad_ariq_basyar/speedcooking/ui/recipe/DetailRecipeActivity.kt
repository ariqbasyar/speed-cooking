package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.home.RECIPE_KEY
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.DetailRecipeViewModel

@AndroidEntryPoint
class DetailRecipeActivity: AppCompatActivity() {

    private val detailRecipeViewModel: DetailRecipeViewModel by lazy {
        ViewModelProviders.of(this).get(DetailRecipeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_recipe_activity)

        val recipeId = intent.getLongExtra(RECIPE_KEY, 1)
        detailRecipeViewModel.applyRecipeId(recipeId)

        supportFragmentManager.commit {
            replace(R.id.recipe_container, DetailRecipeFragment())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val index = supportFragmentManager.backStackEntryCount - 1
        if (index < 0) {
            super.onBackPressed()
            return
        }
        val backEntry = supportFragmentManager.getBackStackEntryAt(index)
        if (backEntry.name == "popup") {
            supportFragmentManager.commit {
                replace(R.id.recipe_container, DetailRecipeFragment())
            }
            supportFragmentManager.popBackStack()
            return
        }
        super.onBackPressed()
    }
}
