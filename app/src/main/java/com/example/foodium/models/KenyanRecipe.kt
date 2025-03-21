package com.example.foodium.models


import co.yml.charts.common.model.Point
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KenyanRecipe(
    @SerialName("about")
    val about: String,
    @SerialName("carbohydrates(g)")
    val carbohydratesg: Double,
    @SerialName("energy(kcal)")
    val energykcal: Double,
    @SerialName("F_factor_est")
    val fFactorEst: Double,
    @SerialName("fat(g)")
    val fatg: Double,
    @SerialName("fibre(g)")
    val fibreg: Double,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("ingredients")
    val ingredients: String,
    @SerialName("ingridients")
    val ingridients: String,
    @SerialName("instructions")
    val instructions: String,
    @SerialName("iron(mg)")
    val ironmg: Double,
    @SerialName("no_of_ratings")
    val noOfRatings: Int,
    @SerialName("nutrition per 100g of recipe")
    val nutritionPer100gOfRecipe: String,
    @SerialName("page")
    val page: Int,
    @SerialName("parsedIngredientsList")
    val parsedIngredientsList: List<String>,
    @SerialName("preparation")
    val preparation: String,
    @SerialName("proteins(g)")
    val proteinsg: Double,
    @SerialName("recipe_name")
    val recipeName: String,
    @SerialName("recipe_rating")
    val recipeRating: Float,
    @SerialName("supplementary_ingredients")
    val supplementaryIngredients: String,
    @SerialName("supplementary_instructions")
    val supplementaryInstructions: String,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("vitamin A(mcg)")
    val vitaminAmcg: Int,
    @SerialName("zinc(mg)")
    val zincmg: Double
)

data class NutrientPoints(
    val carbs:List<Point>,
    val proteins:List<Point>,
    val energy:List<Point>,
    val fibre: List<Point>,
    val fat:List<Point>,
    val vitaminA:List<Point>,
    val iron:List<Point>
)