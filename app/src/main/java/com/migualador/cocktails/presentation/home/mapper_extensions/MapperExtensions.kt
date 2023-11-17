package com.migualador.cocktails.presentation.home.mapper_extensions

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.presentation.home.ui_states.CocktailDetailUiState
import com.migualador.cocktails.presentation.home.ui_states.CocktailUiState
import com.migualador.cocktails.presentation.home.ui_states.IngredientAndMeasure

/**
 * Maps a Cocktail entity to CocktailUiState
 */
fun Cocktail.toCocktailUiState(
    onClick: () -> Unit = {}
): CocktailUiState {
    return CocktailUiState(
        name = this.name,
        imageUrl = this.imageUrl,
        onClick = onClick
    )
}

/**
 * Maps a CocktailDetail entity to CocktailDetailUiState
 */
fun CocktailDetail.toCocktailDetailUiState(
    onClick: () -> Unit = {}
): CocktailDetailUiState {
    val ingredientsAndMeasuresList = listOf(
        IngredientAndMeasure(measure1, ingredient1),
        IngredientAndMeasure(measure2, ingredient2),
        IngredientAndMeasure(measure3, ingredient3),
        IngredientAndMeasure(measure4, ingredient4),
        IngredientAndMeasure(measure5, ingredient5),
        IngredientAndMeasure(measure6, ingredient6),
        IngredientAndMeasure(measure7, ingredient7),
        IngredientAndMeasure(measure8, ingredient8),
        IngredientAndMeasure(measure9, ingredient9),
        IngredientAndMeasure(measure10, ingredient10),
        IngredientAndMeasure(measure11, ingredient11),
        IngredientAndMeasure(measure12, ingredient12),
        IngredientAndMeasure(measure13, ingredient13),
        IngredientAndMeasure(measure14, ingredient14),
        IngredientAndMeasure(measure15, ingredient15)
    )

    val isAlcoholicDrink = alcoholic.lowercase() == "alcoholic"

    return CocktailDetailUiState(
        isAlcoholic = isAlcoholicDrink,
        category = this.category,
        drinkName = this.name,
        imageUrl = this.imageUrl,
        glassType = this.glassType,
        ingredientsAndMeasures = ingredientsAndMeasuresList,
        instructions = this.instructions,
        onClick = onClick
    )
}

