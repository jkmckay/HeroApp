package com.example.heroapp.heroDetail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.heroapp.NavArguments.HERO_ARG
import com.example.heroapp.herolist.domain.repository.HeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeroDetailViewModel @Inject constructor(
    private val savedState: SavedStateHandle,
    private val repo: HeroRepository
) : ViewModel() {
    private val _state = MutableStateFlow<HeroDetailState>(HeroDetailState.Loading)
    val state: StateFlow<HeroDetailState>
        get() = _state

    init {
        setContent()
    }

    private fun setContent() {
        viewModelScope.launch {
            _state.value = HeroDetailState.Loading
            repo.heroFlow.collect { result ->
                result.fold(
                    { list ->
                        savedState.get<Int>(HERO_ARG)?.let { heroId ->
                            list.find { it.id == heroId }?.let {
                                _state.value = HeroDetailState.Success(it)
                            }
                        }
                    }, {
                    _state.value = HeroDetailState.Error
                }
                )
            }
        }
    }
}
