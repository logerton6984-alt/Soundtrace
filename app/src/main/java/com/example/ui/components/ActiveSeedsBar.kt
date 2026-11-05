package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FavoriteSeed
import com.example.data.model.SeedType
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonMint
import com.example.ui.theme.SunsetCoral
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ActiveSeedsBar(
  seeds: List<FavoriteSeed>,
  onAddSeedClick: () -> Unit,
  onPresetsClick: () -> Unit,
  onToggleActive: (String, Boolean) -> Unit,
  onRemoveSeed: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .testTag("active_seeds_section")
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(NeonMint)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "ACTIVE INFLUENCES (${seeds.count { it.isActive }}/${seeds.size})",
          style = MaterialTheme.typography.labelMedium,
          color = NeonMint,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = DarkSurfaceVariant,
          border = androidx.compose.foundation.BorderStroke(1.dp, DarkSurfaceBorder),
          modifier = Modifier
            .clickable(onClick = onPresetsClick)
            .testTag("presets_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.AutoAwesome,
              contentDescription = "Presets",
              tint = CyberViolet,
              modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "Presets",
              style = MaterialTheme.typography.labelSmall,
              color = TextPrimary
            )
          }
        }
      }
    }

    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // "+ Add Seed" button
      item {
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = NeonMint.copy(alpha = 0.15f),
          border = androidx.compose.foundation.BorderStroke(1.dp, NeonMint.copy(alpha = 0.4f)),
          modifier = Modifier
            .clickable(onClick = onAddSeedClick)
            .testTag("add_seed_chip")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Add Seed",
              tint = NeonMint,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Add Seed",
              style = MaterialTheme.typography.labelMedium,
              color = NeonMint,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }

      items(seeds, key = { it.id }) { seed ->
        SeedChip(
          seed = seed,
          onToggle = { onToggleActive(seed.id, !seed.isActive) },
          onRemove = { onRemoveSeed(seed.id) }
        )
      }
    }
  }
}

@Composable
fun SeedChip(
  seed: FavoriteSeed,
  onToggle: () -> Unit,
  onRemove: () -> Unit
) {
  val borderColor = if (seed.isActive) {
    if (seed.type == SeedType.SONG) CyberViolet.copy(alpha = 0.7f) else NeonMint.copy(alpha = 0.7f)
  } else {
    DarkSurfaceBorder
  }

  val backgroundColor = if (seed.isActive) {
    if (seed.type == SeedType.SONG) CyberViolet.copy(alpha = 0.12f) else NeonMint.copy(alpha = 0.12f)
  } else {
    DarkSurfaceVariant.copy(alpha = 0.5f)
  }

  Surface(
    shape = RoundedCornerShape(20.dp),
    color = backgroundColor,
    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
    modifier = Modifier
      .clickable(onClick = onToggle)
      .testTag("seed_chip_${seed.id}")
  ) {
    Row(
      modifier = Modifier.padding(start = 10.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = if (seed.type == SeedType.SONG) Icons.Default.MusicNote else Icons.Default.Person,
        contentDescription = seed.type.name,
        tint = if (seed.isActive) {
          if (seed.type == SeedType.SONG) CyberViolet else NeonMint
        } else {
          TextMuted
        },
        modifier = Modifier.size(15.dp)
      )

      Spacer(modifier = Modifier.width(6.dp))

      Column {
        Text(
          text = seed.title,
          style = MaterialTheme.typography.labelMedium,
          color = if (seed.isActive) TextPrimary else TextMuted,
          fontWeight = FontWeight.Medium,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
        if (seed.type == SeedType.SONG && seed.artist.isNotBlank()) {
          Text(
            text = seed.artist,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = if (seed.isActive) TextSecondary else TextMuted,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
        }
      }

      Spacer(modifier = Modifier.width(4.dp))

      Box(
        modifier = Modifier
          .size(22.dp)
          .clip(CircleShape)
          .clickable(onClick = onRemove)
          .testTag("remove_seed_${seed.id}"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Remove Seed",
          tint = TextSecondary,
          modifier = Modifier.size(13.dp)
        )
      }
    }
  }
}
