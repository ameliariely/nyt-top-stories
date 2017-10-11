package com.ameliariely.newmacbookwhodis.util

import com.ameliariely.newmacbookwhodis.models.NYTResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NYTService {
    @GET("topstories/v2/{section}.json")
    fun storiesForSection(@Path("section") section: String): Call<NYTResult>
}


