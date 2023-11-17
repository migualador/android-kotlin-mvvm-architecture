package com.migualador.cocktails.data.network.api

import com.migualador.cocktails.data.network.entities.GetCocktailDetailResponse
import com.migualador.cocktails.data.network.entities.GetCocktailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailsAPI {

    @GET("{apiKey}/filter.php?a=Alcoholic")
    fun getAlcoholicCocktails(
        @Path("apiKey") apiKey: String
    ): Call<GetCocktailsResponse>

    @GET("{apiKey}/filter.php?a=Non_Alcoholic")
    fun getNonAlcoholicCocktails(
        @Path("apiKey") apiKey: String
    ): Call<GetCocktailsResponse>

    @GET("{apiKey}/lookup.php")
    fun getCocktailDetail(
        @Path("apiKey") apiKey: String,
        @Query("i") cocktailId: String
    ): Call<GetCocktailDetailResponse>

}
