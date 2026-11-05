package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.model.SeedType
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonMint
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddSeedDialog(
  onDismiss: () -> Unit,
  onAddSeed: (
    title: String,
    artist: String,
    type: SeedType,
    genre: String,
    mood: String,
    energy: Float,
    atmosphere: Float,
    groove: Float,
    acousticness: Float,
    vocalPresence: Float
  ) -> Unit
) {
  var seedType by remember { mutableStateOf(SeedType.SONG) }
  var title by remember { mutableStateOf("") }
  var artistName by remember { mutableStateOf("") }
  var genre by remember { mutableStateOf("Dream Pop") }
  var mood by remember { mutableStateOf("Ethereal & Atmospheric") }

  var energy by remember { mutableFloatStateOf(0.6f) }
  var atmosphere by remember { mutableFloatStateOf(0.8f) }
  var groove by remember { mutableFloatStateOf(0.65f) }
  var showSonicSliders by remember { mutableStateOf(false) }

  val commonGenres = listOf(
    "Dream Pop", "Shoegaze", "Indie Rock", "Neo-Soul", "Alternative R&B",
    "Psychedelic", "UK Garage", "Electronic", "Post-Punk", "Indie Folk"
  )

  val commonMoods = listOf(
    "Ethereal & Atmospheric", "Hypnotic & Phased", "Vulnerable & Moody",
    "Driving & Raw", "Warm Analog & Nostalgic", "Upbeat & Groovy"
  )

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = DarkSurface,
      border = BorderStroke(1.dp, DarkSurfaceBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("add_seed_dialog")
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Dialog Title & Close button
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Add Musical Influence",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("close_add_seed_dialog")
          ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = TextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Type Toggle: Song vs Artist
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (seedType == SeedType.SONG) CyberViolet.copy(alpha = 0.2f) else DarkSurfaceVariant,
            border = BorderStroke(1.dp, if (seedType == SeedType.SONG) CyberViolet else DarkSurfaceBorder),
            modifier = Modifier
              .weight(1f)
              .clickable { seedType = SeedType.SONG }
              .testTag("select_song_type")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.MusicNote,
                contentDescription = "Song",
                tint = if (seedType == SeedType.SONG) CyberViolet else TextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Favorite Song",
                style = MaterialTheme.typography.labelMedium,
                color = if (seedType == SeedType.SONG) TextPrimary else TextMuted,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = if (seedType == SeedType.ARTIST) NeonMint.copy(alpha = 0.2f) else DarkSurfaceVariant,
            border = BorderStroke(1.dp, if (seedType == SeedType.ARTIST) NeonMint else DarkSurfaceBorder),
            modifier = Modifier
              .weight(1f)
              .clickable { seedType = SeedType.ARTIST }
              .testTag("select_artist_type")
          ) {
            Row(
              modifier = Modifier.padding(vertical = 10.dp),
              horizontalArrangement = Arrangement.Center,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                Icons.Default.Person,
                contentDescription = "Artist",
                tint = if (seedType == SeedType.ARTIST) NeonMint else TextMuted,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Favorite Artist",
                style = MaterialTheme.typography.labelMedium,
                color = if (seedType == SeedType.ARTIST) TextPrimary else TextMuted,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Suggestions
        Text(
          text = "POPULAR INSPIRATIONS",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          MusicKnowledgeCatalog.quickSuggestions.take(8).forEach { (suggestion, type) ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = DarkBackground,
              border = BorderStroke(1.dp, DarkSurfaceBorder),
              modifier = Modifier.clickable {
                if (type == SeedType.SONG) {
                  seedType = SeedType.SONG
                  title = suggestion
                  artistName = ""
                } else {
                  seedType = SeedType.ARTIST
                  title = suggestion
                }
              }
            ) {
              Text(
                text = "+ $suggestion",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title Input
        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text(if (seedType == SeedType.SONG) "Song Title *" else "Artist Name *") },
          placeholder = { Text(if (seedType == SeedType.SONG) "e.g. Myth or Nights" else "e.g. Radiohead or Tame Impala") },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("seed_title_input"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (seedType == SeedType.SONG) CyberViolet else NeonMint,
            unfocusedBorderColor = DarkSurfaceBorder,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            focusedLabelColor = if (seedType == SeedType.SONG) CyberViolet else NeonMint,
            unfocusedLabelColor = TextSecondary
          )
        )

        // Artist Name Input (for Songs)
        if (seedType == SeedType.SONG) {
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = artistName,
            onValueChange = { artistName = it },
            label = { Text("Artist Name") },
            placeholder = { Text("e.g. Beach House or Frank Ocean") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("seed_artist_input"),
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = CyberViolet,
              unfocusedBorderColor = DarkSurfaceBorder,
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedLabelColor = CyberViolet,
              unfocusedLabelColor = TextSecondary
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Genre Picker
        Text(
          text = "PRIMARY GENRE",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          commonGenres.forEach { g ->
            val isSelected = genre.equals(g, ignoreCase = true)
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) NeonMint.copy(alpha = 0.2f) else DarkBackground,
              border = BorderStroke(1.dp, if (isSelected) NeonMint else DarkSurfaceBorder),
              modifier = Modifier.clickable { genre = g }
            ) {
              Text(
                text = g,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = if (isSelected) NeonMint else TextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Mood Picker
        Text(
          text = "SONIC MOOD",
          style = MaterialTheme.typography.labelSmall,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          commonMoods.forEach { m ->
            val isSelected = mood.equals(m, ignoreCase = true)
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) CyberViolet.copy(alpha = 0.2f) else DarkBackground,
              border = BorderStroke(1.dp, if (isSelected) CyberViolet else DarkSurfaceBorder),
              modifier = Modifier.clickable { mood = m }
            ) {
              Text(
                text = m,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = if (isSelected) CyberViolet else TextSecondary,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Optional fine-tune sliders toggle
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { showSonicSliders = !showSonicSliders },
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(Icons.Default.Tune, contentDescription = "Fine-tune", tint = TextSecondary, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (showSonicSliders) "Hide Acoustic DNA Controls" else "Fine-tune Acoustic DNA (Optional)",
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary
          )
        }

        if (showSonicSliders) {
          Spacer(modifier = Modifier.height(10.dp))
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = DarkBackground,
            border = BorderStroke(1.dp, DarkSurfaceBorder),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text("Atmosphere: ${(atmosphere * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
              Slider(
                value = atmosphere,
                onValueChange = { atmosphere = it },
                colors = SliderDefaults.colors(thumbColor = NeonMint, activeTrackColor = NeonMint)
              )

              Text("Energy: ${(energy * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
              Slider(
                value = energy,
                onValueChange = { energy = it },
                colors = SliderDefaults.colors(thumbColor = CyberViolet, activeTrackColor = CyberViolet)
              )

              Text("Groove: ${(groove * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TextPrimary)
              Slider(
                value = groove,
                onValueChange = { groove = it },
                colors = SliderDefaults.colors(thumbColor = NeonMint, activeTrackColor = NeonMint)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Confirm Button
        Button(
          onClick = {
            if (title.isNotBlank()) {
              onAddSeed(
                title,
                artistName,
                seedType,
                genre,
                mood,
                energy,
                atmosphere,
                groove,
                0.45f,
                0.7f
              )
            }
          },
          enabled = title.isNotBlank(),
          colors = ButtonDefaults.buttonColors(
            containerColor = NeonMint,
            contentColor = DarkBackground,
            disabledContainerColor = DarkSurfaceVariant,
            disabledContentColor = TextMuted
          ),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .testTag("confirm_add_seed_button")
        ) {
          Text(
            text = "Generate Recommendations",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
          )
        }
      }
    }
  }
}
