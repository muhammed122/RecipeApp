package com.example.recipeapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.data.model.RecipeItem
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class Home : Fragment(), ItemClickListener {


    companion object {
        fun newInstance() = Home()
    }

    private lateinit var viewModel: HomeViewModel

    //recipes list
    lateinit var recipesList: List<RecipeItem>

    //recycler component
    lateinit var layoutManager: GridLayoutManager
    lateinit var recipeAdapter: RecipeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)



        intiRecyclerView()



        search_recipe_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        //show progress dialog
        progress_bar.visibility = View.VISIBLE

        viewModel = ViewModelProviders.of(requireActivity()).get(HomeViewModel::class.java)
        viewModel.getAllRecipes()
        viewModel.getRecipeLiveData().observe(viewLifecycleOwner, Observer { recipes ->

            recipes.sortedBy { recipeItem ->
                recipeItem.carbos
            }

            progress_bar.visibility = View.GONE
            connection_layout.visibility = View.GONE

            recipesList = recipes
            recipeAdapter.setResult(recipes)


        })

        viewModel.messageLiveData().observe(viewLifecycleOwner, Observer { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            progress_bar.visibility = View.GONE
            connection_layout.visibility = View.VISIBLE

        })

        //try catch data again when error connection has occurred
        txt_retry.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            viewModel.getAllRecipes()
        }
    }


    private fun intiRecyclerView() {
        recipeAdapter = RecipeAdapter(this)
        layoutManager = GridLayoutManager(context, 3)
        recipes_recycler.layoutManager = layoutManager
        recipes_recycler.setHasFixedSize(true)
        recipes_recycler.adapter = recipeAdapter
    }

    private fun filter(text: String) {
        val filteredList: ArrayList<RecipeItem> = ArrayList()
        for (item in recipesList) {
            if (item.name!!.toLowerCase().contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }
        recipeAdapter.setResult(filteredList)
    }

    override fun onItemClickListener(recipeItem: RecipeItem) {

        val bundle = Bundle()
        bundle.putSerializable("recipe", recipeItem)
        findNavController().navigate(R.id.action_home_to_recipeDetails, bundle)
    }

}