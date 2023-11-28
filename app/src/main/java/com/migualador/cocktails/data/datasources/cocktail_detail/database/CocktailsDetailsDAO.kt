package com.migualador.cocktails.data.datasources.cocktail_detail.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.migualador.cocktails.data.entities.CocktailDetail

/**
 * CocktailsDetailsDAO
 *
 * DAO for cocktails details Room database
 */
@Dao
interface CocktailsDetailsDAO {

    @Query("SELECT * FROM CocktailDetail")
    fun getAll(): List<CocktailDetail>

    @Delete
    fun delete(cocktailDetail: CocktailDetail)

    @Query("SELECT * FROM CocktailDetail WHERE id LIKE :search")
    fun findCocktailDetailWithId(search: String): List<CocktailDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cocktailDetail: CocktailDetail)

}