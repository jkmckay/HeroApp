package com.example.heroapp.herolist.data.service

import com.example.heroapp.herolist.data.model.HeroResponse
import retrofit2.http.GET

const val BASE_URL = "https://jkmckay.github.io/"

interface HeroService {

    @GET("superheroes.json")
    suspend fun getHeroes(): HeroResponse
}
