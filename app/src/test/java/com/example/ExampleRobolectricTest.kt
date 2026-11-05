package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.catalog.MusicKnowledgeCatalog
import com.example.data.catalog.RecommendationEngine
import com.example.data.model.DiscoveryDepth
import com.example.data.model.FavoriteSeed
import com.example.data.model.SeedType
import com.example.data.model.SonicProfile
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("SoundTrace", appName)
  }

  @Test
  fun `recommendations generated from dream pop song seed`() {
    val seeds = listOf(
      FavoriteSeed(
        id = "test_myth",
        type = SeedType.SONG,
        title = "Myth",
        artist = "Beach House",
        genre = "Dream Pop",
        mood = "Ethereal & Atmospheric",
        sonicProfile = SonicProfile(0.5f, 0.95f, 0.45f, 0.35f, 0.75f)
      )
    )

    val recommendations = RecommendationEngine.recommend(
      seeds = seeds,
      discoveryDepth = DiscoveryDepth.BALANCED
    )

    assertTrue("Recommendations should not be empty", recommendations.isNotEmpty())
    val topMatch = recommendations.first()
    assertTrue("Top match should have high match score >= 75%", topMatch.matchScore >= 75)
    assertTrue("Should include why recommended reasoning", topMatch.whyRecommended.contains("Myth"))
  }

  @Test
  fun `filtering by genre works correctly`() {
    val preset = MusicKnowledgeCatalog.curatedPresets.first()
    val recommendations = RecommendationEngine.recommend(
      seeds = preset.seeds,
      selectedGenreFilter = "Dream Pop"
    )

    assertTrue("Should find dream pop matches", recommendations.isNotEmpty())
    assertTrue("All results should match genre filter", recommendations.all { artist ->
      artist.genres.any { it.contains("Dream Pop", ignoreCase = true) }
    })
  }
}
