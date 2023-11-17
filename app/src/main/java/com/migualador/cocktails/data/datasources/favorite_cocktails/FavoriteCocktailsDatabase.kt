package com.migualador.cocktails.data.datasources.favorite_cocktails

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migualador.cocktails.data.entities.FavoriteCocktail

/**
 * FavoriteCocktailsDatabase
 *
 */
@Database(entities = [FavoriteCocktail::class], version = 1)
abstract class FavoriteCocktailsDatabase : RoomDatabase() {

    abstract fun favoriteCocktailsDao(): FavoriteCocktailsDAO

}