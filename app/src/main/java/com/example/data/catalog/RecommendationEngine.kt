package com.example.data.catalog

import com.example.data.model.DiscoveryDepth
import com.example.data.model.FavoriteSeed
import com.example.data.model.RecommendedArtist
import com.example.data.model.SeedType
import com.example.data.model.SonicProfile
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

object RecommendationEngine {

  fun recommend(
    seeds: List<FavoriteSeed>,
    discoveryDepth: DiscoveryDepth = DiscoveryDepth.BALANCED,
    selectedGenreFilter: String? = null,
    searchQuery: String = "",
    savedArtistIds: Set<String> = emptySet()
  ): List<RecommendedArtist> {
    val activeSeeds = seeds.filter { it.isActive }
    if (activeSeeds.isEmpty()) return emptyList()

    // Calculate composite user profile from active seeds
    val avgEnergy = activeSeeds.map { it.sonicProfile.energy }.average().toFloat()
    val avgAtmosphere = activeSeeds.map { it.sonicProfile.atmosphere }.average().toFloat()
    val avgGroove = activeSeeds.map { it.sonicProfile.groove }.average().toFloat()
    val avgAcousticness = activeSeeds.map { it.sonicProfile.acousticness }.average().toFloat()
    val avgVocal = activeSeeds.map { it.sonicProfile.vocalPresence }.average().toFloat()
    val targetProfile = SonicProfile(avgEnergy, avgAtmosphere, avgGroove, avgAcousticness, avgVocal)

    val activeSeedNames = activeSeeds.map { it.title.lowercase().trim() }.toSet()

    return MusicKnowledgeCatalog.catalogArtists
      .filter { artist ->
        // Do not recommend the exact same artist if already seeded
        !activeSeedNames.contains(artist.name.lowercase().trim())
      }
      .filter { artist ->
        // Genre filter if applied
        if (selectedGenreFilter.isNullOrBlank() || selectedGenreFilter == "All") {
          true
        } else {
          artist.genres.any { it.contains(selectedGenreFilter, ignoreCase = true) }
        }
      }
      .filter { artist ->
        // Search query if applied
        if (searchQuery.isBlank()) {
          true
        } else {
          val query = searchQuery.trim().lowercase()
          artist.name.lowercase().contains(query) ||
            artist.genres.any { it.lowercase().contains(query) } ||
            artist.vibeSummary.lowercase().contains(query) ||
            artist.topTracks.any { it.title.lowercase().contains(query) }
        }
      }
      .map { artist ->
        // Find the single seed that has highest affinity with this artist
        val bestSeedMatch = activeSeeds.maxByOrNull { seed ->
          calculatePairAffinity(seed, artist)
        } ?: activeSeeds.first()

        val sonicDistance = calculateSonicDistance(artist.sonicProfile, targetProfile)
        val genreScore = calculateGenreAffinity(activeSeeds, artist.genres)

        // Depth modifier
        val depthBonus = when (discoveryDepth) {
          DiscoveryDepth.CLOSE_MATCH -> if (artist.tier == DiscoveryDepth.CLOSE_MATCH) 10f else -6f
          DiscoveryDepth.BALANCED -> if (artist.tier == DiscoveryDepth.BALANCED) 8f else 2f
          DiscoveryDepth.DEEP_CUT -> if (artist.tier == DiscoveryDepth.DEEP_CUT) 12f else -4f
        }

        // Base score from sonic proximity (0..1) + genre affinity (0..1)
        val baseScore = (1f - sonicDistance) * 55f + (genreScore * 35f) + depthBonus
        val clampedScore = max(72, min(98, baseScore.roundToInt()))

        val why = generateWhyRecommended(bestSeedMatch, artist)

        RecommendedArtist(
          id = artist.id,
          name = artist.name,
          matchScore = clampedScore,
          genres = artist.genres,
          vibeSummary = artist.vibeSummary,
          whyRecommended = why,
          matchedSeeds = listOf(bestSeedMatch.displayName),
          topTracks = artist.topTracks,
          sonicProfile = artist.sonicProfile,
          similarTo = artist.similarTo,
          origin = artist.origin,
          tier = artist.tier,
          isSavedToCrate = savedArtistIds.contains(artist.id),
          colorAccentHex = artist.accentColorHex
        )
      }
      .sortedByDescending { it.matchScore }
  }

  private fun calculateSonicDistance(p1: SonicProfile, p2: SonicProfile): Float {
    val dEnergy = abs(p1.energy - p2.energy)
    val dAtmosphere = abs(p1.atmosphere - p2.atmosphere)
    val dGroove = abs(p1.groove - p2.groove)
    val dAcoustic = abs(p1.acousticness - p2.acousticness)
    val dVocal = abs(p1.vocalPresence - p2.vocalPresence)

    return (dEnergy * 0.22f + dAtmosphere * 0.28f + dGroove * 0.22f + dAcoustic * 0.14f + dVocal * 0.14f)
  }

  private fun calculatePairAffinity(seed: FavoriteSeed, artist: CatalogArtist): Float {
    var score = 0f
    // Similar to explicit list
    if (artist.similarTo.any { it.equals(seed.title, ignoreCase = true) || it.equals(seed.artist, ignoreCase = true) }) {
      score += 0.5f
    }
    // Genre match
    if (artist.genres.any { it.contains(seed.genre, ignoreCase = true) || seed.genre.contains(it, ignoreCase = true) }) {
      score += 0.35f
    }
    // Sonic closeness
    val dist = calculateSonicDistance(seed.sonicProfile, artist.sonicProfile)
    score += (1f - dist) * 0.35f
    return score
  }

  private fun calculateGenreAffinity(seeds: List<FavoriteSeed>, artistGenres: List<String>): Float {
    val seedGenres = seeds.map { it.genre.lowercase() }
    var highestAffinity = 0.3f

    for (ag in artistGenres) {
      val agLower = ag.lowercase()
      for (sg in seedGenres) {
        if (agLower.contains(sg) || sg.contains(agLower)) {
          return 1.0f
        }
        // Subgenre kinship matrix
        if (areGenresRelated(agLower, sg)) {
          highestAffinity = max(highestAffinity, 0.8f)
        }
      }
    }
    return highestAffinity
  }

  private fun areGenresRelated(g1: String, g2: String): Boolean {
    val families = listOf(
      setOf("dream pop", "shoegaze", "indie pop", "indie rock", "chillwave"),
      setOf("psychedelic pop", "psych", "funk", "indie soul", "synthpop"),
      setOf("neo-soul", "contemporary r&b", "r&b", "alt-r&b", "soul", "jazz"),
      setOf("uk garage", "electronic", "idm", "breakbeat", "trip-hop", "ambient"),
      setOf("post-punk", "art rock", "alternative rock", "gothic rock", "grunge"),
      setOf("indie folk", "slowcore", "chamber folk", "country", "alt-country")
    )
    return families.any { family ->
      family.any { g1.contains(it) } && family.any { g2.contains(it) }
    }
  }

  private fun generateWhyRecommended(seed: FavoriteSeed, artist: CatalogArtist): String {
    val seedSubject = if (seed.type == SeedType.SONG) {
      if (seed.artist.isNotBlank()) "\"${seed.title}\" by ${seed.artist}" else "\"${seed.title}\""
    } else {
      seed.title
    }

    val dominantTrait = when {
      artist.sonicProfile.atmosphere > 0.82f -> "reverberant atmospheric depth"
      artist.sonicProfile.groove > 0.8f -> "hypnotic syncopated rhythm section"
      artist.sonicProfile.energy > 0.75f -> "driving emotional tension and urgency"
      artist.sonicProfile.acousticness > 0.65f -> "warm acoustic craftsmanship"
      else -> "lush sonic production"
    }

    return "Recommended from your love for $seedSubject — shares matching $dominantTrait and ${artist.genres.firstOrNull() ?: "adjacent"} sensibilities."
  }
}
