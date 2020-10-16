package com.example.recipeapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.model.RecipeItem
import com.example.recipeapp.data.repository.RecipeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    private val recipeRepository: RecipeRepository = RecipeRepository()
    private val recipeLiveData: LiveData<List<RecipeItem>>
    private val message: LiveData<String>

    lateinit var job: Job

    init {

        recipeLiveData = recipeRepository.getRecipeLiveData()
        message = recipeRepository.messageLiveData()
    }

    fun getAllRecipes() {

        job = Job()
        viewModelScope.launch(job) {
            recipeRepository.getAllRecipes()
        }
    }

    fun getRecipeLiveData(): LiveData<List<RecipeItem>> {

        return recipeLiveData;
    }

    fun messageLiveData(): LiveData<String> {
        return message;
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}