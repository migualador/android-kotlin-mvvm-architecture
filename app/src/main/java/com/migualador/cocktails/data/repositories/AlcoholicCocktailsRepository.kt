package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsDBDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsLocalDataSource
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.AlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
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
    private val alcoholicCocktailsDBDataSource: AlcoholicCocktailsDBDataSource,
    private val logger: Logger
) {

    suspend fun getAlcoholicCocktails(): List<Cocktail> {
        // first, try to retrieve from memory
        var cocktails = alcoholicCocktailsLocalDataSource.getAlcoholicCocktails()
        log("AlcoholicCocktailsLocalDataSource", cocktails)

        // if no results, retrieve from network
        if (cocktails.isEmpty()) {
            val networkResult = alcoholicCocktailsRemoteDataSource.getAlcoholicCocktails()
            cocktails = when (networkResult) {
                is NetworkResult.Success -> {
                    networkResult.data?.let {
                        alcoholicCocktailsLocalDataSource.setAlcoholicCocktails(it)
                        alcoholicCocktailsDBDataSource.setAlcoholicCocktails(it)
                        it
                    } ?: emptyList()
                }
                is NetworkResult.HttpError -> emptyList()
                is NetworkResult.ConnectionError -> emptyList()
            }

            log("AlcoholicCocktailsRemoteDataSource", cocktails)
        }

        // if no results, retrieve from Room
        if (cocktails.isEmpty()) {
            cocktails = alcoholicCocktailsDBDataSource.getAlcoholicCocktails()
            log("AlcoholicCocktailsDBDataSource", cocktails)
        }

        return cocktails
    }

    suspend fun getAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = getAlcoholicCocktails()

        return cocktails.filter { cocktailsIds.contains(it.id) }
    }

    private fun log(datasourceName: String, cocktails: List<Cocktail>) {
        logger.log("Retrieving from ${datasourceName}: ${cocktails.size} cocktails")
    }
}