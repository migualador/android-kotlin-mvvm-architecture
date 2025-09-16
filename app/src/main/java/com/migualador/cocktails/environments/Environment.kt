package com.migualador.cocktails.environments

interface Environment {
    fun getAlcoholicCocktailsListUrl(): String
    fun getNonAlcoholicCocktailsListUrl(): String
    fun getCocktailDetailUrl(cocktailId: String): String
}