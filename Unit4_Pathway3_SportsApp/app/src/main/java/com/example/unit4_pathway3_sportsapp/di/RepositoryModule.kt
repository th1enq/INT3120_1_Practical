package com.example.unit4_pathway3_sportsapp.di

import com.example.unit4_pathway3_sportsapp.repository.LocalSportsRepository
import com.example.unit4_pathway3_sportsapp.repository.SportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for repository dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindSportsRepository(
        localSportsRepository: LocalSportsRepository
    ): SportsRepository
}