package com.example.heroapp.herolist.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.herolist.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroListViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val repo: HeroRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HeroListState>(HeroListState.Loading)
    val state: StateFlow<HeroListState>
        get() = _state

    init {
        getHeroes()
        viewModelScope.launch {
            repo.heroFlow.collect {
                it.fold(
                    { heroes -> _state.value = HeroListState.Success(heroes) },
                    { _state.value = HeroListState.Error }
                )
            }
        }
    }

    fun getHeroes() {
        viewModelScope.launch {
            _state.value = HeroListState.Loading
            repo.getHeroes()
        }
    }
}
