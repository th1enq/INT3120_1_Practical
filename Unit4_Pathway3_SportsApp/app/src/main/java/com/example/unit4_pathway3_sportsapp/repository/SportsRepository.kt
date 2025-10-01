package com.example.unit4_pathway3_sportsapp.repository

import com.example.unit4_pathway3_sportsapp.model.Sport
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for sports data operations
 */
interface SportsRepository {
    /**
     * Get all sports as a Flow for reactive updates
     */
    fun getSportsFlow(): Flow<List<Sport>>
    
    /**
     * Get all sports synchronously
     */
    suspend fun getSports(): List<Sport>
    
    /**
     * Get a specific sport by ID
     */
    suspend fun getSportById(id: Int): Sport?
    
    /**
     * Get the default sport (typically the first one)
     */
    suspend fun getDefaultSport(): Sport
    
    /**
     * Search sports by title
     */
    suspend fun searchSports(query: String): List<Sport>
    
    /**
     * Filter sports by criteria
     */
    suspend fun filterSports(
        olympicOnly: Boolean? = null,
        minPlayerCount: Int? = null,
        maxPlayerCount: Int? = null
    ): List<Sport>
}