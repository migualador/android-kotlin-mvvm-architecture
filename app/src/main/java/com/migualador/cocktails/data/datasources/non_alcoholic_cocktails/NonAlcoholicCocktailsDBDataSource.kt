package com.migualador.cocktails.data.datasources.non_alcoholic_cocktails

import android.content.Context
import androidx.room.Room
import com.migualador.cocktails.data.datasources.non_alcoholic_cocktails.database.NonAlcoholicCocktailsDatabase
import com.migualador.cocktails.data.entities.Cocktail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * NonAlcoholicCocktailsDBDataSource
 *
 * Holds non alcoholic cocktails information in Room database
 */
@Singleton
class NonAlcoholicCocktailsDBDataSource @Inject constructor(
    context: Context
) {

    val room: NonAlcoholicCocktailsDatabase = Room.databaseBuilder(
        context,
        NonAlcoholicCocktailsDatabase::class.java,
        "non_alcoholic_cocktails").
    build()

    fun getNonAlcoholicCocktails(): List<Cocktail> {
        return room.nonAlcoholicCocktailsDao().getAll()
    }

    fun setNonAlcoholicCocktails(cocktails: List<Cocktail>) {
        room.nonAlcoholicCocktailsDao().insert(cocktails)
    }

}