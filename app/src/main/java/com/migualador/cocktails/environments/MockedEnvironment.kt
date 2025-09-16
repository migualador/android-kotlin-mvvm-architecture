package com.migualador.cocktails.environments

class MockedEnvironment: Environment {
    override fun getAlcoholicCocktailsListUrl(): String = "https://gist.githubusercontent.com/migualador/2c0d5dacec5411b16263cb4a0216dcb0/raw/1b259c280994e048d49f3d99be76fccb342022c3/list_alcoholic_cocktails.json"

    override fun getNonAlcoholicCocktailsListUrl(): String = "https://gist.githubusercontent.com/migualador/50faf8945fb9dd3f39b58f2c38006350/raw/20d9ae8c8c89b7203781978c6dedc755a3a7f54a/list_non_alcoholic_cocktails.json"

    override fun getCocktailDetailUrl(cocktailId: String): String = "https://gist.githubusercontent.com/migualador/96dc4f60332fe7ab6c5bff5bbdeea8cd/raw/01b976d07a9d04565eb466e403718e2d0e17f8af/cocktail_detail.json"
}