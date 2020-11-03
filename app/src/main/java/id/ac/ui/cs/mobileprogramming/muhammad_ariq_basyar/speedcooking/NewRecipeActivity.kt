package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewRecipeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_recipe_activity)
        supportFragmentManager.commit {
            add(R.id.new_recipe_container, NewRecipeFragment())
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}