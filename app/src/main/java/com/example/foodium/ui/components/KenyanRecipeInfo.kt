package com.example.foodium.ui.components

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
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.network.OnAddIntakeDetails
import com.example.foodium.serverSentEvents.NewReviewsViewModel
import com.example.foodium.ui.components.AddReview
import com.example.foodium.ui.components.LoadingCircle
import com.example.foodium.ui.components.RatingStar
import com.example.foodium.ui.screens.AddRatingState
import com.example.foodium.ui.screens.AddReviewState
import com.example.foodium.ui.screens.CurrentKenyanRecipeState
import com.example.foodium.ui.screens.CurrentWorldwideRecipeState
import com.example.foodium.ui.screens.RecipesViewModel



@Composable
fun KenyanRecipeInfo(
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel,
    newReviewsViewModel: NewReviewsViewModel
) {
    val currentRecipeState = recipesViewModel.currentKenyanRecipeState.observeAsState()
    val addReviewState = recipesViewModel.addReviewState.observeAsState()
    val addRatingState = recipesViewModel.addRatingState.observeAsState()
    val addRecipeIntakeState = recipesViewModel.addRecipeIntakeState.observeAsState()
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
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
            is CurrentKenyanRecipeState.Success -> KenyanRecipeDetails(
                modifier = modifier,
                recipe = result.currentKenyanRecipe,
                onSendReview = {
                    recipesViewModel.addReview(
                        reviewText = it,
                        region = "kenyan",
                        recipeId = result.currentKenyanRecipe.uuid,
                    )
                },
                onSendRating = {
                    recipesViewModel.addRating(
                        ratingNumber = it,
                        region = "kenyan",
                        recipeId = result.currentKenyanRecipe.uuid
                    )
                },
                newReviewsViewModel = newReviewsViewModel,
                onAddIntake = {
                    recipesViewModel.addRecipeIntake(recipeId = it.recipeId, region = it.region)
                }
            )

            null -> {}
        }


    }


}

@Composable
fun KenyanRecipeDetails(
    modifier: Modifier = Modifier,
    recipe: KenyanRecipe,
    onSendReview: (String) -> Unit,
    onSendRating: (Int) -> Unit,
    newReviewsViewModel: NewReviewsViewModel,
    onAddIntake: (OnAddIntakeDetails) -> Unit
) {

    Text(text = recipe.recipeName, fontSize = 32.sp)
    Button(onClick = { onAddIntake(OnAddIntakeDetails(recipeId = recipe.uuid, region = "kenyan")) }) {
        Text("add to my intake")
    }
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
    Text("About", fontSize = 25.sp)
    Text(text = recipe.about)
    Text(text = "Ingredients", fontSize = 25.sp)
    recipe.parsedIngredientsList.forEach { ingredient ->
        Text(text = ingredient)
    }
    Text(text = "instructions", fontSize = 25.sp)
    Text(text = recipe.preparation)
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
        Text("Energy:${recipe.energykcal} kcal")
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
        Text("carbohydrates by summation:${recipe.carbohydratesg} g")
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
        Text("fiber:${recipe.fibreg} g")
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
        Text("proteins:${recipe.proteinsg} g")
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
        Text("fats:${recipe.fatg} g")
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
        Text("Vitamin A:${recipe.vitaminAmcg} mcg")
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.icons8_cod_48),
            contentDescription = "zinc(mg)",
            modifier = Modifier.size(48.dp)
        )
        Text("zinc(mg):${recipe.zincmg} mg")
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
        Text("iron:${recipe.ironmg} mg")
    }
    RatingStar(onStarClick = {
        onSendRating(it)
    })
    AddReview(onSend = {
        onSendReview(it)
    })
    NewReviews(newReviewsViewModel = newReviewsViewModel, recipeId = recipe.uuid, region = "kenyan")


}