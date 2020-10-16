package com.example.recipeapp.data.model

import java.io.Serializable

data class RecipeItem(
    val calories: String?=null,
    val carbos: String?=null,
    val country: String?=null,
    val description: String?=null,
    val difficulty: Int?=null,
    val fats: String?=null,
    val headline: String?=null,
    val id: String?=null,
    val image: String?=null,
    val name: String?=null,
    val proteins: String?=null,
    val thumb: String?=null,
    val time: String?=null
) : Serializable