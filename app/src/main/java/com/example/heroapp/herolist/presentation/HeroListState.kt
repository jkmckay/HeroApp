package com.example.heroapp.herolist.presentation

import com.example.heroapp.herolist.domain.model.HeroItem

sealed interface HeroListState {
    object Loading : HeroListState
    data class Success(val heroes: List<HeroItem>) : HeroListState
    object Empty : HeroListState
    object Error : HeroListState
}
