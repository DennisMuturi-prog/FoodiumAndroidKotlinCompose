package com.example.foodium.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.foodium.models.KenyanRecipe
import com.example.foodium.models.WorldwideRecipe
import com.example.foodium.repository.Repository
import com.example.foodium.ui.components.snackbarconfig.SnackbarController
import com.example.foodium.ui.components.snackbarconfig.SnackbarEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import okio.IOException


sealed interface CurrentWorldwideRecipeState {
    data class Success(val currentWorldwideRecipe: WorldwideRecipe) : CurrentWorldwideRecipeState
}

sealed interface CurrentKenyanRecipeState {
    data class Success(val currentKenyanRecipe: KenyanRecipe) : CurrentKenyanRecipeState
}

sealed interface AddRatingState {
    data object Success : AddRatingState
    data class Error(val message: String) : AddRatingState
    data object Loading : AddRatingState
}

sealed interface AddReviewState {
    data object Success : AddReviewState
    data class Error(val message: String) : AddReviewState
    data object Loading : AddReviewState
}

class RecipesViewModel(private val repository: Repository) : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set
    val searchResults: StateFlow<List<KenyanRecipe>> =
        snapshotFlow { searchQuery }.filter { searchPrefix ->
            (searchPrefix.length > 1)

        }
            .debounce(300).distinctUntilChanged()
            .flatMapLatest { searchTerm ->
                repository.searchKenyanRecipes(region = "kenyan", searchTerm = searchTerm)
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.Lazily
            )


    var recipes: Flow<PagingData<WorldwideRecipe>> =
        repository.getWorldwideRecipes().cachedIn(viewModelScope)
    var kenyanRecipes: Flow<PagingData<KenyanRecipe>> =
        repository.getKenyanRecipes().cachedIn(viewModelScope)
    private val _currentWorldwideRecipeState = MutableLiveData<CurrentWorldwideRecipeState>()
    val currentWorldwideRecipeState: LiveData<CurrentWorldwideRecipeState> =
        _currentWorldwideRecipeState
    private val _currentKenyanRecipeState = MutableLiveData<CurrentKenyanRecipeState>()
    val currentKenyanRecipeState: LiveData<CurrentKenyanRecipeState> = _currentKenyanRecipeState
    private val _addRatingState = MutableLiveData<AddRatingState>()
    val addRatingState: LiveData<AddRatingState> = _addRatingState
    private val _addReviewState = MutableLiveData<AddReviewState>()
    val addReviewState: LiveData<AddReviewState> = _addReviewState


    init {
        getTokens()
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun changeCurrentRecipe(recipe: WorldwideRecipe) {
        _currentWorldwideRecipeState.value = CurrentWorldwideRecipeState.Success(recipe)
    }

    fun changeCurrentKenyanRecipe(recipe: KenyanRecipe) {
        _currentKenyanRecipeState.value = CurrentKenyanRecipeState.Success(recipe)
    }

    private fun attachDataSource() {
        recipes = repository.getWorldwideRecipes().cachedIn(viewModelScope)
        kenyanRecipes = repository.getKenyanRecipes().cachedIn(viewModelScope)
    }

    private fun getTokens() {
        viewModelScope.launch {
            try {
                repository.getAuthTokens()
                attachDataSource()

            } catch (e: HttpException) {
                Log.d("error in recipes", e.toString())
            }
        }

    }

    fun addRating(ratingNumber: Int, region: String, recipeId: String) {
        viewModelScope.launch {
            _addRatingState.value = try {
                repository.addRating(
                    ratingNumber = ratingNumber,
                    region = region,
                    recipeId = recipeId
                )
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "login successful",
                    )
                )
                AddRatingState.Success

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AddRatingState.Error(errorMessage)

            } catch (e: IOException) {
                AddRatingState.Error(e.toString())
            }
        }
    }

    fun addReview(reviewText: String, region: String, recipeId: String) {
        _addReviewState.value = AddReviewState.Loading
        viewModelScope.launch {
            _addReviewState.value = try {
                repository.addReview(reviewText = reviewText, region = region, recipeId = recipeId)
                SnackbarController.sendEvent(
                    event = SnackbarEvent(
                        message = "review added successful",
                    )
                )
                AddReviewState.Success

            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: "Unknown error"
                AddReviewState.Error(errorMessage)

            } catch (e: IOException) {
                val errorMessage = e.message ?: "io error"
                AddReviewState.Error(errorMessage)
            }
        }
    }

}