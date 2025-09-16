package com.migualador.cocktails.data.network.api

import com.migualador.cocktails.data.network.entities.GetCocktailDetailResponse
import com.migualador.cocktails.data.network.entities.GetCocktailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CocktailsAPI {

    @GET
    fun getAlcoholicCocktails(
        @Url url: String
    ): Call<GetCocktailsResponse>

    @GET
    fun getNonAlcoholicCocktails(
        @Url url: String
    ): Call<GetCocktailsResponse>

    @GET
    fun getCocktailDetail(
        @Url url: String,
    ): Call<GetCocktailDetailResponse>

}
