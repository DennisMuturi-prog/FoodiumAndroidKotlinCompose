package com.example.foodium.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.foodium.R
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.ui.components.AddReview
import com.example.foodium.ui.components.RatingStar

@Composable
fun RecipeInfo(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel) {
    val currentRecipeState=recipesViewModel.currentWorldwideRecipeState.observeAsState()
    val addReviewState=recipesViewModel.addReviewState.observeAsState()
    val addRatingState=recipesViewModel.addRatingState.observeAsState()
    when(val result=currentRecipeState.value){
        is CurrentWorldwideRecipeState.Success-> RecipeDetails(modifier=modifier,recipe=result.currentWorldwideRecipe, onSendReview = {
            recipesViewModel.addReview(reviewText=it, region = "worldwide", recipeId = result.currentWorldwideRecipe.uuid)
        }, onSendRating = {
            recipesViewModel.addRating(ratingNumber=it, region = "worldwide", recipeId = result.currentWorldwideRecipe.uuid)
        })
        null->{}
    }
    when(val result=addReviewState.value){
        is AddReviewState.Success-> Text("Success")
        is AddReviewState.Error-> Text(result.message)
        is AddReviewState.Loading-> Text("loading")
        null ->{}
    }
    when(val result=addRatingState.value){
        is AddRatingState.Success-> Text("Success")
        is AddRatingState.Error-> Text(result.message)
        is AddRatingState.Loading-> Text("loading")
        null ->{}
    }
}

@Composable
fun RecipeDetails(modifier: Modifier = Modifier,recipe:WorldwideRecipe,onSendReview:(String)->Unit,onSendRating:(Int)->Unit) {
    Column(
        modifier=modifier.fillMaxSize()
    ) {
        Text(text = recipe.recipeName)
        AsyncImage(
            model=recipe.imageUrl,
            contentDescription = recipe.recipeName
        )
        Row {
            Text("rating")
            RatingStar(rating = recipe.recipeRating)
        }
        Text(text = "Ingredients")
        recipe.ingredients.forEach {ingredient->
            Text(text = ingredient)
        }
        Text(text = "instructions")
        recipe.directions.forEach { direction->
            Text(text = "•$direction")
        }
        Text(text="nutrients")
        Row {
            Image(
                painter = painterResource(R.drawable.calories_svgrepo_com),
                contentDescription = "energy",
                modifier = Modifier.size(48.dp)
            )
            Text("Energy:${recipe.energy}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.grain_gluten_wheat_carbohydrates_svgrepo_com),
                contentDescription = "carbohydrates",
                modifier = Modifier.size(48.dp)
            )
            Text("carbohydrates by summation:${recipe.carbohydrateBySummation}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_fiber_48),
                contentDescription = "fiber",
                modifier = Modifier.size(48.dp)
            )
            Text("fiber:${recipe.fiberTotalDietary}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_wheat_48),
                contentDescription = "starch",
                modifier = Modifier.size(48.dp)
            )
            Text("starch:${recipe.starch}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_proteins_64),
                contentDescription = "protein",
                modifier = Modifier.size(48.dp)
            )
            Text("proteins:${recipe.protein}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.protein_fat_svgrepo_com),
                contentDescription = "fats",
                modifier = Modifier.size(48.dp)
            )
            Text("fats:${recipe.protein}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.sugar_svgrepo_com),
                contentDescription = "sugar",
                modifier = Modifier.size(48.dp)
            )
            Text("sugar:${recipe.sugarsTotal}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_biting_a_carrot_48),
                contentDescription = "vitamin A",
                modifier = Modifier.size(48.dp)
            )
            Text("VitaminA:${recipe.vitaminARAE}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_watermelon_50),
                contentDescription = "vitamin B12",
                modifier = Modifier.size(48.dp)
            )
            Text("vitamin B12:${recipe.vitaminB12}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_lime_48),
                contentDescription = "vitamin C",
                modifier = Modifier.size(48.dp)
            )
            Text("vitamin C:${recipe.vitaminCTotalAscorbicAcid}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_cod_48),
                contentDescription = "vitamin D",
                modifier = Modifier.size(48.dp)
            )
            Text("proteins:${recipe.vitaminD4}")
        }
        Row {
            Image(
                painter = painterResource(R.drawable.icons8_iron_ore_48),
                contentDescription = "iron",
                modifier = Modifier.size(48.dp)
            )
            Text("iron:${recipe.ironFe}")
        }
        RatingStar(onStarClick = {
            onSendRating(it)
        })
        AddReview(onSend={
            onSendReview(it)
        })
    }


}