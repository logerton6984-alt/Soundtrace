package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.local.AppDatabase
import com.example.data.repository.SoundTraceRepository
import com.example.ui.components.AddSeedDialog
import com.example.ui.components.ArtistDetailBottomSheet
import com.example.ui.components.CuratedPresetsSheet
import com.example.ui.screens.CrateScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.SeedsManagerScreen
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonMint
import com.example.ui.theme.SunsetCoral
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.SoundTraceViewModel
import com.example.ui.viewmodel.SoundTraceViewModelFactory

enum class ScreenTab {
  DISCOVER,
  SEEDS,
  CRATE
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        SoundTraceApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoundTraceApp() {
  val context = LocalContext.current
  val database = remember { AppDatabase.getDatabase(context) }
  val repository = remember { SoundTraceRepository(database.soundTraceDao()) }
  val viewModel: SoundTraceViewModel = viewModel(factory = SoundTraceViewModelFactory(repository))

  var currentTab by remember { mutableStateOf(ScreenTab.DISCOVER) }

  val seeds by viewModel.favoriteSeeds.collectAsStateWithLifecycle()
  val savedArtists by viewModel.savedArtists.collectAsStateWithLifecycle()
  val recommendations by viewModel.recommendedArtists.collectAsStateWithLifecycle()
  val discoveryDepth by viewModel.discoveryDepth.collectAsStateWithLifecycle()
  val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()
  val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
  val selectedArtistForDetail by viewModel.selectedArtistForDetail.collectAsStateWithLifecycle()
  val isAddSeedDialogOpen by viewModel.isAddSeedDialogOpen.collectAsStateWithLifecycle()
  val showPresetsSheet by viewModel.showPresetsSheet.collectAsStateWithLifecycle()
  val snackbarMessage by viewModel.snackbarMessage.collectAsStateWithLifecycle()

  val snackbarHostState = remember { SnackbarHostState() }
  val detailSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val presetsSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  // Handle snackbar notifications
  LaunchedEffect(snackbarMessage) {
    snackbarMessage?.let {
      snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
      viewModel.dismissSnackbar()
    }
  }

  // Handle Back Button: return to Discover if on another tab
  BackHandler(enabled = currentTab != ScreenTab.DISCOVER) {
    currentTab = ScreenTab.DISCOVER
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = DarkBackground,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      NavigationBar(
        containerColor = DarkSurface,
        contentColor = TextPrimary,
        tonalElevation = 0.dp,
        modifier = Modifier
          .testTag("bottom_nav_bar")
          .windowInsetsPadding(WindowInsets.navigationBars)
      ) {
        NavigationBarItem(
          selected = currentTab == ScreenTab.DISCOVER,
          onClick = { currentTab = ScreenTab.DISCOVER },
          icon = {
            Icon(
              imageVector = if (currentTab == ScreenTab.DISCOVER) Icons.Default.Explore else Icons.Outlined.Explore,
              contentDescription = "Discover"
            )
          },
          label = { Text("Discover", fontWeight = if (currentTab == ScreenTab.DISCOVER) FontWeight.Bold else FontWeight.Normal) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = NeonMint,
            selectedTextColor = NeonMint,
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted,
            indicatorColor = NeonMint.copy(alpha = 0.15f)
          ),
          modifier = Modifier.testTag("nav_item_discover")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.SEEDS,
          onClick = { currentTab = ScreenTab.SEEDS },
          icon = {
            Icon(
              imageVector = if (currentTab == ScreenTab.SEEDS) Icons.Default.Spa else Icons.Outlined.Spa,
              contentDescription = "My Seeds"
            )
          },
          label = { Text("My Seeds (${seeds.count { it.isActive }})", fontWeight = if (currentTab == ScreenTab.SEEDS) FontWeight.Bold else FontWeight.Normal) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = CyberViolet,
            selectedTextColor = CyberViolet,
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted,
            indicatorColor = CyberViolet.copy(alpha = 0.15f)
          ),
          modifier = Modifier.testTag("nav_item_seeds")
        )

        NavigationBarItem(
          selected = currentTab == ScreenTab.CRATE,
          onClick = { currentTab = ScreenTab.CRATE },
          icon = {
            Icon(
              imageVector = if (currentTab == ScreenTab.CRATE) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
              contentDescription = "Crate"
            )
          },
          label = { Text("Crate (${savedArtists.size})", fontWeight = if (currentTab == ScreenTab.CRATE) FontWeight.Bold else FontWeight.Normal) },
          colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SunsetCoral,
            selectedTextColor = SunsetCoral,
            unselectedIconColor = TextMuted,
            unselectedTextColor = TextMuted,
            indicatorColor = SunsetCoral.copy(alpha = 0.15f)
          ),
          modifier = Modifier.testTag("nav_item_crate")
        )
      }
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentTab) {
        ScreenTab.DISCOVER -> {
          DiscoverScreen(
            seeds = seeds,
            recommendations = recommendations,
            discoveryDepth = discoveryDepth,
            selectedGenre = selectedGenre,
            searchQuery = searchQuery,
            onDepthSelected = { viewModel.setDiscoveryDepth(it) },
            onGenreSelected = { viewModel.setSelectedGenre(it) },
            onSearchQueryChanged = { viewModel.setSearchQuery(it) },
            onAddSeedClick = { viewModel.setAddSeedDialogOpen(true) },
            onPresetsClick = { viewModel.setPresetsSheetOpen(true) },
            onToggleSeedActive = { id, active -> viewModel.toggleSeedActive(id, active) },
            onRemoveSeed = { viewModel.removeSeed(it) },
            onSelectArtist = { viewModel.selectArtistForDetail(it) },
            onListenArtist = { artist -> viewModel.openListenIntent(context, artist.name) },
            onSeedFromArtist = { artist -> viewModel.seedFromArtist(artist) },
            onToggleSaveArtist = { artist -> viewModel.toggleSaveArtist(artist) }
          )
        }

        ScreenTab.SEEDS -> {
          SeedsManagerScreen(
            seeds = seeds,
            onAddSeedClick = { viewModel.setAddSeedDialogOpen(true) },
            onPresetsClick = { viewModel.setPresetsSheetOpen(true) },
            onToggleActive = { id, active -> viewModel.toggleSeedActive(id, active) },
            onRemoveSeed = { viewModel.removeSeed(it) },
            onClearAll = { viewModel.clearSeeds() }
          )
        }

        ScreenTab.CRATE -> {
          CrateScreen(
            savedArtists = savedArtists,
            onToggleListened = { id, listened -> viewModel.toggleListened(id, listened) },
            onUpdateNotes = { id, notes -> viewModel.updateNotes(id, notes) },
            onRemoveFromCrate = { viewModel.removeFromCrate(it) },
            onListenArtist = { name -> viewModel.openListenIntent(context, name) },
            onSeedFromArtistName = { name ->
              val matchInCatalog = MusicKnowledgeCatalog.catalogArtists.find { it.name.equals(name, ignoreCase = true) }
              if (matchInCatalog != null) {
                viewModel.seedFromArtist(
                  com.example.data.model.RecommendedArtist(
                    id = matchInCatalog.id,
                    name = matchInCatalog.name,
                    matchScore = 95,
                    genres = matchInCatalog.genres,
                    vibeSummary = matchInCatalog.vibeSummary,
                    whyRecommended = "Added from your Crate",
                    matchedSeeds = emptyList(),
                    topTracks = matchInCatalog.topTracks,
                    sonicProfile = matchInCatalog.sonicProfile,
                    similarTo = matchInCatalog.similarTo,
                    origin = matchInCatalog.origin,
                    tier = matchInCatalog.tier
                  )
                )
              } else {
                viewModel.addCustomSeed(
                  title = name,
                  artist = "",
                  type = com.example.data.model.SeedType.ARTIST,
                  genre = "Indie",
                  mood = "Personal Discovery"
                )
              }
            },
            onNavigateToDiscover = { currentTab = ScreenTab.DISCOVER }
          )
        }
      }
    }
  }

  // Add Seed Dialog
  if (isAddSeedDialogOpen) {
    AddSeedDialog(
      onDismiss = { viewModel.setAddSeedDialogOpen(false) },
      onAddSeed = { title, artist, type, genre, mood, energy, atmosphere, groove, acousticness, vocal ->
        viewModel.addCustomSeed(title, artist, type, genre, mood, energy, atmosphere, groove, acousticness, vocal)
      }
    )
  }

  // Presets Bottom Sheet
  if (showPresetsSheet) {
    CuratedPresetsSheet(
      sheetState = presetsSheetState,
      onDismiss = { viewModel.setPresetsSheetOpen(false) },
      onSelectPreset = { preset -> viewModel.loadPreset(preset) }
    )
  }

  // Artist Detail Bottom Sheet
  if (selectedArtistForDetail != null) {
    val artist = selectedArtistForDetail!!
    ArtistDetailBottomSheet(
      artist = artist,
      sheetState = detailSheetState,
      onDismiss = { viewModel.selectArtistForDetail(null) },
      onListenTrack = { trackTitle ->
        viewModel.openListenIntent(context, artist.name, trackTitle.ifBlank { null })
      },
      onSeedFromArtist = {
        viewModel.seedFromArtist(artist)
        viewModel.selectArtistForDetail(null)
      },
      onToggleSave = {
        viewModel.toggleSaveArtist(artist)
      }
    )
  }
}
