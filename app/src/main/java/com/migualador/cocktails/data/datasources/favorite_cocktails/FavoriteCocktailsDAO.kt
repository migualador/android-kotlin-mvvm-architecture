package com.migualador.cocktails.data.datasources.favorite_cocktails

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migualador.cocktails.data.entities.FavoriteCocktail

/**
 * FavoriteCocktailsDAO
 *
 * DAO for favorite cocktails Room database
 */
@Dao
interface FavoriteCocktailsDAO {

    @Query("SELECT * FROM FavoriteCocktail")
    fun getAll(): List<FavoriteCocktail>

    @Delete
    fun delete(favoriteCocktail: FavoriteCocktail)

    @Query("SELECT * FROM FavoriteCocktail WHERE cocktailId LIKE :search")
    fun findCocktailWithId(search: String): List<FavoriteCocktail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(favoriteCocktails: List<FavoriteCocktail>)


}