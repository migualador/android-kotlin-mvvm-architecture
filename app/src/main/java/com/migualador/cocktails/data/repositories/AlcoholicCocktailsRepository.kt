package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject

/**
 * AlcoholicCocktailsRepository
 *
 * Holds alcoholic cocktails list
 */
class AlcoholicCocktailsRepository @Inject constructor (
    private val alcoholicCocktailsRemoteDataSource: AlcoholicCocktailsRemoteDataSource,
) {

    suspend fun getAlcoholicCocktails(): List<Cocktail>? {
        return alcoholicCocktailsRemoteDataSource.getAlcoholicCocktails()
    }

    suspend fun getAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = alcoholicCocktailsRemoteDataSource.getAlcoholicCocktails()
        val resultCocktails = cocktails?.filter { cocktailsIds.contains(it.id) }

        return resultCocktails ?: emptyList()
    }
}