package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nutriments(
    @SerialName("carbohydrates")
    val carbohydrates: Double,
    @SerialName("carbohydrates_100g")
    val carbohydrates100g: Double,
    @SerialName("carbohydrates_unit")
    val carbohydratesUnit: String,
    @SerialName("carbohydrates_value")
    val carbohydratesValue: Double,
    @SerialName("energy")
    val energy: Int,
    @SerialName("energy_100g")
    val energy100g: Int,
    @SerialName("energy-kcal")
    val energyKcal: Int,
    @SerialName("energy-kcal_100g")
    val energyKcal100g: Int,
    @SerialName("energy-kcal_unit")
    val energyKcalUnit: String,
    @SerialName("energy-kcal_value")
    val energyKcalValue: Int,
    @SerialName("energy-kcal_value_computed")
    val energyKcalValueComputed: Double,
    @SerialName("energy_unit")
    val energyUnit: String,
    @SerialName("energy_value")
    val energyValue: Int,
    @SerialName("fat")
    val fat: Double,
    @SerialName("fat_100g")
    val fat100g: Double,
    @SerialName("fat_unit")
    val fatUnit: String,
    @SerialName("fat_value")
    val fatValue: Double,
    @SerialName("fruits-vegetables-legumes-estimate-from-ingredients_100g")
    val fruitsVegetablesLegumesEstimateFromIngredients100g: Int,
    @SerialName("fruits-vegetables-legumes-estimate-from-ingredients_serving")
    val fruitsVegetablesLegumesEstimateFromIngredientsServing: Int,
    @SerialName("fruits-vegetables-nuts-estimate-from-ingredients_100g")
    val fruitsVegetablesNutsEstimateFromIngredients100g: Double,
    @SerialName("fruits-vegetables-nuts-estimate-from-ingredients_serving")
    val fruitsVegetablesNutsEstimateFromIngredientsServing: Double,
    @SerialName("nova-group")
    val novaGroup: Int,
    @SerialName("nova-group_100g")
    val novaGroup100g: Int,
    @SerialName("nova-group_serving")
    val novaGroupServing: Int,
    @SerialName("nutrition-score-fr")
    val nutritionScoreFr: Int,
    @SerialName("nutrition-score-fr_100g")
    val nutritionScoreFr100g: Int,
    @SerialName("proteins")
    val proteins: Double,
    @SerialName("proteins_100g")
    val proteins100g: Double,
    @SerialName("proteins_unit")
    val proteinsUnit: String,
    @SerialName("proteins_value")
    val proteinsValue: Double,
    @SerialName("salt")
    val salt: Double,
    @SerialName("salt_100g")
    val salt100g: Double,
    @SerialName("salt_unit")
    val saltUnit: String,
    @SerialName("salt_value")
    val saltValue: Double,
    @SerialName("saturated-fat")
    val saturatedFat: Double,
    @SerialName("saturated-fat_100g")
    val saturatedFat100g: Double,
    @SerialName("saturated-fat_unit")
    val saturatedFatUnit: String,
    @SerialName("saturated-fat_value")
    val saturatedFatValue: Double,
    @SerialName("sodium")
    val sodium: Double,
    @SerialName("sodium_100g")
    val sodium100g: Double,
    @SerialName("sodium_unit")
    val sodiumUnit: String,
    @SerialName("sodium_value")
    val sodiumValue: Double,
    @SerialName("sugars")
    val sugars: Double,
    @SerialName("sugars_100g")
    val sugars100g: Double,
    @SerialName("sugars_unit")
    val sugarsUnit: String,
    @SerialName("sugars_value")
    val sugarsValue: Double
)