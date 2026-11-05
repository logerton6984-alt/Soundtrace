package com.example.data.repository

import com.example.data.local.FavoriteSeedEntity
import com.example.data.local.SavedArtistEntity
import com.example.data.local.SoundTraceDao
import com.example.data.model.FavoriteSeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SoundTraceRepository(private val dao: SoundTraceDao) {

  val favoriteSeeds: Flow<List<FavoriteSeed>> = dao.getFavoriteSeeds().map { entities ->
    entities.map { it.toDomain() }
  }

  val savedArtists: Flow<List<SavedArtistEntity>> = dao.getSavedArtists()

  suspend fun addSeed(seed: FavoriteSeed) {
    dao.insertSeed(FavoriteSeedEntity.fromDomain(seed))
  }

  suspend fun addSeeds(seeds: List<FavoriteSeed>) {
    dao.insertSeeds(seeds.map { FavoriteSeedEntity.fromDomain(it) })
  }

  suspend fun removeSeed(id: String) {
    dao.deleteSeed(id)
  }

  suspend fun toggleSeedActive(id: String, isActive: Boolean) {
    dao.updateSeedActive(id, isActive)
  }

  suspend fun clearSeeds() {
    dao.clearAllSeeds()
  }

  suspend fun saveArtist(artist: SavedArtistEntity) {
    dao.insertSavedArtist(artist)
  }

  suspend fun unsaveArtist(artistId: String) {
    dao.deleteSavedArtist(artistId)
  }

  suspend fun toggleListened(artistId: String, isListened: Boolean) {
    dao.updateListenedStatus(artistId, isListened)
  }

  suspend fun updateNotes(artistId: String, notes: String) {
    dao.updateNotes(artistId, notes)
  }
}
