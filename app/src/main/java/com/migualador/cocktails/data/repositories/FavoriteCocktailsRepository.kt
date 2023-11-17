package com.migualador.cocktails.data.repositories

import com.migualador.cocktails.data.datasources.favorite_cocktails.FavoriteCocktailsDBDataSource
import com.migualador.cocktails.data.entities.FavoriteCocktail
import javax.inject.Inject

/**
 * FavoriteCocktailsRepository
 *
 * Holds favorite cocktails list
 */
class FavoriteCocktailsRepository @Inject constructor (
    private val favoriteCocktailsDBDataSource: FavoriteCocktailsDBDataSource
) {

    fun addFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        favoriteCocktailsDBDataSource.addFavoriteCocktail(favoriteCocktail)
    }

    fun removeFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        favoriteCocktailsDBDataSource.removeFavoriteCocktail(favoriteCocktail)
    }

    fun getFavoriteCocktailById(id: String): FavoriteCocktail? {
        return favoriteCocktailsDBDataSource.getFavoriteCocktailById(id)
    }

    fun getFavoriteCocktails(): List<FavoriteCocktail> {
        return favoriteCocktailsDBDataSource.getFavoriteCocktails()
    }
}