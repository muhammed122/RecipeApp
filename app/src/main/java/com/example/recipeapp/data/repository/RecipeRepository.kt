package com.example.recipeapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipeapp.data.model.RecipeItem
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

class RecipeRepository {

    private val recipeLiveData: MutableLiveData<List<RecipeItem>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()


    private lateinit var job: Job

    private val timeOut: Long = 10000

    fun getRecipeLiveData(): LiveData<List<RecipeItem>> {
        return recipeLiveData;
    }

    fun messageLiveData(): LiveData<String> {
        return message;
    }


    //handle any error occur
    private val handler = CoroutineExceptionHandler { _, exception ->
        message.postValue(exception.localizedMessage)
        print("dolaa ${exception.localizedMessage}")
    }

    suspend fun getAllRecipes() {

        job = Job()
        CoroutineScope(IO + job).launch(handler) {

            val totalTime = measureTimeMillis {
                val time = withTimeoutOrNull(timeOut) {
                    val recipes = ApiManager.apiClient.getAllRecipes()
                   recipeLiveData.postValue(recipes)

                }
                if (time == null)
                    job.cancel("request time out")


                job.invokeOnCompletion {
                    if (it != null)
                        message.postValue(it.localizedMessage)
                }

            }


        }


    }

}