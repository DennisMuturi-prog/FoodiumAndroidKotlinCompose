package com.example.foodium.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodium.R
import com.example.foodium.models.OpenFoodFactsData
import com.example.foodium.ui.viewmodels.OpenFoodFactsViewModel

@Composable
fun FoodFacts(modifier: Modifier = Modifier, foodInfo: OpenFoodFactsData) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        foodInfo.product?.let { Text(text = it.productName, fontSize = 32.sp) }

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Nutritional grade : ")
        foodInfo.product?.let { Text(text = it.nutritionGrades) }

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.calories_svgrepo_com),
            contentDescription = "energy",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "Energy in Calories : ")
        Text(text = foodInfo.product?.nutriments?.energy.toString())

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.grain_gluten_wheat_carbohydrates_svgrepo_com),
            contentDescription = "carbs",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "Carbohydrates :  ")
        Text(text = foodInfo.product?.nutriments?.carbohydrates.toString())

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
        Text(text = "Fats : ")
        Text(text = foodInfo.product?.nutriments?.fat.toString())

    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(R.drawable.steak_svgrepo_com),
            contentDescription = "proteins",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "Proteins : ")
        Text(text = foodInfo.product?.nutriments?.proteins.toString())

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.sugar_svgrepo_com),
            contentDescription = "sugars",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "Sugars : ")
        Text(text = foodInfo.product?.nutriments?.sugars.toString())

    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.protein_fat_svgrepo_com),
            contentDescription = "saturated fat",
            modifier = Modifier.size(48.dp)
        )
        Text(text = "Saturated fat : ")
        Text(text = foodInfo.product?.nutriments?.saturatedFat.toString())

    }

}