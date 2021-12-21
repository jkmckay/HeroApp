package com.example.heroapp.di

import com.example.heroapp.herolist.data.service.HeroService
import com.example.heroapp.herolist.domain.repository.HeroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HeroModule {

    @Singleton
    @Provides
    fun provideHeroRepository(service: HeroService) = HeroRepository(service)
}
