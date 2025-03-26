package com.example.foodium.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodium.models.Food
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.models.toWorldwideRecipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResults: List<KenyanRecipe>,
    worldwideSearchResults: List<WorldwideRecipe>,
    foodSearchResults: List<Food>,
    onSearchQueryChange: (String) -> Unit,
    onRecipeInfoClick:(WorldwideRecipe)->Unit,
    onKenyanRecipeInfoClick:(KenyanRecipe) ->Unit,
    onFoodInfoClick:(WorldwideRecipe)->Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    SearchBar(
//        modifier = Modifier.align(Alignment.TopCenter).semantics { traversalIndex = 0f },
        inputField = {
            SearchBarDefaults.InputField(
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text("search recipe") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                query = searchQuery,
                onQueryChange = onSearchQueryChange
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].uuid },
                itemContent = { index ->
                    val recipe = searchResults[index]
                    TextButton(onClick = {
                        onKenyanRecipeInfoClick(recipe)


                    }) {
                        Text(recipe.recipeName)
                    }
                }
            )
            items(
                count = worldwideSearchResults.size,
                key = { index -> worldwideSearchResults[index].uuid },
                itemContent = { index ->
                    val recipe = worldwideSearchResults[index]
                    TextButton(onClick = {
                        onRecipeInfoClick(recipe)


                    }) {
                        Text(recipe.recipeName)

                    }
                }
            )
            items(
                count = foodSearchResults.size,
                key = { index -> foodSearchResults[index].uuid },
                itemContent = { index ->
                    val food = foodSearchResults[index]
                    TextButton(onClick = {
                        onFoodInfoClick(food.toWorldwideRecipe())
                    } ){
                        Text(food.foodName)
                    }
                }
            )
        }


    }


}




