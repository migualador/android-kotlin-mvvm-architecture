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
       // first, try to retrieve from memory
       var cocktails = nonAlcoholicCocktailsLocalDataSource.getNonAlcoholicCocktails()
       log("NonAlcoholicCocktailsLocalDataSource", cocktails)

       // if no results, retrieve from network
       if (cocktails.isEmpty()) {
           val networkResult = nonAlcoholicCocktailsRemoteDataSource.getNonAlcoholicCocktails()
           cocktails = when (networkResult) {
               is NetworkResult.Success -> {
                   networkResult.data?.let {
                       nonAlcoholicCocktailsLocalDataSource.setNonAlcoholicCocktails(it)
                       nonAlcoholicCocktailsDBDataSource.setNonAlcoholicCocktails(it)
                       it
                   } ?: emptyList()
               }
               is NetworkResult.HttpError -> emptyList()
               is NetworkResult.ConnectionError -> emptyList()
           }

           log("NonAlcoholicCocktailsRemoteDataSource", cocktails)
       }

       // if no results, retrieve from Room
       if (cocktails.isEmpty()) {
           cocktails = nonAlcoholicCocktailsDBDataSource.getNonAlcoholicCocktails()
           log("NonAlcoholicCocktailsDBDataSource", cocktails)
       }

       return cocktails
    }

    suspend fun getNonAlcoholicCocktailsById(cocktailsIds: List<String>): List<Cocktail> {
        val cocktails = getNonAlcoholicCocktails()

        return cocktails.filter { cocktailsIds.contains(it.id) }
    }

    private fun log(datasourceName: String, cocktails: List<Cocktail>) {
        logger.log("Retrieving from ${datasourceName}: ${cocktails.size} cocktails")
    }
}