package com.migualador.cocktails.data.network.mapper_extensions

import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.network.entities.Drink
import com.migualador.cocktails.data.network.entities.DrinkX

fun Drink.toCocktail(): Cocktail {
    return Cocktail(
        this.idDrink,
        this.strDrink,
        this.strDrinkThumb
    )
}

fun DrinkX.toCocktailDetail(): CocktailDetail {
    return CocktailDetail(
        id = this.idDrink,
        name = this.strDrink ?: "",
        alcoholic = this.strAlcoholic ?: "",
        category = this.strCategory ?: "",
        imageUrl = this.strDrinkThumb ?: "",
        glassType = this.strGlass ?: "",
        ingredient1 = this.strIngredient1 ?: "",
        ingredient2 = this.strIngredient2 ?: "",
        ingredient3 = this.strIngredient3 ?: "",
        ingredient4 = this.strIngredient4 ?: "",
        ingredient5 = this.strIngredient5 ?: "",
        ingredient6 = this.strIngredient6 ?: "",
        ingredient7 = this.strIngredient7 ?: "",
        ingredient8 = this.strIngredient8 ?: "",
        ingredient9 = this.strIngredient9 ?: "",
        ingredient10 = this.strIngredient10 ?: "",
        ingredient11 = this.strIngredient11 ?: "",
        ingredient12 = this.strIngredient12 ?: "",
        ingredient13 = this.strIngredient13 ?: "",
        ingredient14 = this.strIngredient14 ?: "",
        ingredient15 = this.strIngredient15 ?: "",
        measure1 = this.strMeasure1 ?: "",
        measure2 = this.strMeasure2 ?: "",
        measure3 = this.strMeasure3 ?: "",
        measure4 = this.strMeasure4 ?: "",
        measure5 = this.strMeasure5 ?: "",
        measure6 = this.strMeasure6 ?: "",
        measure7 = this.strMeasure7 ?: "",
        measure8 = this.strMeasure8 ?: "",
        measure9 = this.strMeasure9 ?: "",
        measure10 = this.strMeasure10 ?: "",
        measure11 = this.strMeasure11 ?: "",
        measure12 = this.strMeasure12 ?: "",
        measure13 = this.strMeasure13 ?: "",
        measure14 = this.strMeasure14 ?: "",
        measure15 = this.strMeasure15 ?: "",
        instructions = this.strInstructions ?: ""
    )
}