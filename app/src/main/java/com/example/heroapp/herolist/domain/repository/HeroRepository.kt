package com.example.heroapp.herolist.domain.repository

import com.example.heroapp.herolist.data.model.HeroResponseItem
import com.example.heroapp.herolist.data.service.HeroService
import com.example.heroapp.herolist.domain.model.HeroItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HeroRepository @Inject constructor(
    private val service: HeroService
) {
    private val _heroFlow = MutableStateFlow(Result.success(emptyList<HeroItem>()))

    val heroFlow: StateFlow<Result<List<HeroItem>>>
        get() = _heroFlow.asStateFlow()

    suspend fun getHeroes() {
        _heroFlow.value = try {
            Result.success(service.getHeroes().map(::mapToDomainModel))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mapToDomainModel(it: HeroResponseItem) =
        HeroItem(
            id = it.id,
            name = it.name,
            fullName = it.biography.fullName,
            listImage = it.images.sm,
            detailImage = it.images.lg,
            firstAppearance = it.biography.firstAppearance,
            publisher = it.biography.publisher
        )
}
