package com.example.recipeapp.ui.home

import com.example.recipeapp.data.model.RecipeItem

interface ItemClickListener {

    fun onItemClickListener(recipeItem: RecipeItem)
}