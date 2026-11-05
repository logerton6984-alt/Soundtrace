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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.data.model.FavoriteSeed
import com.example.data.model.SeedType
import com.example.ui.components.SonicAttributeBar
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
fun SeedsManagerScreen(
  seeds: List<FavoriteSeed>,
  onAddSeedClick: () -> Unit,
  onPresetsClick: () -> Unit,
  onToggleActive: (String, Boolean) -> Unit,
  onRemoveSeed: (String) -> Unit,
  onClearAll: () -> Unit,
  modifier: Modifier = Modifier
) {
  var filterType by remember { mutableStateOf<SeedType?>(null) }

  val filteredSeeds = when (filterType) {
    SeedType.SONG -> seeds.filter { it.type == SeedType.SONG }
    SeedType.ARTIST -> seeds.filter { it.type == SeedType.ARTIST }
    null -> seeds
  }

  // Calculate composite DNA
  val activeSeeds = seeds.filter { it.isActive }
  val avgEnergy = if (activeSeeds.isNotEmpty()) activeSeeds.map { it.sonicProfile.energy }.average().toFloat() else 0.5f
  val avgAtmosphere = if (activeSeeds.isNotEmpty()) activeSeeds.map { it.sonicProfile.atmosphere }.average().toFloat() else 0.5f
  val avgGroove = if (activeSeeds.isNotEmpty()) activeSeeds.map { it.sonicProfile.groove }.average().toFloat() else 0.5f
  val avgAcoustic = if (activeSeeds.isNotEmpty()) activeSeeds.map { it.sonicProfile.acousticness }.average().toFloat() else 0.5f

  val sonicArchetype = when {
    avgAtmosphere > 0.75f && avgEnergy < 0.6f -> "Atmospheric Dream-Weaver"
    avgGroove > 0.75f && avgEnergy > 0.7f -> "High-Voltage Rhythm Seeker"
    avgAcoustic > 0.65f -> "Warm Organic Purist"
    avgEnergy > 0.75f -> "Urgent Art-Rock Aficionado"
    else -> "Eclectic Sound Explorer"
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(DarkBackground)
      .testTag("seeds_manager_screen"),
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
              imageVector = Icons.Default.Spa,
              contentDescription = "Seeds",
              tint = NeonMint,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "INFLUENCE VAULT",
              style = MaterialTheme.typography.labelSmall,
              color = NeonMint,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "My Music Seeds",
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp),
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = CyberViolet.copy(alpha = 0.15f),
            border = BorderStroke(1.dp, CyberViolet.copy(alpha = 0.4f)),
            modifier = Modifier
              .clickable(onClick = onPresetsClick)
              .testTag("manage_presets_button")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.AutoAwesome, contentDescription = "Packs", tint = CyberViolet, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Packs", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))
    }

    // Composite Sound DNA Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        border = BorderStroke(1.dp, DarkSurfaceBorder),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("taste_profile_card")
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.GraphicEq, contentDescription = "DNA", tint = CyberViolet, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "COMPOSITE TASTE DNA",
                style = MaterialTheme.typography.labelSmall,
                color = CyberViolet,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
            }
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = DarkSurfaceVariant
            ) {
              Text(
                text = "${activeSeeds.size} Active",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = sonicArchetype,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )

          Spacer(modifier = Modifier.height(12.dp))

          SonicAttributeBar(name = "Atmosphere", value = avgAtmosphere, color = ElectricBlue, leftLabel = "Dry", rightLabel = "Ethereal")
          Spacer(modifier = Modifier.height(6.dp))
          SonicAttributeBar(name = "Energy", value = avgEnergy, color = SunsetCoral, leftLabel = "Calm", rightLabel = "Driving")
          Spacer(modifier = Modifier.height(6.dp))
          SonicAttributeBar(name = "Groove", value = avgGroove, color = NeonMint, leftLabel = "Freeform", rightLabel = "Rhythmic")
          Spacer(modifier = Modifier.height(6.dp))
          SonicAttributeBar(name = "Acousticness", value = avgAcoustic, color = WarmGold, leftLabel = "Synthetic", rightLabel = "Organic")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))
    }

    // Filter Buttons (All, Songs, Artists) & Add button
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          FilterPill(label = "All (${seeds.size})", isSelected = filterType == null, onClick = { filterType = null })
          FilterPill(label = "Songs (${seeds.count { it.type == SeedType.SONG }})", isSelected = filterType == SeedType.SONG, onClick = { filterType = SeedType.SONG })
          FilterPill(label = "Artists (${seeds.count { it.type == SeedType.ARTIST }})", isSelected = filterType == SeedType.ARTIST, onClick = { filterType = SeedType.ARTIST })
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = NeonMint,
          modifier = Modifier
            .clickable(onClick = onAddSeedClick)
            .testTag("manage_add_seed_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = DarkBackground, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Add", style = MaterialTheme.typography.labelMedium, color = DarkBackground, fontWeight = FontWeight.Bold)
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))
    }

    // List of Seeds
    if (filteredSeeds.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = DarkSurface),
          border = BorderStroke(1.dp, DarkSurfaceBorder),
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.MusicNote, contentDescription = "Empty", tint = TextMuted, modifier = Modifier.size(36.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text("No seeds in this category", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Tap '+ Add' above to save your favorite songs or artists.", style = MaterialTheme.typography.bodySmall, color = TextSecondary, textAlign = TextAlign.Center)
          }
        }
      }
    } else {
      items(filteredSeeds, key = { it.id }) { seed ->
        SeedManagementCard(
          seed = seed,
          onToggleActive = { onToggleActive(seed.id, it) },
          onRemove = { onRemoveSeed(seed.id) }
        )
        Spacer(modifier = Modifier.height(8.dp))
      }
    }

    if (seeds.isNotEmpty()) {
      item {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
        ) {
          TextButton(
            onClick = onClearAll,
            colors = ButtonDefaults.textButtonColors(contentColor = SunsetCoral.copy(alpha = 0.8f))
          ) {
            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear all", modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Clear All Seeds", style = MaterialTheme.typography.labelSmall)
          }
        }
      }
    }
  }
}

@Composable
fun FilterPill(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (isSelected) NeonMint.copy(alpha = 0.2f) else DarkSurface,
    border = BorderStroke(1.dp, if (isSelected) NeonMint else DarkSurfaceBorder),
    modifier = Modifier.clickable(onClick = onClick)
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = if (isSelected) NeonMint else TextSecondary,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
    )
  }
}

@Composable
fun SeedManagementCard(
  seed: FavoriteSeed,
  onToggleActive: (Boolean) -> Unit,
  onRemove: () -> Unit
) {
  val typeColor = if (seed.type == SeedType.SONG) CyberViolet else NeonMint

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    border = BorderStroke(1.dp, if (seed.isActive) DarkSurfaceBorder else DarkSurfaceBorder.copy(alpha = 0.4f)),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("manage_seed_card_${seed.id}")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(40.dp)
          .clip(CircleShape)
          .background(typeColor.copy(alpha = if (seed.isActive) 0.15f else 0.05f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (seed.type == SeedType.SONG) Icons.Default.MusicNote else Icons.Default.Person,
          contentDescription = seed.type.name,
          tint = if (seed.isActive) typeColor else TextMuted,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = seed.title,
          style = MaterialTheme.typography.titleMedium,
          color = if (seed.isActive) TextPrimary else TextMuted,
          fontWeight = FontWeight.Bold,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        if (seed.type == SeedType.SONG && seed.artist.isNotBlank()) {
          Text(
            text = "by ${seed.artist}",
            style = MaterialTheme.typography.bodySmall,
            color = if (seed.isActive) TextSecondary else TextMuted,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = DarkSurfaceVariant
          ) {
            Text(
              text = seed.genre,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
              color = TextSecondary,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = seed.mood,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = TextMuted,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      Spacer(modifier = Modifier.width(8.dp))

      // Active Switch
      Switch(
        checked = seed.isActive,
        onCheckedChange = onToggleActive,
        colors = SwitchDefaults.colors(
          checkedThumbColor = NeonMint,
          checkedTrackColor = NeonMint.copy(alpha = 0.35f),
          uncheckedThumbColor = TextMuted,
          uncheckedTrackColor = DarkSurfaceVariant
        )
      )

      Spacer(modifier = Modifier.width(4.dp))

      // Delete Button
      IconButton(
        onClick = onRemove,
        modifier = Modifier.testTag("delete_seed_${seed.id}")
      ) {
        Icon(
          imageVector = Icons.Outlined.Delete,
          contentDescription = "Delete seed",
          tint = TextMuted,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}
