package com.example.recipeapp.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.recipeapp.R
import com.example.recipeapp.data.model.RecipeItem
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeAdapter(private val itemClickListener: ItemClickListener) :

    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var recipesList: List<RecipeItem> = ArrayList<RecipeItem>()


    fun setResult(newData: List<RecipeItem>) {
        this.recipesList = newData
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        if (recipesList == null)
            return 0

        return recipesList!!.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val recipe: RecipeItem = recipesList[position]
        Glide.with(holder.itemView.context)
            .load(recipe.image)
            .into(holder.itemView.recipe_image)

        holder.itemView.recipe_name.text = recipe.name
        holder.itemView.recipe_release_date.text = recipe.time

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(recipe)
        }

    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}