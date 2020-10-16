import com.example.recipeapp.data.model.RecipeItem

import retrofit2.http.GET


interface RecipeService {


    @GET("android-test/recipes.json")
    suspend fun getAllRecipes(): List<RecipeItem>





}