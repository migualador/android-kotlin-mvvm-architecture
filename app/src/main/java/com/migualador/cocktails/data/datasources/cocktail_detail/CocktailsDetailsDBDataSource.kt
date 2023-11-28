package com.migualador.cocktails.data.datasources.cocktail_detail

import android.content.Context
import androidx.room.Room
import com.migualador.cocktails.data.datasources.cocktail_detail.database.CocktailsDetailsDatabase
import com.migualador.cocktails.data.entities.CocktailDetail
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CocktailsDetailsDBDataSource
 *
 * Holds cocktails details information in Room database
 */
@Singleton
class CocktailsDetailsDBDataSource @Inject constructor(
    context: Context
) {

    val room: CocktailsDetailsDatabase = Room.databaseBuilder(
        context,
        CocktailsDetailsDatabase::class.java,
        "cocktails_details").
    build()

    fun getCocktailDetail(cocktailId: String): CocktailDetail? {
        val result = room.cocktailsDetailsDao().findCocktailDetailWithId(cocktailId)
        return if (result.isEmpty()) {
            null
        } else {
            result[0]
        }
    }

    fun addCocktailDetail(cocktailDetail: CocktailDetail) {
        room.cocktailsDetailsDao().insert(cocktailDetail)
    }

}