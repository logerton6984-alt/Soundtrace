package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.local.SavedArtistEntity
import com.example.data.model.RecommendedArtist
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
fun CrateScreen(
  savedArtists: List<SavedArtistEntity>,
  onToggleListened: (String, Boolean) -> Unit,
  onUpdateNotes: (String, String) -> Unit,
  onRemoveFromCrate: (String) -> Unit,
  onListenArtist: (String) -> Unit,
  onSeedFromArtistName: (String) -> Unit,
  onNavigateToDiscover: () -> Unit,
  modifier: Modifier = Modifier
) {
  var filterListened by remember { mutableStateOf<Boolean?>(null) }
  var editingNotesArtist by remember { mutableStateOf<SavedArtistEntity?>(null) }
  var noteText by remember { mutableStateOf("") }

  val filteredArtists = when (filterListened) {
    true -> savedArtists.filter { it.isListened }
    false -> savedArtists.filter { !it.isListened }
    null -> savedArtists
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .testTag("crate_screen"),
    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 96.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Bookmark,
              contentDescription = "Crate",
              tint = SunsetCoral,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "SAVED DISCOVERIES",
              style = MaterialTheme.typography.labelSmall,
              color = SunsetCoral,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "My Vinyl Crate",
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = DarkSurfaceVariant
        ) {
          Text(
            text = "${savedArtists.size} Saved",
            style = MaterialTheme.typography.labelMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // Filter Chips: All, Want to Listen, Listened
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterPill(label = "All (${savedArtists.size})", isSelected = filterListened == null, onClick = { filterListened = null })
        FilterPill(label = "Want to Listen (${savedArtists.count { !it.isListened }})", isSelected = filterListened == false, onClick = { filterListened = false })
        FilterPill(label = "Listened (${savedArtists.count { it.isListened }})", isSelected = filterListened == true, onClick = { filterListened = true })
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Empty state or Saved list
    if (filteredArtists.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = DarkSurface),
          border = BorderStroke(1.dp, DarkSurfaceBorder),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
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
                .background(SunsetCoral.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Headphones,
                contentDescription = "Empty Crate",
                tint = SunsetCoral,
                modifier = Modifier.size(30.dp)
              )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
              text = if (savedArtists.isEmpty()) "Your Crate is empty" else "No artists in this filter",
              style = MaterialTheme.typography.titleMedium,
              color = TextPrimary,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = if (savedArtists.isEmpty()) {
                "Bookmark recommended artists in the Discover tab to build your personal listening queue."
              } else {
                "Switch back to 'All' or explore new sounds in the Discover feed."
              },
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondary,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onNavigateToDiscover,
              colors = ButtonDefaults.buttonColors(containerColor = SunsetCoral, contentColor = TextPrimary),
              shape = RoundedCornerShape(12.dp)
            ) {
              Text("Explore Discover Feed", fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    } else {
      items(filteredArtists, key = { it.artistId }) { artist ->
        SavedArtistCard(
          artist = artist,
          onToggleListened = { onToggleListened(artist.artistId, !artist.isListened) },
          onEditNotes = {
            editingNotesArtist = artist
            noteText = artist.notes
          },
          onRemove = { onRemoveFromCrate(artist.artistId) },
          onListen = { onListenArtist(artist.name) },
          onSeedFrom = { onSeedFromArtistName(artist.name) }
        )
        Spacer(modifier = Modifier.height(10.dp))
      }
    }
  }

  // Edit Notes Dialog
  if (editingNotesArtist != null) {
    val artist = editingNotesArtist!!
    AlertDialog(
      onDismissRequest = { editingNotesArtist = null },
      containerColor = DarkSurface,
      shape = RoundedCornerShape(20.dp),
      title = {
        Text("Listening Notes: ${artist.name}", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
      },
      text = {
        Column {
          Text(
            text = "Record your thoughts, favorite tracks, or sonic impressions.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            placeholder = { Text("e.g. Loved their 2023 record! Insane synthesizer textures and bassline on track 2.", color = TextMuted) },
            minLines = 3,
            maxLines = 5,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("crate_note_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = SunsetCoral,
              unfocusedBorderColor = DarkSurfaceBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary
            )
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            onUpdateNotes(artist.artistId, noteText)
            editingNotesArtist = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = SunsetCoral, contentColor = TextPrimary)
        ) {
          Text("Save Note")
        }
      },
      dismissButton = {
        TextButton(onClick = { editingNotesArtist = null }) {
          Text("Cancel", color = TextSecondary)
        }
      }
    )
  }
}

@Composable
fun SavedArtistCard(
  artist: SavedArtistEntity,
  onToggleListened: () -> Unit,
  onEditNotes: () -> Unit,
  onRemove: () -> Unit,
  onListen: () -> Unit,
  onSeedFrom: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    border = BorderStroke(1.dp, DarkSurfaceBorder),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("saved_artist_card_${artist.artistId}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f)
        ) {
          IconButton(
            onClick = onToggleListened,
            modifier = Modifier.size(36.dp)
          ) {
            Icon(
              imageVector = if (artist.isListened) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
              contentDescription = if (artist.isListened) "Mark as unlistened" else "Mark as listened",
              tint = if (artist.isListened) NeonMint else TextMuted,
              modifier = Modifier.size(24.dp)
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Text(
              text = artist.name,
              style = MaterialTheme.typography.titleMedium,
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = artist.genres,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextSecondary,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = DarkSurfaceVariant,
          border = BorderStroke(1.dp, DarkSurfaceBorder)
        ) {
          Text(
            text = "${artist.matchScore}% Match",
            style = MaterialTheme.typography.labelSmall,
            color = NeonMint,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = artist.vibeSummary,
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      // Listening Notes Section
      Spacer(modifier = Modifier.height(10.dp))
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = DarkBackground,
        border = BorderStroke(1.dp, DarkSurfaceBorder),
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onEditNotes)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              Icons.Default.Edit,
              contentDescription = "Edit notes",
              tint = TextMuted,
              modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (artist.notes.isNotBlank()) artist.notes else "Add personal listening notes...",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = if (artist.notes.isNotBlank()) TextPrimary else TextMuted,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Action Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = NeonMint.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, NeonMint.copy(alpha = 0.4f)),
            modifier = Modifier.clickable(onClick = onListen)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Outlined.OpenInNew, contentDescription = "Listen", tint = NeonMint, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Listen", style = MaterialTheme.typography.labelSmall, color = NeonMint, fontWeight = FontWeight.Bold)
            }
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = DarkSurfaceVariant,
            modifier = Modifier.clickable(onClick = onSeedFrom)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Spa, contentDescription = "Seed", tint = CyberViolet, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Seed from this", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            }
          }
        }

        IconButton(
          onClick = onRemove,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(Icons.Outlined.Delete, contentDescription = "Remove from crate", tint = TextMuted, modifier = Modifier.size(18.dp))
        }
      }
    }
  }
}
