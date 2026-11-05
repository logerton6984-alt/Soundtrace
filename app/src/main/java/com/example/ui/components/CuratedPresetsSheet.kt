package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.model.CuratedPreset
import com.example.ui.theme.CyberViolet
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonMint
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CuratedPresetsSheet(
  sheetState: SheetState,
  onDismiss: () -> Unit,
  onSelectPreset: (CuratedPreset) -> Unit,
  modifier: Modifier = Modifier
) {
  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = DarkSurface,
    contentColor = TextPrimary,
    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    modifier = modifier.testTag("curated_presets_sheet")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
        .navigationBarsPadding()
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.AutoAwesome,
          contentDescription = "Presets",
          tint = CyberViolet,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Curated Influence Packs",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "Select a curated sound palette to explore its artist orbit immediately.",
        style = MaterialTheme.typography.bodyMedium,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(16.dp))

      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MusicKnowledgeCatalog.curatedPresets.forEach { preset ->
          PresetCard(
            preset = preset,
            onLoad = { onSelectPreset(preset) }
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PresetCard(
  preset: CuratedPreset,
  onLoad: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = DarkBackground,
    border = BorderStroke(1.dp, DarkSurfaceBorder),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("preset_card_${preset.id}")
  ) {
    Column(
      modifier = Modifier.padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = preset.name,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = preset.tagLine,
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
          )
        }

        Button(
          onClick = onLoad,
          colors = ButtonDefaults.buttonColors(containerColor = CyberViolet, contentColor = TextPrimary),
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.testTag("load_preset_${preset.id}")
        ) {
          Text("Load Pack", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Seeds list preview
      FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        preset.seeds.forEach { seed ->
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = DarkSurfaceVariant
          ) {
            Text(
              text = "• ${seed.displayName}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = TextPrimary.copy(alpha = 0.85f),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
          }
        }
      }
    }
  }
}
