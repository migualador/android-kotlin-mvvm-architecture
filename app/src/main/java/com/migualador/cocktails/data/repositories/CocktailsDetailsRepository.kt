package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsDBDataSource
import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsLocalDataSource
import com.migualador.cocktails.data.datasources.cocktail_detail.CocktailsDetailsRemoteDataSource
import com.migualador.cocktails.data.entities.CocktailDetail
import com.migualador.cocktails.data.network.NetworkResult
import com.migualador.cocktails.other.logging.Logger
import javax.inject.Inject

/**
 * CocktailsDetailsRepository
 *
 * Repository with cocktails details data. Uses three levels of data sources:
 * -Memory data source
 * -Network data source
 * -Room data source
 */
class CocktailsDetailsRepository @Inject constructor (
    private val cocktailsDetailsLocalDataSource: CocktailsDetailsLocalDataSource,
    private val cocktailsDetailsRemoteDataSource: CocktailsDetailsRemoteDataSource,
    private val cocktailsDetailsDBDataSource: CocktailsDetailsDBDataSource,
    private val logger: Logger
) {

    suspend fun getCocktailDetail(cocktailId: String): CocktailDetail? {

        // first, try to retrieve from local (memory)
        var cocktailDetail = retrieveFromLocalDataSource(cocktailId)

        // if no results from local, retrieve from network
        if (cocktailDetail == null) {
            cocktailDetail = retrieveFromRemoteDataSource(cocktailId)
        }

        // if no results from network, retrieve from DB (Room)
        if (cocktailDetail == null) {
            cocktailDetail = retrieveFromDBDataSource(cocktailId)
        } else {
            // store in local and DB for later use
            cocktailsDetailsLocalDataSource.addCocktailDetail(cocktailDetail)
            cocktailsDetailsDBDataSource.addCocktailDetail(cocktailDetail)
        }

        return cocktailDetail
    }

    private fun retrieveFromLocalDataSource(cocktailId: String): CocktailDetail? {
        val result = cocktailsDetailsLocalDataSource.getCocktailDetail(cocktailId)

        log("CocktailsDetailsLocalDataSource", result)
        return result
    }

    private suspend fun retrieveFromRemoteDataSource(cocktailId: String): CocktailDetail? {
        val networkResult = cocktailsDetailsRemoteDataSource.getCocktailDetail(cocktailId)
        val result = when {
            (networkResult is NetworkResult.Success) && (networkResult.data != null) -> networkResult.data
            else -> null  // HttpError, ConnectionError, empty response
        }

        log("CocktailsDetailsRemoteDataSource", result)
        return result
    }

    private fun retrieveFromDBDataSource(cocktailId: String): CocktailDetail? {
        val result = cocktailsDetailsDBDataSource.getCocktailDetail(cocktailId)

        log("CocktailsDetailsDBDataSource", result)
        return result
    }

    private fun log(datasourceName: String, cocktailDetail: CocktailDetail?) {
        val count = if (cocktailDetail == null) 0
        else 1
        logger.log("Retrieving from ${datasourceName}: $count cocktail detail")
    }
}