package com.example.unit3_pathway3_materialtheming.data.repository

import com.example.unit3_pathway3_materialtheming.data.Dog
import com.example.unit3_pathway3_materialtheming.data.dogs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Repository interface for managing dog data.
 */
interface DogRepository {
    /**
     * Gets all dogs as a Flow for reactive data updates.
     */
    fun getDogs(): Flow<List<Dog>>
    
    /**
     * Gets a single dog by its position in the list.
     */
    suspend fun getDog(position: Int): Dog?
}

/**
 * Implementation of DogRepository that provides local dog data.
 */
class LocalDogRepository : DogRepository {
    
    override fun getDogs(): Flow<List<Dog>> {
        return flowOf(dogs)
    }
    
    override suspend fun getDog(position: Int): Dog? {
        return dogs.getOrNull(position)
    }
}