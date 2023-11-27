package com.migualador.cocktails.data.datasources.alcoholic_cocktails

import android.content.Context
import androidx.room.Room
import com.migualador.cocktails.data.datasources.alcoholic_cocktails.database.AlcoholicCocktailsDatabase
import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * AlcoholicCocktailsDBDataSource
 *
 * Holds alcoholic cocktails information in Room database
 */
@Singleton
class AlcoholicCocktailsDBDataSource @Inject constructor(
    context: Context
) {

    val room: AlcoholicCocktailsDatabase = Room.databaseBuilder(
        context,
        AlcoholicCocktailsDatabase::class.java,
        "alcoholic_cocktails").
    build()

    fun getAlcoholicCocktails(): List<Cocktail> {
        return room.alcoholicCocktailsDao().getAll()
    }

    fun setAlcoholicCocktails(cocktails: List<Cocktail>) {
        room.alcoholicCocktailsDao().insert(cocktails)
    }

}