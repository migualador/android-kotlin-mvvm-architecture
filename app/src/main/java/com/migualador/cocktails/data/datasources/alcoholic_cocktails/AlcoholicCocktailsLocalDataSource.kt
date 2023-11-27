package com.migualador.cocktails.data.datasources.alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AlcoholicCocktailsLocalDataSource
 *
 * Data source that holds alcoholic cocktails data in memory
 */
@Singleton
class AlcoholicCocktailsLocalDataSource @Inject constructor() {

    private var cocktails: List<Cocktail> = listOf()

    fun getAlcoholicCocktails(): List<Cocktail> {
        return cocktails
    }

    fun setAlcoholicCocktails(cocktails: List<Cocktail>) {
        this.cocktails = cocktails
    }

}