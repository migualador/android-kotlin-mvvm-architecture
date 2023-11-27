package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsDBDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsLocalDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import javax.inject.Inject

/**
 * AlcoholicCocktailsRepository
 *
 * Repository with alcoholic cocktails data. Uses three levels of data sources:
 * -Memory data source
 * -Network data source
 * -Room data source
 */
class AlcoholicCocktailsRepository @Inject constructor (
    private val alcoholicCocktailsLocalDataSource: AlcoholicCocktailsLocalDataSource,
    private val alcoholicCocktailsRemoteDataSource: AlcoholicCocktailsRemoteDataSource,
    private val alcoholicCocktailsDBDataSource: AlcoholicCocktailsDBDataSource
) {

    suspend fun getAlcoholicCocktails(): List<Cocktail> {
        // first, try to retrieve from memory
        var cocktails = alcoholicCocktailsLocalDataSource.getAlcoholicCocktails()

        // if no results, retrieve from network
        if (cocktails.isEmpty()) {
            val networkResult = alcoholicCocktailsRemoteDataSource.getAlcoholicCocktails()
            cocktails = when (networkResult) {
                is NetworkResult.Success -> {
                    alcoholicCocktailsLocalDataSource.setAlcoholicCocktails(networkResult.data)
                    alcoholicCocktailsDBDataSource.setAlcoholicCocktails(networkResult.data)
                    networkResult.data
                }
                is NetworkResult.HttpError -> emptyList()
                is NetworkResult.ConnectionError -> emptyList()
            }
        }

        // if no results, retrieve from Room
        if (cocktails.isEmpty()) {
            cocktails = alcoholicCocktailsDBDataSource.getAlcoholicCocktails()
        }

        return cocktails
    }

    suspend fun getAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = getAlcoholicCocktails()

        return cocktails.filter { cocktailsIds.contains(it.id) }
    }
}