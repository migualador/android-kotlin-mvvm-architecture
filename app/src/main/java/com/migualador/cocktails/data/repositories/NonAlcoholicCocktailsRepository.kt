package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.NonAlcoholicCocktailsDBDataSource
import com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.NonAlcoholicCocktailsLocalDataSource
import com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.NonAlcoholicCocktailsRemoteDataSource
import com.migualador.cocktails.data.entities.Cocktail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
import javax.inject.Inject

/**
 * NonAlcoholicCocktailsRepository
 *
 * Repository with non alcoholic cocktails data. Uses three levels of data sources:
 * -Memory data source
 * -Network data source
 * -Room data source
 */
class NonAlcoholicCocktailsRepository @Inject constructor (
    private val nonAlcoholicCocktailsLocalDataSource: NonAlcoholicCocktailsLocalDataSource,
    private val nonAlcoholicCocktailsRemoteDataSource: NonAlcoholicCocktailsRemoteDataSource,
    private val nonAlcoholicCocktailsDBDataSource: NonAlcoholicCocktailsDBDataSource,
    private val logger: Logger
) {

   suspend fun getNonAlcoholicCocktails(): List<Cocktail> {

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
           nonAlcoholicCocktailsLocalDataSource.setNonAlcoholicCocktails(cocktails)
           nonAlcoholicCocktailsDBDataSource.setNonAlcoholicCocktails(cocktails)
       }

       return cocktails
    }

    private fun retrieveFromLocalDataSource(): List<Cocktail> {
        val result = nonAlcoholicCocktailsLocalDataSource.getNonAlcoholicCocktails()

        log("NonAlcoholicCocktailsLocalDataSource", result)
        return result
    }

    private suspend fun retrieveFromRemoteDataSource(): List<Cocktail> {
        val networkResult = nonAlcoholicCocktailsRemoteDataSource.getNonAlcoholicCocktails()
        val result = when {
            (networkResult is NetworkResult.Success) && (networkResult.data != null) -> networkResult.data
            else -> emptyList()  // HttpError, ConnectionError, empty response
        }

        log("NonAlcoholicCocktailsRemoteDataSource", result)
        return result
    }

    private fun retrieveFromDBDataSource(): List<Cocktail> {
        val result = nonAlcoholicCocktailsDBDataSource.getNonAlcoholicCocktails()

        log("NonAlcoholicCocktailsDBDataSource", result)
        return result
    }

    suspend fun getNonAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = getNonAlcoholicCocktails()

        return cocktails.filter { cocktailsIds.contains(it.id) }
    }

    private fun log(datasourceName: String, cocktails: List<Cocktail>) {
        logger.log("Retrieving from ${datasourceName}: ${cocktails.size} cocktails")
    }
}