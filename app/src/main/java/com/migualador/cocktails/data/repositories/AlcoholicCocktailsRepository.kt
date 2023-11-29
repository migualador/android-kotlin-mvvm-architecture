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

        // first, try to retrieve from local (memory)
        var cocktails = retrieveFromLocalDataSource()

        // if no results from local, retrieve from network
        if (cocktails.isEmpty()) {
            cocktails = retrieveFromRemoteDataSource()
        }

        // if no results from network, retrieve from DB (Room)
        if (cocktails.isEmpty()) {
            cocktails = retrieveFromDBDataSource()
        } else {
            // store in local and DB for later use
            alcoholicCocktailsLocalDataSource.setAlcoholicCocktails(cocktails)
            alcoholicCocktailsDBDataSource.setAlcoholicCocktails(cocktails)
        }

        return cocktails
    }

    private fun retrieveFromLocalDataSource(): List<Cocktail> {
        val result = alcoholicCocktailsLocalDataSource.getAlcoholicCocktails()

        log("AlcoholicCocktailsLocalDataSource", result)
        return result
    }

    private suspend fun retrieveFromRemoteDataSource(): List<Cocktail> {
        val networkResult = alcoholicCocktailsRemoteDataSource.getAlcoholicCocktails()
        val result = when {
            (networkResult is NetworkResult.Success) && (networkResult.data != null) -> networkResult.data
            else -> emptyList()  // HttpError, ConnectionError, empty response
        }

        log("AlcoholicCocktailsRemoteDataSource", result)
        return result
    }

    private fun retrieveFromDBDataSource(): List<Cocktail> {
        val result = alcoholicCocktailsDBDataSource.getAlcoholicCocktails()

        log("AlcoholicCocktailsDBDataSource", result)
        return result
    }

    suspend fun getAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = getAlcoholicCocktails()
        return cocktails.filter { cocktailsIds.contains(it.id) }
    }

    private fun log(datasourceName: String, cocktails: List<Cocktail>) {
        logger.log("Retrieving from ${datasourceName}: ${cocktails.size} cocktails")
    }
}