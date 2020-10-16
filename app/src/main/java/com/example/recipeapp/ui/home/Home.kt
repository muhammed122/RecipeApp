package com.example.recipeapp.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.data.model.RecipeItem
import com.example.recipeapp.helper.SharedPreferencesHelper
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import kotlin.collections.ArrayList

class Home : Fragment(), ItemClickListener {


    companion object {
        fun newInstance() = Home()
    }

    lateinit var sharedPreferencesHelper: SharedPreferencesHelper

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
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_items, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.calories_sort) {

            val sorted = recipesList.sortedBy {
                it.calories
            }
            recipeAdapter.setResult(sorted)
            sharedPreferencesHelper.saveDataInShared("sort", "calories")

            return true
        } else if (item.itemId == R.id.fats_sort) {
            val sorted = recipesList.sortedBy {
                it.fats
            }
            recipeAdapter.setResult(sorted)

            sharedPreferencesHelper.saveDataInShared("sort", "fats")
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())

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

            val sortValue = sharedPreferencesHelper.getDataFromShared("sort")

            if (sortValue == null) {

                recipesList = recipes
            } else if (sortValue == "calories") {
                recipesList = recipes.sortedBy {
                    it.calories
                }
            } else {
                recipesList = recipes.sortedBy {
                    it.fats
                }
            }



            progress_bar.visibility = View.GONE
            connection_layout.visibility = View.GONE

            recipeAdapter.setResult(recipesList)


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