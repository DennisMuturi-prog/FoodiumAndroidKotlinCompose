package com.example.foodium.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.foodium.R
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel

@Composable
fun FoodFacts(modifier: Modifier = Modifier,foodInfo:OpenFoodFactsData) {
    Column {
        Row {
            Text(text="Product name")
            Text(text=foodInfo.product.productName)

        }
        Row {
            Image(painter =  painterResource(R.drawable.icons8_google_48),
                contentDescription = "google sign in")
            Text(text="Nutritional grade")
            Text(text=foodInfo.product.nutritionGrades)

        }
        Row {
            Image(painter =  painterResource(R.drawable.calories_svgrepo_com),
                contentDescription = "energy")
            Text(text="Energy in Calories")
            Text(text=foodInfo.product.nutriments.energy.toString())

        }
        Row {
            Image(painter =  painterResource(R.drawable.grain_gluten_wheat_carbohydrates_svgrepo_com),
                contentDescription = "carbs")
            Text(text="Carbohydrates")
            Text(text=foodInfo.product.nutriments.carbohydrates.toString())

        }
        Row {
            Image(painter =  painterResource(R.drawable.protein_fat_svgrepo_com),
                contentDescription = "fats")
            Text(text="Fats")
            Text(text=foodInfo.product.nutriments.fat.toString())

        }
        Row {
            Image(painter =  painterResource(R.drawable.steak_svgrepo_com),
                contentDescription = "proteins")
            Text(text="Proteins")
            Text(text=foodInfo.product.nutriments.proteins.toString())

        }
        Row {
            Image(painter =  painterResource(R.drawable.sugar_svgrepo_com),
                contentDescription = "sugars")
            Text(text="Sugars")
            Text(text=foodInfo.product.nutriments.sugars.toString())

        }
        Row {
            Image(painter =  painterResource(R.drawable.protein_fat_svgrepo_com),
                contentDescription = "saturated fat")
            Text(text="Saturated fat")
            Text(text=foodInfo.product.nutriments.saturatedFat.toString())

        }

    }

}