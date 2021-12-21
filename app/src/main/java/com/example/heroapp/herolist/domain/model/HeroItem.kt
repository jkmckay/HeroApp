package com.example.heroapp.herolist.domain.model

data class HeroItem(
    val id: Int,
    val listImage: String,
    val detailImage: String,
    val name: String,
    val fullName: String,
    val publisher: String?,
    val firstAppearance: String
)
