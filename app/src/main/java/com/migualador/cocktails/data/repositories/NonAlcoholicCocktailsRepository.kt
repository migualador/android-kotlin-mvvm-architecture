package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.NonAlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject

/**
 * NonAlcoholicCocktailsRepository
 *
 * Holds non alcoholic cocktails list
 */
class NonAlcoholicCocktailsRepository @Inject constructor (
    private val nonAlcoholicCocktailsRemoteDataSource: NonAlcoholicCocktailsRemoteDataSource
) {

   suspend fun getNonAlcoholicCocktails(): List<Cocktail>? {
        return nonAlcoholicCocktailsRemoteDataSource.getNonAlcoholicCocktails()
    }

    suspend fun getNonAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = nonAlcoholicCocktailsRemoteDataSource.getNonAlcoholicCocktails()
        val resultCocktails = cocktails?.filter { cocktailsIds.contains(it.id) }

        return resultCocktails ?: emptyList()
    }
}