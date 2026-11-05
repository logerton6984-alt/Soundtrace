package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SoundTraceColorScheme = darkColorScheme(
  primary = NeonMint,
  onPrimary = DarkBackground,
  primaryContainer = DarkSurfaceVariant,
  onPrimaryContainer = NeonMint,
  secondary = CyberViolet,
  onSecondary = DarkBackground,
  secondaryContainer = DarkSurfaceVariant,
  onSecondaryContainer = CyberViolet,
  tertiary = ElectricBlue,
  onTertiary = DarkBackground,
  background = DarkBackground,
  onBackground = TextPrimary,
  surface = DarkSurface,
  onSurface = TextPrimary,
  surfaceVariant = DarkSurfaceVariant,
  onSurfaceVariant = TextSecondary,
  outline = DarkSurfaceBorder,
  surfaceContainerHigh = DarkSurfaceHigh
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = SoundTraceColorScheme

  val view = LocalView.current
  if (!view.isInEditMode) {
    SideEffect {
      val window = (view.context as? Activity)?.window
      if (window != null) {
        window.statusBarColor = DarkBackground.toArgb()
        window.navigationBarColor = DarkBackground.toArgb()
        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = false
        insetsController.isAppearanceLightNavigationBars = false
      }
    }
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
