package com.migualador.cocktails.data.datasources.non_alcoholic_cocktails

import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NonAlcoholicCocktailsLocalDataSource
 *
 * Data source that holds non alcoholic cocktails data in memory
 */
@Singleton
class NonAlcoholicCocktailsLocalDataSource @Inject constructor() {

    private var cocktails: List<Cocktail> = listOf()

    fun getNonAlcoholicCocktails(): List<Cocktail> {
        return cocktails
    }

    fun setNonAlcoholicCocktails(cocktails: List<Cocktail>) {
        this.cocktails = cocktails
    }

}