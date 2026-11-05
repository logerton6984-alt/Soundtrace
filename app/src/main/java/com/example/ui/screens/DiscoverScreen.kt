package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DiscoveryDepth
import com.example.data.model.FavoriteSeed
import com.example.data.model.RecommendedArtist
import com.example.ui.components.ActiveSeedsBar
import com.example.ui.components.ArtistRecommendationCard
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.NeonMint
import com.example.ui.theme.SunsetCoral
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.WarmGold

@Composable
fun DiscoverScreen(
  seeds: List<FavoriteSeed>,
  recommendations: List<RecommendedArtist>,
  discoveryDepth: DiscoveryDepth,
  selectedGenre: String,
  searchQuery: String,
  onDepthSelected: (DiscoveryDepth) -> Unit,
  onGenreSelected: (String) -> Unit,
  onSearchQueryChanged: (String) -> Unit,
  onAddSeedClick: () -> Unit,
  onPresetsClick: () -> Unit,
  onToggleSeedActive: (String, Boolean) -> Unit,
  onRemoveSeed: (String) -> Unit,
  onSelectArtist: (RecommendedArtist) -> Unit,
  onListenArtist: (RecommendedArtist) -> Unit,
  onSeedFromArtist: (RecommendedArtist) -> Unit,
  onToggleSaveArtist: (RecommendedArtist) -> Unit,
  modifier: Modifier = Modifier
) {
  val genres = listOf("All", "Dream Pop", "Shoegaze", "Indie Rock", "Neo-Soul", "Psychedelic", "Electronic", "Post-Punk", "Indie Folk")

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .testTag("discover_screen"),
    contentPadding = PaddingValues(bottom = 96.dp)
  ) {
    // Hero Banner & Branding Header
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
      ) {
        Image(
          painter = painterResource(id = R.drawable.soundtrace_banner),
          contentDescription = "SoundTrace Banner",
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )

        // Gradient overlay for seamless text blending
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              Brush.verticalGradient(
                colors = listOf(
                  Color.Transparent,
                  DarkBackground.copy(alpha = 0.65f),
                  DarkBackground
                )
              )
            )
        )

        Column(
          modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp),
          verticalArrangement = Arrangement.Bottom
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.GraphicEq,
              contentDescription = "SoundTrace",
              tint = NeonMint,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "SOUNDTRACE",
              style = MaterialTheme.typography.labelMedium,
              color = NeonMint,
              fontWeight = FontWeight.Bold,
              letterSpacing = 2.sp
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "Artist Discovery Engine",
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "Suggesting new sounds calibrated to your favorite music",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
          )
        }
      }
    }

    // Active Seeds Influences Row
    item {
      ActiveSeedsBar(
        seeds = seeds,
        onAddSeedClick = onAddSeedClick,
        onPresetsClick = onPresetsClick,
        onToggleActive = onToggleSeedActive,
        onRemoveSeed = onRemoveSeed,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
      )
    }

    // Discovery Depth Segmented Selector
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "DISCOVERY HORIZON",
            style = MaterialTheme.typography.labelSmall,
            color = TextMuted,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = discoveryDepth.subtitle,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = TextSecondary
          )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurface)
            .border(1.dp, DarkSurfaceBorder, RoundedCornerShape(14.dp))
            .padding(4.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          DiscoveryDepth.values().forEach { depth ->
            val isSelected = depth == discoveryDepth
            val accentColor = when (depth) {
              DiscoveryDepth.CLOSE_MATCH -> NeonMint
              DiscoveryDepth.BALANCED -> ElectricBlue
              DiscoveryDepth.DEEP_CUT -> CyberViolet
            }

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) accentColor.copy(alpha = 0.2f) else Color.Transparent,
              border = if (isSelected) BorderStroke(1.dp, accentColor) else null,
              modifier = Modifier
                .weight(1f)
                .clickable { onDepthSelected(depth) }
                .testTag("depth_button_${depth.name}")
            ) {
              Text(
                text = depth.label,
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) TextPrimary else TextMuted,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
              )
            }
          }
        }
      }
    }

    // Search and Genre Filter
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 6.dp)
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = onSearchQueryChanged,
          placeholder = { Text("Filter artists, genres, or keywords...", style = MaterialTheme.typography.bodyMedium, color = TextMuted) },
          leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = TextSecondary, modifier = Modifier.size(18.dp)) },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { onSearchQueryChanged("") }) {
                Icon(Icons.Default.Clear, contentDescription = "Clear search", tint = TextSecondary, modifier = Modifier.size(16.dp))
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(14.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = DarkSurface,
            unfocusedContainerColor = DarkSurface,
            focusedBorderColor = NeonMint,
            unfocusedBorderColor = DarkSurfaceBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary
          ),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("search_filter_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Genre filter chips
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(genres) { genre ->
            val isSelected = selectedGenre.equals(genre, ignoreCase = true)
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = if (isSelected) NeonMint.copy(alpha = 0.2f) else DarkSurface,
              border = BorderStroke(1.dp, if (isSelected) NeonMint else DarkSurfaceBorder),
              modifier = Modifier
                .clickable { onGenreSelected(genre) }
                .testTag("genre_chip_$genre")
            ) {
              Text(
                text = genre,
                style = MaterialTheme.typography.labelSmall,
                color = if (isSelected) NeonMint else TextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
    }

    // Results Header
    item {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "MATCHED ARTISTS (${recommendations.size})",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Text(
          text = "Ranked by Acoustic Proximity",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = NeonMint
        )
      }
    }

    // Empty State or List of Recommended Artists
    if (recommendations.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = DarkSurface),
          border = BorderStroke(1.dp, DarkSurfaceBorder),
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("empty_recommendations_card")
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(CyberViolet.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Hearing,
                contentDescription = "No matches",
                tint = CyberViolet,
                modifier = Modifier.size(32.dp)
              )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "No matching sounds found",
              style = MaterialTheme.typography.titleMedium,
              color = TextPrimary,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = if (seeds.none { it.isActive }) {
                "All your music seeds are currently paused. Turn on at least one seed or add a favorite song to activate the discovery radar."
              } else {
                "Try loosening your genre filter, clearing your search query, or switching to 'Deep Cut' horizon."
              },
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondary,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onAddSeedClick,
              colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = DarkBackground),
              shape = RoundedCornerShape(12.dp)
            ) {
              Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Add Favorite Song/Artist", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      items(recommendations, key = { it.id }) { artist ->
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
          ArtistRecommendationCard(
            artist = artist,
            onCardClick = { onSelectArtist(artist) },
            onListenClick = { onListenArtist(artist) },
            onSeedFromArtist = { onSeedFromArtist(artist) },
            onToggleSave = { onToggleSaveArtist(artist) }
          )
        }
      }
    }
  }
}
