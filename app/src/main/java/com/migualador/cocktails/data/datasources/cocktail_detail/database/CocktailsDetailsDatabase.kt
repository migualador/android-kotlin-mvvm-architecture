package com.migualador.cocktails.data.datasources.cocktail_detail.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.migualador.cocktails.data.entities.CocktailDetail

/**
 * CocktailsDetailsDatabase
 *
 */
@Database(entities = [CocktailDetail::class], version = 1)
abstract class CocktailsDetailsDatabase : RoomDatabase() {

    abstract fun cocktailsDetailsDao(): CocktailsDetailsDAO

}