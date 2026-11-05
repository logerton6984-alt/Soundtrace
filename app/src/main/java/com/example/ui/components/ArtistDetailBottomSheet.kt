package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RecommendedArtist
import com.example.data.model.SampleTrack
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkBackground
import androidx.compose.foundation.layout.navigationBarsPadding
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ArtistDetailBottomSheet(
  artist: RecommendedArtist,
  sheetState: SheetState,
  onDismiss: () -> Unit,
  onListenTrack: (String) -> Unit,
  onSeedFromArtist: () -> Unit,
  onToggleSave: () -> Unit,
  modifier: Modifier = Modifier
) {
  val accentColor = Color(artist.colorAccentHex)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = DarkSurface,
    contentColor = TextPrimary,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    modifier = modifier.testTag("artist_detail_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .navigationBarsPadding()
    ) {
      // Header: Artist name, origin, close button
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = artist.name,
            style = MaterialTheme.typography.displayLarge.copy(fontSize = 28.sp),
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "${artist.origin} • ${artist.tier.label}",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
          )
        }

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = DarkSurfaceVariant,
          border = BorderStroke(1.5.dp, accentColor)
        ) {
          Text(
            text = "${artist.matchScore}% Match",
            style = MaterialTheme.typography.labelLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Genre chips
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        artist.genres.forEach { genre ->
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = DarkSurfaceVariant,
            border = BorderStroke(1.dp, DarkSurfaceBorder)
          ) {
            Text(
              text = genre,
              style = MaterialTheme.typography.labelMedium,
              color = TextPrimary,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Sonic Bridge Box (Why Recommended)
      Surface(
        shape = RoundedCornerShape(16.dp),
        color = DarkSurfaceVariant.copy(alpha = 0.7f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.35f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Hearing,
              contentDescription = "Sonic Bridge",
              tint = accentColor,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "WHY THEY'RE ON YOUR RADAR",
              style = MaterialTheme.typography.labelSmall,
              color = accentColor,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
          }
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = artist.whyRecommended,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            lineHeight = 20.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Sonic Profile Analysis Bars
      Text(
        text = "SONIC DNA SPECTRUM",
        style = MaterialTheme.typography.labelMedium,
        color = TextMuted,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.height(10.dp))

      Surface(
        shape = RoundedCornerShape(16.dp),
        color = DarkBackground,
        border = BorderStroke(1.dp, DarkSurfaceBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(
          modifier = Modifier.padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          SonicAttributeBar(name = "Atmosphere", value = artist.sonicProfile.atmosphere, color = ElectricBlue, leftLabel = "Dry", rightLabel = "Ethereal")
          SonicAttributeBar(name = "Energy", value = artist.sonicProfile.energy, color = SunsetCoral, leftLabel = "Chill", rightLabel = "Driving")
          SonicAttributeBar(name = "Groove", value = artist.sonicProfile.groove, color = NeonMint, leftLabel = "Freeform", rightLabel = "Rhythmic")
          SonicAttributeBar(name = "Acousticness", value = artist.sonicProfile.acousticness, color = WarmGold, leftLabel = "Synthetic", rightLabel = "Organic")
          SonicAttributeBar(name = "Vocal Presence", value = artist.sonicProfile.vocalPresence, color = CyberViolet, leftLabel = "Textural", rightLabel = "Prominent")
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Essential Starter Tracks
      Text(
        text = "START WITH THESE TRACKS",
        style = MaterialTheme.typography.labelMedium,
        color = TextMuted,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )
      Spacer(modifier = Modifier.height(10.dp))

      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        artist.topTracks.forEachIndexed { index, track ->
          TrackItemRow(
            index = index + 1,
            track = track,
            accentColor = accentColor,
            onPlayClick = { onListenTrack(track.title) }
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Shared Vibe / Similar Artists
      if (artist.similarTo.isNotEmpty()) {
        Text(
          text = "IN THE SAME ORBIT AS",
          style = MaterialTheme.typography.labelMedium,
          color = TextMuted,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          artist.similarTo.forEach { similar ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = DarkSurfaceVariant,
              border = BorderStroke(1.dp, DarkSurfaceBorder)
            ) {
              Text(
                text = similar,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(20.dp))
      }

      // Action Buttons at bottom
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = { onListenTrack("") },
          colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = DarkBackground),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("detail_listen_button")
        ) {
          Icon(Icons.Outlined.OpenInNew, contentDescription = "Listen", modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Listen on Web", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
          onClick = onSeedFromArtist,
          colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberViolet),
          border = BorderStroke(1.dp, CyberViolet),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .height(50.dp)
            .testTag("detail_seed_button")
        ) {
          Icon(Icons.Default.Spa, contentDescription = "Seed", modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text("Seed This", fontWeight = FontWeight.Bold)
        }

        IconButton(
          onClick = onToggleSave,
          modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(DarkSurfaceVariant)
            .testTag("detail_save_button")
        ) {
          Icon(
            imageVector = if (artist.isSavedToCrate) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = "Save",
            tint = if (artist.isSavedToCrate) SunsetCoral else TextSecondary,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }
  }
}

@Composable
fun SonicAttributeBar(
  name: String,
  value: Float,
  color: Color,
  leftLabel: String,
  rightLabel: String
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = name,
        style = MaterialTheme.typography.labelSmall,
        color = TextPrimary,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = "${(value * 100).toInt()}%",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = color,
        fontWeight = FontWeight.Bold
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { value },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(RoundedCornerShape(3.dp)),
      color = color,
      trackColor = DarkSurfaceBorder
    )
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = leftLabel, style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp), color = TextMuted)
      Text(text = rightLabel, style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp), color = TextMuted)
    }
  }
}

@Composable
fun TrackItemRow(
  index: Int,
  track: SampleTrack,
  accentColor: Color,
  onPlayClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = DarkBackground,
    border = BorderStroke(1.dp, DarkSurfaceBorder),
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onPlayClick)
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "0$index",
        style = MaterialTheme.typography.labelMedium,
        color = TextMuted,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = track.title,
            style = MaterialTheme.typography.labelLarge,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.width(8.dp))
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = accentColor.copy(alpha = 0.15f)
          ) {
            Text(
              text = track.highlight,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
              color = accentColor,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = track.description,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondary,
          lineHeight = 15.sp
        )
      }

      Spacer(modifier = Modifier.width(8.dp))

      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(DarkSurfaceVariant),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.PlayArrow,
          contentDescription = "Listen to ${track.title}",
          tint = NeonMint,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}
