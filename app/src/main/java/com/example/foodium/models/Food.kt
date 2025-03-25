package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Food(
    @SerialName("Carbohydrate_by_difference")
    val carbohydrateByDifference: Double,
    @SerialName("Carbohydrate_by_summation")
    val carbohydrateBySummation: Double,
    @SerialName("Energy")
    val energy: Double,
    @SerialName("Fiber_total_dietary")
    val fiberTotalDietary: Double,
    @SerialName("food_name")
    val foodName: String,
    @SerialName("Iron_Fe")
    val ironFe: Double,
    @SerialName("Protein")
    val protein: Double,
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
fun Food.toWorldwideRecipe(): WorldwideRecipe {
    return WorldwideRecipe(
        carbohydrateByDifference = this.carbohydrateByDifference,
        carbohydrateBySummation = this.carbohydrateBySummation,
        directions = emptyList(),
        energy = this.energy,
        fiberTotalDietary = this.fiberTotalDietary,
        imageUrl = "",
        ingredients = emptyList(),
        ironFe = this.ironFe,
        nER = emptyList(),
        noOfRatings = 0,
        protein = this.protein,
        recipeName = this.foodName,
        recipeRating = 0f, // Convert Double to Float
        retinol = this.retinol,
        riboflavin = this.riboflavin,
        starch = this.starch,
        sugarsTotal = this.sugarsTotal,
        totalFatNLEA = this.totalFatNLEA,
        uuid = this.uuid, // Use `recipeUuid` from `UserIntake` for `uuid`
        vitaminARAE = this.vitaminARAE,
        vitaminB12 = this.vitaminB12,
        vitaminCTotalAscorbicAcid = this.vitaminCTotalAscorbicAcid,
        vitaminD4 = this.vitaminD4,
        vitaminDD2AndD3 = this.vitaminDD2AndD3
    )
}