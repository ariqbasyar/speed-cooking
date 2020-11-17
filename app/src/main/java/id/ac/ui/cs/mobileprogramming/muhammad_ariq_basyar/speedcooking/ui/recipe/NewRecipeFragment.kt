package id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.ui.recipe

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.MainActivity
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.R
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.databinding.NewRecipeFragmentBinding
import id.ac.ui.cs.mobileprogramming.muhammad_ariq_basyar.speedcooking.viewmodels.NewRecipeViewModel
import kotlinx.android.synthetic.main.new_recipe_fragment.*


const val PICK_IMAGE = 100
const val PERMISSION_CODE = 101

@AndroidEntryPoint
class NewRecipeFragment: Fragment() {

    private lateinit var binding: NewRecipeFragmentBinding
    private lateinit var ingredientList: LinearLayout
    private val newRecipeViewModel: NewRecipeViewModel by lazy {
        ViewModelProviders.of(this).get(NewRecipeViewModel::class.java)
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
        ingredientList = binding.addIngredientLinearLayout
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = newRecipeViewModel

        binding.saveRecipeButton.setOnClickListener {
            save()
        }
        binding.addIngredientButton.setOnClickListener {
            addIngredient()
        }

        binding.uploadPictureButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (PermissionChecker.checkSelfPermission(
                        context!!,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PermissionChecker.PERMISSION_DENIED) {
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    pickImage()
                }
            } else {
                pickImage()
            }
        }

        newRecipeViewModel.imageUri.observe(viewLifecycleOwner) { imageUri ->
            selected_image_view.setImageURI(imageUri)
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PermissionChecker.PERMISSION_GRANTED
                ) {
                    pickImage()
                } else {
                    Toast.makeText(context, "Permission denied.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            when (requestCode) {
                PICK_IMAGE -> {
                    val mData = data?.data
                    newRecipeViewModel.setImageUri(mData)
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun addIngredient() {
        val ingredientInput: View =
            layoutInflater.inflate(R.layout.new_ingredient_input_text, null, false)
        ingredientList.addView(ingredientInput)
    }

    private fun saveIngredientsEditText() {
        ingredientList.children.forEach {
            val editText: EditText = it.findViewById(R.id.ingredient_edit_text)
            newRecipeViewModel.addIngredient(editText.text.toString())
        }
    }

    private fun save() {
        saveIngredientsEditText()
        newRecipeViewModel.save()
        val backToMain = Intent(context, MainActivity::class.java)
        startActivity(backToMain)
    }
}