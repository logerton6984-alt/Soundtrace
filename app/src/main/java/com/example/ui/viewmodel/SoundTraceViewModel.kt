package com.example.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.catalog.RecommendationEngine
import com.example.data.local.SavedArtistEntity
import com.example.data.model.CuratedPreset
import com.example.data.model.DiscoveryDepth
import com.example.data.model.FavoriteSeed
import com.example.data.model.RecommendedArtist
import com.example.data.model.SeedType
import com.example.data.model.SonicProfile
import com.example.data.repository.SoundTraceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class SoundTraceViewModel(private val repository: SoundTraceRepository) : ViewModel() {

  val favoriteSeeds: StateFlow<List<FavoriteSeed>> = repository.favoriteSeeds
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  val savedArtists: StateFlow<List<SavedArtistEntity>> = repository.savedArtists
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  private val _discoveryDepth = MutableStateFlow(DiscoveryDepth.BALANCED)
  val discoveryDepth: StateFlow<DiscoveryDepth> = _discoveryDepth.asStateFlow()

  private val _selectedGenre = MutableStateFlow("All")
  val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedArtistForDetail = MutableStateFlow<RecommendedArtist?>(null)
  val selectedArtistForDetail: StateFlow<RecommendedArtist?> = _selectedArtistForDetail.asStateFlow()

  private val _isAddSeedDialogOpen = MutableStateFlow(false)
  val isAddSeedDialogOpen: StateFlow<Boolean> = _isAddSeedDialogOpen.asStateFlow()

  private val _showPresetsSheet = MutableStateFlow(false)
  val showPresetsSheet: StateFlow<Boolean> = _showPresetsSheet.asStateFlow()

  private val _snackbarMessage = MutableStateFlow<String?>(null)
  val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

  init {
    viewModelScope.launch {
      favoriteSeeds.collect { currentSeeds ->
        if (currentSeeds.isEmpty()) {
          // Pre-populate with our default "Dream Pop & Shoegaze" starter seeds for instant delight
          val defaultPreset = MusicKnowledgeCatalog.curatedPresets.first()
          repository.addSeeds(defaultPreset.seeds)
        }
      }
    }
  }

  // Combined reactive recommendations feed
  val recommendedArtists: StateFlow<List<RecommendedArtist>> = combine(
    favoriteSeeds,
    savedArtists,
    discoveryDepth,
    selectedGenre,
    searchQuery
  ) { seeds, saved, depth, genre, query ->
    val savedIds = saved.map { it.artistId }.toSet()
    RecommendationEngine.recommend(
      seeds = seeds,
      discoveryDepth = depth,
      selectedGenreFilter = genre,
      searchQuery = query,
      savedArtistIds = savedIds
    )
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun setDiscoveryDepth(depth: DiscoveryDepth) {
    _discoveryDepth.value = depth
  }

  fun setSelectedGenre(genre: String) {
    _selectedGenre.value = genre
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun setAddSeedDialogOpen(isOpen: Boolean) {
    _isAddSeedDialogOpen.value = isOpen
  }

  fun setPresetsSheetOpen(isOpen: Boolean) {
    _showPresetsSheet.value = isOpen
  }

  fun selectArtistForDetail(artist: RecommendedArtist?) {
    _selectedArtistForDetail.value = artist
  }

  fun dismissSnackbar() {
    _snackbarMessage.value = null
  }

  fun addCustomSeed(
    title: String,
    artist: String,
    type: SeedType,
    genre: String,
    mood: String,
    energy: Float = 0.6f,
    atmosphere: Float = 0.7f,
    groove: Float = 0.6f,
    acousticness: Float = 0.4f,
    vocalPresence: Float = 0.7f
  ) {
    viewModelScope.launch {
      val newSeed = FavoriteSeed(
        id = "seed_${UUID.randomUUID().toString().take(8)}",
        type = type,
        title = title.trim(),
        artist = artist.trim(),
        genre = genre.ifBlank { "Indie / Alternative" },
        mood = mood.ifBlank { "Lush & Atmospheric" },
        isActive = true,
        sonicProfile = SonicProfile(energy, atmosphere, groove, acousticness, vocalPresence)
      )
      repository.addSeed(newSeed)
      _snackbarMessage.value = "Added \"${newSeed.displayName}\" to your seeds"
      _isAddSeedDialogOpen.value = false
    }
  }

  fun removeSeed(id: String) {
    viewModelScope.launch {
      repository.removeSeed(id)
      _snackbarMessage.value = "Seed removed"
    }
  }

  fun toggleSeedActive(id: String, isActive: Boolean) {
    viewModelScope.launch {
      repository.toggleSeedActive(id, isActive)
    }
  }

  fun clearSeeds() {
    viewModelScope.launch {
      repository.clearSeeds()
      _snackbarMessage.value = "All seeds cleared"
    }
  }

  fun loadPreset(preset: CuratedPreset) {
    viewModelScope.launch {
      repository.addSeeds(preset.seeds)
      _showPresetsSheet.value = false
      _snackbarMessage.value = "Loaded preset: ${preset.name}"
    }
  }

  fun seedFromArtist(artist: RecommendedArtist) {
    viewModelScope.launch {
      val seed = FavoriteSeed(
        id = "seed_${artist.id}",
        type = SeedType.ARTIST,
        title = artist.name,
        genre = artist.genres.firstOrNull() ?: "Indie",
        mood = artist.vibeSummary.take(24),
        isActive = true,
        sonicProfile = artist.sonicProfile
      )
      repository.addSeed(seed)
      _snackbarMessage.value = "Now seeding from ${artist.name}! Recommendations updated."
    }
  }

  fun toggleSaveArtist(artist: RecommendedArtist) {
    viewModelScope.launch {
      val isSaved = savedArtists.value.any { it.artistId == artist.id }
      if (isSaved) {
        repository.unsaveArtist(artist.id)
        _snackbarMessage.value = "Removed ${artist.name} from Crate"
      } else {
        val entity = SavedArtistEntity(
          artistId = artist.id,
          name = artist.name,
          genres = artist.genres.joinToString(", "),
          vibeSummary = artist.vibeSummary,
          matchScore = artist.matchScore
        )
        repository.saveArtist(entity)
        _snackbarMessage.value = "Saved ${artist.name} to Crate!"
      }
    }
  }

  fun toggleListened(artistId: String, isListened: Boolean) {
    viewModelScope.launch {
      repository.toggleListened(artistId, isListened)
    }
  }

  fun updateNotes(artistId: String, notes: String) {
    viewModelScope.launch {
      repository.updateNotes(artistId, notes)
    }
  }

  fun removeFromCrate(artistId: String) {
    viewModelScope.launch {
      repository.unsaveArtist(artistId)
    }
  }

  fun openListenIntent(context: Context, artistName: String, trackTitle: String? = null) {
    val query = if (!trackTitle.isNullOrBlank()) "$artistName $trackTitle" else artistName
    val intent = Intent(Intent.ACTION_VIEW).apply {
      data = Uri.parse("https://www.youtube.com/results?search_query=${Uri.encode(query)}")
    }
    try {
      context.startActivity(intent)
    } catch (_: Exception) {
      // Fallback to web search
      val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${Uri.encode("$query music")}"))
      context.startActivity(webIntent)
    }
  }
}

class SoundTraceViewModelFactory(private val repository: SoundTraceRepository) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SoundTraceViewModel::class.java)) {
      return SoundTraceViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
