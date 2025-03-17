package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIntake(
    @SerialName("Carbohydrate_by_difference")
    val carbohydrateByDifference: Double,
    @SerialName("Carbohydrate_by_summation")
    val carbohydrateBySummation: Double,
    @SerialName("created_at")
    val createdAt:String,
    @SerialName("directions")
    val directions: List<String>,
    @SerialName("Energy")
    val energy: Double,
    @SerialName("Fiber_total_dietary")
    val fiberTotalDietary: Double,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("ingredients")
    val ingredients: List<String>,
    @SerialName("Iron_Fe")
    val ironFe: Double,
    @SerialName("NER")
    val nER: List<String>,
    @SerialName("no_of_ratings")
    val noOfRatings: Int,
    @SerialName("Protein")
    val protein: Double,
    @SerialName("recipe_name")
    val recipeName: String,
    @SerialName("recipe_rating")
    val recipeRating: Double,
    @SerialName("recipe_uuid")
    val recipeUuid: String,
    @SerialName("Retinol")
    val retinol: Double,
    @SerialName("Riboflavin")
    val riboflavin: Double,
    @SerialName("Starch")
    val starch: Double,
    @SerialName("Sugars_Total")
    val sugarsTotal: Double,
    @SerialName("Total_fat_NLEA")
    val totalFatNLEA: Double,
    @SerialName("uuid")
    val uuid: String,
    @SerialName("Vitamin_A_RAE")
    val vitaminARAE: Double,
    @SerialName("Vitamin_B_12")
    val vitaminB12: Double,
    @SerialName("Vitamin_C_total_ascorbic_acid")
    val vitaminCTotalAscorbicAcid: Double,
    @SerialName("Vitamin_D4")
    val vitaminD4: Double,
    @SerialName("Vitamin_D_D2_and_D3")
    val vitaminDD2AndD3: Double
)

fun UserIntake.toWorldwideRecipe(): WorldwideRecipe {
    return WorldwideRecipe(
        carbohydrateByDifference = this.carbohydrateByDifference,
        carbohydrateBySummation = this.carbohydrateBySummation,
        directions = this.directions,
        energy = this.energy,
        fiberTotalDietary = this.fiberTotalDietary,
        imageUrl = this.imageUrl,
        ingredients = this.ingredients,
        ironFe = this.ironFe,
        nER = this.nER,
        noOfRatings = this.noOfRatings,
        protein = this.protein,
        recipeName = this.recipeName,
        recipeRating = this.recipeRating.toFloat(), // Convert Double to Float
        retinol = this.retinol,
        riboflavin = this.riboflavin,
        starch = this.starch,
        sugarsTotal = this.sugarsTotal,
        totalFatNLEA = this.totalFatNLEA,
        uuid = this.recipeUuid, // Use `recipeUuid` from `UserIntake` for `uuid`
        vitaminARAE = this.vitaminARAE,
        vitaminB12 = this.vitaminB12,
        vitaminCTotalAscorbicAcid = this.vitaminCTotalAscorbicAcid,
        vitaminD4 = this.vitaminD4,
        vitaminDD2AndD3 = this.vitaminDD2AndD3
    )
}
