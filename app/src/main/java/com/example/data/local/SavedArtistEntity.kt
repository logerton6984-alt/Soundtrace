package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_artists")
data class SavedArtistEntity(
  @PrimaryKey val artistId: String,
  val name: String,
  val genres: String,
  val vibeSummary: String,
  val matchScore: Int,
  val notes: String = "",
  val isListened: Boolean = false,
  val savedAt: Long = System.currentTimeMillis()
)
