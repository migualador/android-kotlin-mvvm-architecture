package com.migualador.cocktails.environments

class ProductionEnvironment: Environment {
    override fun getAlcoholicCocktailsListUrl(): String = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Alcoholic"

    override fun getNonAlcoholicCocktailsListUrl(): String = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic"

    override fun getCocktailDetailUrl(cocktailId: String): String =
        "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=$cocktailId"
}