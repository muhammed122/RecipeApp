package com.example.recipeapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recipeapp.R
import com.example.recipeapp.data.model.RecipeItem
import kotlinx.android.synthetic.main.fragment_recipe_destails.*
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeDetails : Fragment() {

    private val TAG = "RecipeDetails"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_destails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //get recipe object to show details
        val recipe = (requireArguments().getSerializable("recipe") as RecipeItem?)
        bindDataToView(recipe)

    }

    fun bindDataToView(recipe: RecipeItem?) {

        if (recipe!=null) {
            Glide.with(requireActivity())
                .load(recipe.image)
                .into(recipe_image_deatails)

            recipe_name_details.text = recipe.name
            recipe_headline_details.text = recipe.headline
            recipe_description.text = recipe.description
            calories.text=recipe.calories
            fat.text=recipe.fats

        }
    }


}