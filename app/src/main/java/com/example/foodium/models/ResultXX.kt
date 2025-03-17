package com.example.foodium.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserKenyanIntake(
    @SerialName("about")
    val about: String,
    @SerialName("carbohydrates(g)")
    val carbohydratesg: Double,
    @SerialName("created_at")
    val createdAt:String,
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
    @SerialName("recipe_uuid")
    val recipeUuid: String,
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

fun UserKenyanIntake.toKenyanRecipe(): KenyanRecipe {
    return KenyanRecipe(
        about = this.about,
        carbohydratesg = this.carbohydratesg,
        energykcal = this.energykcal,
        fFactorEst = this.fFactorEst,
        fatg = this.fatg,
        fibreg = this.fibreg,
        imageUrl = this.imageUrl,
        ingredients = this.ingredients,
        ingridients = this.ingridients,
        instructions = this.instructions,
        ironmg = this.ironmg,
        noOfRatings = 0, // Default since UserKenyanIntake doesn't have this field
        nutritionPer100gOfRecipe = this.nutritionPer100gOfRecipe,
        page = this.page,
        parsedIngredientsList = this.parsedIngredientsList,
        preparation = this.preparation,
        proteinsg = this.proteinsg,
        recipeName = this.recipeName,
        recipeRating = 0.0f, // Default since UserKenyanIntake doesn't have this field
        supplementaryIngredients = this.supplementaryIngredients,
        supplementaryInstructions = this.supplementaryInstructions,
        uuid = this.recipeUuid, // Mapping `recipe_uuid` from UserKenyanIntake to `uuid`
        vitaminAmcg = this.vitaminAmcg,
        zincmg = this.zincmg
    )
}
