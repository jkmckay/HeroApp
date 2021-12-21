package com.example.heroapp.heroDetail.presentation

import com.example.heroapp.herolist.domain.model.HeroItem

sealed interface HeroDetailState {
    object Loading : HeroDetailState
    data class Success(val hero: HeroItem) : HeroDetailState
    object Error : HeroDetailState
}
