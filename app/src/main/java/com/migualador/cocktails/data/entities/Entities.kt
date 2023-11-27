package com.migualador.cocktails.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cocktail
 *
 * Information of a cocktail (for both data sources and Room)
 */
@Entity
data class Cocktail(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val imageUrl: String
)

/**
 * CocktailDetail
 *
 * Information of a cocktail detail (for both data sources and Room)
 */
@Entity
data class CocktailDetail(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String,
    @ColumnInfo val alcoholic: String,
    @ColumnInfo val category: String,
    @ColumnInfo val imageUrl: String,
    @ColumnInfo val glassType: String,
    @ColumnInfo val ingredient1: String,
    @ColumnInfo val ingredient2: String,
    @ColumnInfo val ingredient3: String,
    @ColumnInfo val ingredient4: String,
    @ColumnInfo val ingredient5: String,
    @ColumnInfo val ingredient6: String,
    @ColumnInfo val ingredient7: String,
    @ColumnInfo val ingredient8: String,
    @ColumnInfo val ingredient9: String,
    @ColumnInfo val ingredient10: String,
    @ColumnInfo val ingredient11: String,
    @ColumnInfo val ingredient12: String,
    @ColumnInfo val ingredient13: String,
    @ColumnInfo val ingredient14: String,
    @ColumnInfo val ingredient15: String,
    @ColumnInfo val measure1: String,
    @ColumnInfo val measure2: String,
    @ColumnInfo val measure3: String,
    @ColumnInfo val measure4: String,
    @ColumnInfo val measure5: String,
    @ColumnInfo val measure6: String,
    @ColumnInfo val measure7: String,
    @ColumnInfo val measure8: String,
    @ColumnInfo val measure9: String,
    @ColumnInfo val measure10: String,
    @ColumnInfo val measure11: String,
    @ColumnInfo val measure12: String,
    @ColumnInfo val measure13: String,
    @ColumnInfo val measure14: String,
    @ColumnInfo val measure15: String,
    @ColumnInfo val instructions: String
)

/**
 * FavoriteCocktail
 *
 * Information of a favorite cocktail (for Room)
 */
@Entity
data class FavoriteCocktail(
    @PrimaryKey var cocktailId: String,
)