package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SoundTraceDao {
  @Query("SELECT * FROM favorite_seeds ORDER BY createdAt DESC")
  fun getFavoriteSeeds(): Flow<List<FavoriteSeedEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSeed(seed: FavoriteSeedEntity)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSeeds(seeds: List<FavoriteSeedEntity>)

  @Query("DELETE FROM favorite_seeds WHERE id = :id")
  suspend fun deleteSeed(id: String)

  @Query("UPDATE favorite_seeds SET isActive = :isActive WHERE id = :id")
  suspend fun updateSeedActive(id: String, isActive: Boolean)

  @Query("DELETE FROM favorite_seeds")
  suspend fun clearAllSeeds()

  @Query("SELECT * FROM saved_artists ORDER BY savedAt DESC")
  fun getSavedArtists(): Flow<List<SavedArtistEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertSavedArtist(artist: SavedArtistEntity)

  @Query("DELETE FROM saved_artists WHERE artistId = :artistId")
  suspend fun deleteSavedArtist(artistId: String)

  @Query("UPDATE saved_artists SET isListened = :isListened WHERE artistId = :artistId")
  suspend fun updateListenedStatus(artistId: String, isListened: Boolean)

  @Query("UPDATE saved_artists SET notes = :notes WHERE artistId = :artistId")
  suspend fun updateNotes(artistId: String, notes: String)
}
