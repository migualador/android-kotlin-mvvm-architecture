package com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migualador.cocktails.data.entities.Cocktail

/**
 * NonAlcoholicCocktailsDatabase
 *
 */
@Database(entities = [Cocktail::class], version = 1)
abstract class NonAlcoholicCocktailsDatabase : RoomDatabase() {

    abstract fun nonAlcoholicCocktailsDao(): NonAlcoholicCocktailsDAO

}