package com.example.foodium.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodium.models.KenyanRecipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesSearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchResults: List<KenyanRecipe>,
    onSearchQueryChange: (String) -> Unit
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
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = searchResults.size,
                key = { index -> searchResults[index].uuid },
                itemContent = { index ->
                    val recipe = searchResults[index]
                    Text(recipe.recipeName)
                }
            )
        }


    }


}




