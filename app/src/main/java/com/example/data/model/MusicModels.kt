package com.example.data.model

enum class SeedType {
  SONG,
  ARTIST
}

enum class DiscoveryDepth(val label: String, val subtitle: String) {
  CLOSE_MATCH("Close Match", "Direct sonic neighbors & familiar styles"),
  BALANCED("Balanced", "Harmonious cross-genre discoveries"),
  DEEP_CUT("Deep Cut", "Underground gems & boundary pushers")
}

data class SonicProfile(
  val energy: Float,        // 0.0 (Calm/ambient) -> 1.0 (Driving/aggressive)
  val atmosphere: Float,    // 0.0 (Dry/close) -> 1.0 (Ethereal/reverberant)
  val groove: Float,        // 0.0 (Ambient/freeform) -> 1.0 (Bouncy/rhythmic)
  val acousticness: Float,  // 0.0 (Synthetic/electronic) -> 1.0 (Analog/organic)
  val vocalPresence: Float  // 0.0 (Instrumental/buried) -> 1.0 (Prominent/belting)
)

data class SampleTrack(
  val title: String,
  val description: String,
  val duration: String,
  val highlight: String
)

data class FavoriteSeed(
  val id: String,
  val type: SeedType,
  val title: String,
  val artist: String = "",
  val genre: String,
  val mood: String,
  val isActive: Boolean = true,
  val sonicProfile: SonicProfile = SonicProfile(0.6f, 0.6f, 0.6f, 0.5f, 0.7f)
) {
  val displayName: String
    get() = if (type == SeedType.SONG && artist.isNotBlank()) "$title by $artist" else title
}

data class RecommendedArtist(
  val id: String,
  val name: String,
  val matchScore: Int,
  val genres: List<String>,
  val vibeSummary: String,
  val whyRecommended: String,
  val matchedSeeds: List<String>,
  val topTracks: List<SampleTrack>,
  val sonicProfile: SonicProfile,
  val similarTo: List<String>,
  val origin: String,
  val tier: DiscoveryDepth,
  val isSavedToCrate: Boolean = false,
  val colorAccentHex: Long = 0xFF00E5C9
)

data class CuratedPreset(
  val id: String,
  val name: String,
  val tagLine: String,
  val genres: List<String>,
  val seeds: List<FavoriteSeed>
)
