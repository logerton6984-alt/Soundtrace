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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Hearing
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DiscoveryDepth
import com.example.data.model.RecommendedArtist
import com.example.ui.theme.CyberViolet
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArtistRecommendationCard(
  artist: RecommendedArtist,
  onCardClick: () -> Unit,
  onListenClick: () -> Unit,
  onSeedFromArtist: () -> Unit,
  onToggleSave: () -> Unit,
  modifier: Modifier = Modifier
) {
  val accentColor = Color(artist.colorAccentHex)

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = DarkSurface),
    border = BorderStroke(1.dp, DarkSurfaceBorder),
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onCardClick)
      .testTag("artist_card_${artist.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      // Top row: Name, Origin & Match Score Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = artist.name,
            style = MaterialTheme.typography.displayMedium.copy(fontSize = 22.sp),
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          Spacer(modifier = Modifier.height(2.dp))
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = artist.origin,
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
              modifier = Modifier
                .size(3.dp)
                .clip(CircleShape)
                .background(TextMuted)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = artist.tier.label,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = when (artist.tier) {
                DiscoveryDepth.CLOSE_MATCH -> NeonMint
                DiscoveryDepth.BALANCED -> ElectricBlue
                DiscoveryDepth.DEEP_CUT -> CyberViolet
              },
              fontWeight = FontWeight.Medium
            )
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Match Score Radial Badge
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = DarkSurfaceVariant,
          border = BorderStroke(1.5.dp, accentColor)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(accentColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "${artist.matchScore}%",
              style = MaterialTheme.typography.labelLarge,
              color = TextPrimary,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Genre pills
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        artist.genres.forEach { genre ->
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = DarkSurfaceVariant.copy(alpha = 0.8f),
            border = BorderStroke(1.dp, DarkSurfaceBorder)
          ) {
            Text(
              text = genre,
              style = MaterialTheme.typography.labelSmall,
              color = TextSecondary,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Vibe summary
      Text(
        text = artist.vibeSummary,
        style = MaterialTheme.typography.bodyMedium,
        color = TextPrimary.copy(alpha = 0.9f),
        lineHeight = 19.sp,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(10.dp))

      // "Why You'll Love Them" narrative box
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = DarkSurfaceVariant.copy(alpha = 0.6f),
        border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(10.dp),
          verticalAlignment = Alignment.Top
        ) {
          Icon(
            imageVector = Icons.Default.Hearing,
            contentDescription = "Sonic match",
            tint = accentColor,
            modifier = Modifier
              .size(16.dp)
              .padding(top = 2.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = artist.whyRecommended,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            lineHeight = 16.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Essential starter track preview
      val gatewayTrack = artist.topTracks.firstOrNull()
      if (gatewayTrack != null) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(DarkSurfaceVariant.copy(alpha = 0.35f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(accentColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Play",
              tint = accentColor,
              modifier = Modifier.size(16.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Start with: \"${gatewayTrack.title}\"",
              style = MaterialTheme.typography.labelMedium,
              color = TextPrimary,
              fontWeight = FontWeight.Medium,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
            Text(
              text = gatewayTrack.highlight,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = accentColor,
              maxLines = 1
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action row: Listen / Seed from / Bookmark
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Listen button
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = NeonMint.copy(alpha = 0.15f),
          border = BorderStroke(1.dp, NeonMint.copy(alpha = 0.4f)),
          modifier = Modifier
            .clickable(onClick = onListenClick)
            .testTag("listen_artist_${artist.id}")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Outlined.OpenInNew,
              contentDescription = "Listen",
              tint = NeonMint,
              modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Listen",
              style = MaterialTheme.typography.labelMedium,
              color = NeonMint,
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          // Seed from this artist
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = DarkSurfaceVariant,
            border = BorderStroke(1.dp, DarkSurfaceBorder),
            modifier = Modifier
              .clickable(onClick = onSeedFromArtist)
              .testTag("seed_from_${artist.id}")
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = "Seed From Artist",
                tint = CyberViolet,
                modifier = Modifier.size(15.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Seed from",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
              )
            }
          }

          Spacer(modifier = Modifier.width(8.dp))

          // Save to Crate toggle
          IconButton(
            onClick = onToggleSave,
            modifier = Modifier
              .size(40.dp)
              .testTag("save_artist_${artist.id}")
          ) {
            Icon(
              imageVector = if (artist.isSavedToCrate) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = if (artist.isSavedToCrate) "Remove from Crate" else "Save to Crate",
              tint = if (artist.isSavedToCrate) SunsetCoral else TextMuted,
              modifier = Modifier.size(22.dp)
            )
          }
        }
      }
    }
  }
}
