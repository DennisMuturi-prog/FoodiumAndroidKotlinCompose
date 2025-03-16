package com.example.foodium.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodium.ui.components.RecipesSearchBar

@Composable
fun SearchScreen(modifier: Modifier = Modifier,recipesViewModel: RecipesViewModel) {
    val searchResults by recipesViewModel.searchResults.collectAsStateWithLifecycle()
    Column(modifier = modifier.fillMaxSize()){
        RecipesSearchBar(searchResults=searchResults, onSearchQueryChange = {recipesViewModel.onSearchQueryChange(it)}, searchQuery = recipesViewModel.searchQuery)

    }
}