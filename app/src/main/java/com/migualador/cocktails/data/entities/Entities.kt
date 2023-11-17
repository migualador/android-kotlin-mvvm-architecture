package com.migualador.cocktails.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cocktail
 *
 * Information of a cocktail
 */
data class Cocktail(
    val id: String,
    val name: String,
    val imageUrl: String
)

/**
 * CocktailDetail
 *
 * Information of a cocktail detail
 */
data class CocktailDetail(
    val id: String,
    val name: String,
    val alcoholic: String,
    val category: String,
    val imageUrl: String,
    val glassType: String,
    val ingredient1: String,
    val ingredient2: String,
    val ingredient3: String,
    val ingredient4: String,
    val ingredient5: String,
    val ingredient6: String,
    val ingredient7: String,
    val ingredient8: String,
    val ingredient9: String,
    val ingredient10: String,
    val ingredient11: String,
    val ingredient12: String,
    val ingredient13: String,
    val ingredient14: String,
    val ingredient15: String,
    val measure1: String,
    val measure2: String,
    val measure3: String,
    val measure4: String,
    val measure5: String,
    val measure6: String,
    val measure7: String,
    val measure8: String,
    val measure9: String,
    val measure10: String,
    val measure11: String,
    val measure12: String,
    val measure13: String,
    val measure14: String,
    val measure15: String,
    val instructions: String
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