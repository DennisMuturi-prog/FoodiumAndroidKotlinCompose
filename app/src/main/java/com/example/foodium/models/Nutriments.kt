package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nutriments(
    @SerialName("carbohydrates")
    val carbohydrates: Double?= null,
    @SerialName("carbohydrates_100g")
    val carbohydrates100g: Double?= null,
    @SerialName("carbohydrates_unit")
    val carbohydratesUnit: String?= null,
    @SerialName("carbohydrates_value")
    val carbohydratesValue: Double?= null,
    @SerialName("energy")
    val energy: Int?= null,
    @SerialName("energy_100g")
    val energy100g: Int?= null,
    @SerialName("energy-kcal")
    val energyKcal: Int?= null,
    @SerialName("energy-kcal_100g")
    val energyKcal100g: Int?= null,
    @SerialName("energy-kcal_unit")
    val energyKcalUnit: String?= null,
    @SerialName("energy-kcal_value")
    val energyKcalValue: Int?= null,
    @SerialName("energy-kcal_value_computed")
    val energyKcalValueComputed: Double?= null,
    @SerialName("energy_unit")
    val energyUnit: String?= null,
    @SerialName("energy_value")
    val energyValue: Int?= null,
    @SerialName("fat")
    val fat: Double?= null,
    @SerialName("fat_100g")
    val fat100g: Double?= null,
    @SerialName("fat_unit")
    val fatUnit: String?= null,
    @SerialName("fat_value")
    val fatValue: Double?= null,
    @SerialName("fruits-vegetables-legumes-estimate-from-ingredients_100g")
    val fruitsVegetablesLegumesEstimateFromIngredients100g: Int?= null,
    @SerialName("fruits-vegetables-legumes-estimate-from-ingredients_serving")
    val fruitsVegetablesLegumesEstimateFromIngredientsServing: Int?= null,
    @SerialName("fruits-vegetables-nuts-estimate-from-ingredients_100g")
    val fruitsVegetablesNutsEstimateFromIngredients100g: Double?= null,
    @SerialName("fruits-vegetables-nuts-estimate-from-ingredients_serving")
    val fruitsVegetablesNutsEstimateFromIngredientsServing: Double?= null,
    @SerialName("nova-group")
    val novaGroup: Int?= null,
    @SerialName("nova-group_100g")
    val novaGroup100g: Int?= null,
    @SerialName("nova-group_serving")
    val novaGroupServing: Int?= null,
    @SerialName("nutrition-score-fr")
    val nutritionScoreFr: Int?= null,
    @SerialName("nutrition-score-fr_100g")
    val nutritionScoreFr100g: Int?= null,
    @SerialName("proteins")
    val proteins: Double?= null,
    @SerialName("proteins_100g")
    val proteins100g: Double?= null,
    @SerialName("proteins_unit")
    val proteinsUnit: String?= null,
    @SerialName("proteins_value")
    val proteinsValue: Double?= null,
    @SerialName("salt")
    val salt: Double?= null,
    @SerialName("salt_100g")
    val salt100g: Double?= null,
    @SerialName("salt_unit")
    val saltUnit: String?= null,
    @SerialName("salt_value")
    val saltValue: Double?= null,
    @SerialName("saturated-fat")
    val saturatedFat: Double?= null,
    @SerialName("saturated-fat_100g")
    val saturatedFat100g: Double?= null,
    @SerialName("saturated-fat_unit")
    val saturatedFatUnit: String?= null,
    @SerialName("saturated-fat_value")
    val saturatedFatValue: Double?= null,
    @SerialName("sodium")
    val sodium: Double?= null,
    @SerialName("sodium_100g")
    val sodium100g: Double?= null,
    @SerialName("sodium_unit")
    val sodiumUnit: String?= null,
    @SerialName("sodium_value")
    val sodiumValue: Double?= null,
    @SerialName("sugars")
    val sugars: Double?= null,
    @SerialName("sugars_100g")
    val sugars100g: Double?= null,
    @SerialName("sugars_unit")
    val sugarsUnit: String?= null,
    @SerialName("sugars_value")
    val sugarsValue: Double?= null
)