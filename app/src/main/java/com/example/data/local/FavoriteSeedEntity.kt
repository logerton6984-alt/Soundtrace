package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.FavoriteSeed
import com.example.data.model.SeedType
import com.example.data.model.SonicProfile

@Entity(tableName = "favorite_seeds")
data class FavoriteSeedEntity(
  @PrimaryKey val id: String,
  val type: String,
  val title: String,
  val artist: String,
  val genre: String,
  val mood: String,
  val isActive: Boolean,
  val energy: Float,
  val atmosphere: Float,
  val groove: Float,
  val acousticness: Float,
  val vocalPresence: Float,
  val createdAt: Long = System.currentTimeMillis()
) {
  fun toDomain(): FavoriteSeed {
    return FavoriteSeed(
      id = id,
      type = if (type == "SONG") SeedType.SONG else SeedType.ARTIST,
      title = title,
      artist = artist,
      genre = genre,
      mood = mood,
      isActive = isActive,
      sonicProfile = SonicProfile(
        energy = energy,
        atmosphere = atmosphere,
        groove = groove,
        acousticness = acousticness,
        vocalPresence = vocalPresence
      )
    )
  }

  companion object {
    fun fromDomain(seed: FavoriteSeed): FavoriteSeedEntity {
      return FavoriteSeedEntity(
        id = seed.id,
        type = seed.type.name,
        title = seed.title,
        artist = seed.artist,
        genre = seed.genre,
        mood = seed.mood,
        isActive = seed.isActive,
        energy = seed.sonicProfile.energy,
        atmosphere = seed.sonicProfile.atmosphere,
        groove = seed.sonicProfile.groove,
        acousticness = seed.sonicProfile.acousticness,
        vocalPresence = seed.sonicProfile.vocalPresence
      )
    }
  }
}
