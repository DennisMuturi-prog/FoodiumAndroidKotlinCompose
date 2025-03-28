package com.example.foodium.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.foodium.R
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.network.OnAddIntakeDetails
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import com.example.foodium.serverSentEvents.SSEEventData
import com.example.foodium.ui.components.AddReview
import com.example.foodium.ui.components.LoadingCircle
import com.example.foodium.ui.components.NewReviews
import com.example.foodium.ui.components.RatingStar

@Composable
fun RecipeInfo(
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel,
    newReviewsViewModel: NewReviewsViewModel,
    isRecipe: Boolean
) {
    val currentRecipeState = recipesViewModel.currentWorldwideRecipeState.observeAsState()
    val addReviewState = recipesViewModel.addReviewState.observeAsState()
    val addRatingState = recipesViewModel.addRatingState.observeAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        when (val result = addReviewState.value) {
            is AddReviewState.Success -> {}
            is AddReviewState.Error -> Text(result.message, color = Color.Red)
            is AddReviewState.Loading -> LoadingCircle()
            null -> {}
        }
        when (val result = addRatingState.value) {
            is AddRatingState.Success -> {}
            is AddRatingState.Error -> Text(result.message, color = Color.Red)
            is AddRatingState.Loading -> LoadingCircle()
            null -> {}
        }


        when (val result = currentRecipeState.value) {
            is CurrentWorldwideRecipeState.Success -> RecipeDetails(
                modifier = modifier,
                recipe = result.currentWorldwideRecipe,
                onSendReview = {
                    recipesViewModel.addReview(
                        reviewText = it,
                        region = "worldwide",
                        recipeId = result.currentWorldwideRecipe.uuid,
                    )
                },
                onSendRating = {
                    recipesViewModel.addRating(
                        ratingNumber = it,
                        region = "worldwide",
                        recipeId = result.currentWorldwideRecipe.uuid
                    )
                },
                newReviewsViewModel = newReviewsViewModel,
                onAddIntake = {
                    recipesViewModel.addRecipeIntake(recipeId = it.recipeId, region = it.region)
                },
                isRecipe = isRecipe,
                onAddFoodIntake = {
                    recipesViewModel.addFoodIntake(it)
                }
            )

            null -> {}
        }
    }


}

@Composable
fun RecipeDetails(
    modifier: Modifier = Modifier,
    recipe: WorldwideRecipe,
    onSendReview: (String) -> Unit,
    onSendRating: (Int) -> Unit,
    newReviewsViewModel: NewReviewsViewModel,
    onAddIntake: (OnAddIntakeDetails) -> Unit,
    onAddFoodIntake: (String) -> Unit,
    isRecipe: Boolean
) {

    Text(text = recipe.recipeName, fontSize = 32.sp)
    Button(onClick = {
        if (isRecipe) {
            onAddIntake(OnAddIntakeDetails(recipeId = recipe.uuid, region = "worldwide"))
        }else{
            onAddFoodIntake(recipe.uuid)
        }
    }) {
        Text("add to my intake")
    }
    if(isRecipe){
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.recipeName
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("rating", fontSize = 25.sp)
            RatingStar(rating = recipe.recipeRating, isIndicator = true)
        }
        Text(text = "Ingredients", fontSize = 25.sp)
        recipe.ingredients.forEach { ingredient ->
            Text(text = ingredient)
        }
        Text(text = "instructions", fontSize = 25.sp)
        recipe.directions.forEach { direction ->
            Text(text = "•$direction")
        }

    }

    Text(text = "nutrients", fontSize = 25.sp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.calories_svgrepo_com),
            contentDescription = "energy",
            modifier = Modifier.size(48.dp)
        )
        Text("Energy:${recipe.energy}kcal")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.grain_gluten_wheat_carbohydrates_svgrepo_com),
            contentDescription = "carbohydrates",
            modifier = Modifier.size(48.dp)
        )
        Text("carbohydrates by summation:${recipe.carbohydrateBySummation}g")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_fiber_48),
            contentDescription = "fiber",
            modifier = Modifier.size(48.dp)
        )
        Text("fiber:${recipe.fiberTotalDietary}g")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_wheat_48),
            contentDescription = "starch",
            modifier = Modifier.size(48.dp)
        )
        Text("starch:${recipe.starch}g")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_proteins_64),
            contentDescription = "protein",
            modifier = Modifier.size(48.dp)
        )
        Text("proteins:${recipe.protein}g")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.protein_fat_svgrepo_com),
            contentDescription = "fats",
            modifier = Modifier.size(48.dp)
        )
        Text("fats:${recipe.protein}g")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.sugar_svgrepo_com),
            contentDescription = "sugar",
            modifier = Modifier.size(48.dp)
        )
        Text("sugar:${recipe.sugarsTotal}")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_biting_a_carrot_48),
            contentDescription = "vitamin A",
            modifier = Modifier.size(48.dp)
        )
        Text("VitaminA:${recipe.vitaminARAE}mcg")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_watermelon_50),
            contentDescription = "vitamin B12",
            modifier = Modifier.size(48.dp)
        )
        Text("vitamin B12:${recipe.vitaminB12}mcg")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_lime_48),
            contentDescription = "vitamin C",
            modifier = Modifier.size(48.dp)
        )
        Text("vitamin C:${recipe.vitaminCTotalAscorbicAcid}mcg")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_cod_48),
            contentDescription = "vitamin D",
            modifier = Modifier.size(48.dp)
        )
        Text("vitamin D:${recipe.vitaminD4}mcg")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_iron_ore_48),
            contentDescription = "iron",
            modifier = Modifier.size(48.dp)
        )
        Text("iron:${recipe.ironFe}mcg")
    }
    if(isRecipe){
        RatingStar(onStarClick = {
            onSendRating(it)
        })
        AddReview(onSend = {
            onSendReview(it)
        })
        NewReviews(
            newReviewsViewModel = newReviewsViewModel,
            recipeId = recipe.uuid,
            region = "worldwide"
        )

    }



}