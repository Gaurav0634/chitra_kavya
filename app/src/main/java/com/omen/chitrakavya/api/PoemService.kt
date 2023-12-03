package com.omen.chitrakavya.api


import com.omen.chitrakavya.models.PoemList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PoemService {

    @GET("author,title/{author};{title}")
    suspend fun getPoems(
        @Path("author") author: String,
        @Path("title") title: String
    ) : Response<PoemList>
}