package com.migualador.cocktails.data.datasources.favorite_cocktails

import android.content.Context
import androidx.room.Room
import com.migualador.cocktails.data.entities.FavoriteCocktail
import javax.inject.Inject

/**
 * FavoriteCocktailsLocalDataSource
 *
 * Holds list of favorite cocktails in a Room database
 */
class FavoriteCocktailsDBDataSource @Inject constructor(
    context: Context
) {

    private val room: FavoriteCocktailsDatabase = Room.databaseBuilder(
        context,
        FavoriteCocktailsDatabase::class.java,
        "favorite_cocktails").
    build()

    fun getFavoriteCocktails(): List<FavoriteCocktail> {
        return room.favoriteCocktailsDao().getAll()
    }

    fun getFavoriteCocktailById(id: String): FavoriteCocktail? {
        val result = room.favoriteCocktailsDao().findCocktailWithId(id)
        return if (result.isNotEmpty()) {
            result[0]
        } else null
    }

    fun addFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        room.favoriteCocktailsDao().insertAll(listOf(favoriteCocktail))
    }

    fun removeFavoriteCocktail(favoriteCocktail: FavoriteCocktail) {
        room.favoriteCocktailsDao().delete(favoriteCocktail)
    }
}