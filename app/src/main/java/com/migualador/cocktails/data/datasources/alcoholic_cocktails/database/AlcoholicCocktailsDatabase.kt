package com.migualador.cocktails.data.datasources.alcoholic_cocktails.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migualador.cocktails.data.entities.Cocktail

/**
 * AlcoholicCocktailsDatabase
 *
 */
@Database(entities = [Cocktail::class], version = 1)
abstract class AlcoholicCocktailsDatabase : RoomDatabase() {

    abstract fun alcoholicCocktailsDao(): AlcoholicCocktailsDAO

}