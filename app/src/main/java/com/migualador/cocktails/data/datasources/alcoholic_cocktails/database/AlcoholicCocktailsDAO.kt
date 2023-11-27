package com.migualador.cocktails.data.datasources.alcoholic_cocktails.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migualador.cocktails.data.entities.Cocktail

/**
 * AlcoholicCocktailsDAO
 *
 * DAO for alcoholic cocktails Room database
 */
@Dao
interface AlcoholicCocktailsDAO {

    @Query("SELECT * FROM Cocktail")
    fun getAll(): List<Cocktail>

    @Delete
    fun delete(cocktail: Cocktail)

    @Query("SELECT * FROM Cocktail WHERE id LIKE :search")
    fun findCocktailWithId(search: String): List<Cocktail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktails: List<Cocktail>)

}