package com.example.unit4_pathway3_sportsapp.repository

import com.example.unit4_pathway3_sportsapp.data.LocalSportsDataProvider
import com.example.unit4_pathway3_sportsapp.model.Sport
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of SportsRepository using local data
 */
@Singleton
class LocalSportsRepository @Inject constructor() : SportsRepository {
    
    private val sportsData = LocalSportsDataProvider.getSportsData()
    
    override fun getSportsFlow(): Flow<List<Sport>> {
        return flowOf(sportsData)
    }
    
    override suspend fun getSports(): List<Sport> {
        return sportsData
    }
    
    override suspend fun getSportById(id: Int): Sport? {
        return sportsData.find { it.id == id }
    }
    
    override suspend fun getDefaultSport(): Sport {
        return sportsData.firstOrNull() ?: LocalSportsDataProvider.defaultSport
    }
    
    override suspend fun searchSports(query: String): List<Sport> {
        return sportsData.filter { sport ->
            // In a real app, you would resolve the string resources
            // For now, we'll just use the IDs for demonstration
            sport.titleResourceId.toString().contains(query, ignoreCase = true)
        }
    }
    
    override suspend fun filterSports(
        olympicOnly: Boolean?,
        minPlayerCount: Int?,
        maxPlayerCount: Int?
    ): List<Sport> {
        return sportsData.filter { sport ->
            val olympicMatch = olympicOnly?.let { sport.olympic == it } ?: true
            val minPlayerMatch = minPlayerCount?.let { sport.playerCount >= it } ?: true
            val maxPlayerMatch = maxPlayerCount?.let { sport.playerCount <= it } ?: true
            
            olympicMatch && minPlayerMatch && maxPlayerMatch
        }
    }
}