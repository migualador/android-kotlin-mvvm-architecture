package com.migualador.cocktails.presentation.home.ui_states

data class CocktailUiState(
    val name: String,
    val imageUrl: String,
    var onClick: () -> Unit = {}
)

data class CocktailDetailUiState(
    val isAlcoholic: Boolean,
    val category: String,
    val drinkName: String,
    val imageUrl: String,
    val glassType: String,
    val ingredientsAndMeasures: List<IngredientAndMeasure>,
    val instructions: String,
    var onClick: () -> Unit = {}
)

data class IngredientAndMeasure(
    val ingredient: String,
    val measure: String
)