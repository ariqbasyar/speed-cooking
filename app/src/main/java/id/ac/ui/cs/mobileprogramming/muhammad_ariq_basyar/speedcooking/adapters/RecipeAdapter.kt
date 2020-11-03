package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.data.recipe.Recipe
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.RecipeItemBinding

class RecipeAdapter(private val mListener: RecipeClickListener) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    var recipeList: List<Recipe>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.bind(recipeList?.get(position), mListener)

    override fun getItemCount(): Int {
        //If list is null, return 0, otherwise return the size of the list
        return recipeList?.size ?: 0
    }

    class RecipeViewHolder(private val binding: RecipeItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(recipe: Recipe?, clickListener : RecipeClickListener){
            binding.recipe = recipe
            binding.recipeItemClick = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RecipeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil
                    .inflate<RecipeItemBinding>(layoutInflater, R.layout.recipe_item,
                        parent, false)
                return RecipeViewHolder(binding)
            }
        }
    }

    interface RecipeClickListener {
        fun onRecipeClicked(recipe: Recipe)
    }

}
