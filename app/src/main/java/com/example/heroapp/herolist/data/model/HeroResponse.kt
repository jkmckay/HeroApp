package com.example.heroapp.herolist.data.model

import com.squareup.moshi.JsonClass

typealias HeroResponse = List<HeroResponseItem>

@JsonClass(generateAdapter = true)
data class HeroResponseItem(
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: Int,
    val images: Images,
    val name: String,
    val powerstats: Powerstats,
    val slug: String,
    val work: Work
) {
    @JsonClass(generateAdapter = true)
    data class Appearance(
        val eyeColor: String,
        val gender: String,
        val hairColor: String,
        val height: List<String>,
        val race: String?,
        val weight: List<String>
    )

    @JsonClass(generateAdapter = true)
    data class Biography(
        val aliases: List<String>,
        val alignment: String,
        val alterEgos: String,
        val firstAppearance: String,
        val fullName: String,
        val placeOfBirth: String,
        val publisher: String?
    )

    @JsonClass(generateAdapter = true)
    data class Connections(
        val groupAffiliation: String,
        val relatives: String
    )

    @JsonClass(generateAdapter = true)
    data class Images(
        val lg: String,
        val md: String,
        val sm: String,
        val xs: String
    )

    @JsonClass(generateAdapter = true)
    data class Powerstats(
        val combat: Int,
        val durability: Int,
        val intelligence: Int,
        val power: Int,
        val speed: Int,
        val strength: Int
    )

    @JsonClass(generateAdapter = true)
    data class Work(
        val base: String,
        val occupation: String
    )
}
