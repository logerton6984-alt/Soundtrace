package com.example.data.catalog

import com.example.data.model.CuratedPreset
import com.example.data.model.DiscoveryDepth
import com.example.data.model.FavoriteSeed
import com.example.data.model.SampleTrack
import com.example.data.model.SeedType
import com.example.data.model.SonicProfile

data class CatalogArtist(
  val id: String,
  val name: String,
  val origin: String,
  val genres: List<String>,
  val sonicProfile: SonicProfile,
  val vibeSummary: String,
  val sonicKeywords: List<String>,
  val topTracks: List<SampleTrack>,
  val tier: DiscoveryDepth,
  val similarTo: List<String>,
  val accentColorHex: Long = 0xFF00E5C9
)

object MusicKnowledgeCatalog {

  val curatedPresets: List<CuratedPreset> = listOf(
    CuratedPreset(
      id = "preset_indie_dream",
      name = "Dream Pop & Shoegaze",
      tagLine = "Ethereal reverbs, jangly guitars & nostalgic melodies",
      genres = listOf("Dream Pop", "Shoegaze", "Indie Rock"),
      seeds = listOf(
        FavoriteSeed(
          id = "seed_myth",
          type = SeedType.SONG,
          title = "Myth",
          artist = "Beach House",
          genre = "Dream Pop",
          mood = "Ethereal & Nostalgic",
          sonicProfile = SonicProfile(0.5f, 0.95f, 0.45f, 0.35f, 0.75f)
        ),
        FavoriteSeed(
          id = "seed_slowdive",
          type = SeedType.ARTIST,
          title = "Slowdive",
          genre = "Shoegaze",
          mood = "Sublime Reverb Walls",
          sonicProfile = SonicProfile(0.6f, 0.98f, 0.4f, 0.3f, 0.6f)
        ),
        FavoriteSeed(
          id = "seed_alvvays",
          type = SeedType.ARTIST,
          title = "Alvvays",
          genre = "Indie Pop",
          mood = "Jangle Pop Energy",
          sonicProfile = SonicProfile(0.75f, 0.7f, 0.65f, 0.4f, 0.85f)
        )
      )
    ),
    CuratedPreset(
      id = "preset_neo_psychedelia",
      name = "Psych & Analog Grooves",
      tagLine = "Phased drums, hypnotic basslines & warm vintage synths",
      genres = listOf("Psychedelic Pop", "Funk", "Indie Soul"),
      seeds = listOf(
        FavoriteSeed(
          id = "seed_tame",
          type = SeedType.ARTIST,
          title = "Tame Impala",
          genre = "Psychedelic Pop",
          mood = "Phased Hypnotic Vibe",
          sonicProfile = SonicProfile(0.72f, 0.85f, 0.82f, 0.3f, 0.7f)
        ),
        FavoriteSeed(
          id = "seed_khruangbin",
          type = SeedType.ARTIST,
          title = "Khruangbin",
          genre = "Thai Funk / Psych",
          mood = "Chill Sunset Groove",
          sonicProfile = SonicProfile(0.55f, 0.88f, 0.85f, 0.65f, 0.2f)
        ),
        FavoriteSeed(
          id = "seed_borderline",
          type = SeedType.SONG,
          title = "Borderline",
          artist = "Tame Impala",
          genre = "Synthpop / Psych",
          mood = "Groovy Late-Night Walk",
          sonicProfile = SonicProfile(0.78f, 0.8f, 0.88f, 0.25f, 0.75f)
        )
      )
    ),
    CuratedPreset(
      id = "preset_neo_soul",
      name = "Neo-Soul Sanctuary",
      tagLine = "Lush Rhodes chords, intimate vocal runs & slinky bass",
      genres = listOf("Neo-Soul", "Alternative R&B", "Contemporary Jazz"),
      seeds = listOf(
        FavoriteSeed(
          id = "seed_sza",
          type = SeedType.ARTIST,
          title = "SZA",
          genre = "Alternative R&B",
          mood = "Vulnerable & Moody",
          sonicProfile = SonicProfile(0.58f, 0.78f, 0.75f, 0.4f, 0.95f)
        ),
        FavoriteSeed(
          id = "seed_nights",
          type = SeedType.SONG,
          title = "Nights",
          artist = "Frank Ocean",
          genre = "Alt-R&B",
          mood = "Introspective Euphoria",
          sonicProfile = SonicProfile(0.68f, 0.82f, 0.76f, 0.38f, 0.88f)
        )
      )
    ),
    CuratedPreset(
      id = "preset_electronic_ambient",
      name = "Hypnotic Electronic & UKG",
      tagLine = "Syncopated garage shuffles, emotive vocal chops & club energy",
      genres = listOf("Electronic", "UK Garage", "IDM"),
      seeds = listOf(
        FavoriteSeed(
          id = "seed_fredagain",
          type = SeedType.ARTIST,
          title = "Fred again..",
          genre = "Emotional Electronic",
          mood = "Cathartic Club Memories",
          sonicProfile = SonicProfile(0.85f, 0.8f, 0.9f, 0.15f, 0.78f)
        ),
        FavoriteSeed(
          id = "seed_teardrop",
          type = SeedType.SONG,
          title = "Teardrop",
          artist = "Massive Attack",
          genre = "Trip-Hop",
          mood = "Dark Sub-bass Atmosphere",
          sonicProfile = SonicProfile(0.48f, 0.94f, 0.62f, 0.42f, 0.85f)
        )
      )
    ),
    CuratedPreset(
      id = "preset_indie_folk",
      name = "Acoustic Warmth & Slowcore",
      tagLine = "Fingerpicked nylon strings, warm tape saturation & raw poetry",
      genres = listOf("Indie Folk", "Slowcore", "Singer-Songwriter"),
      seeds = listOf(
        FavoriteSeed(
          id = "seed_phoebe",
          type = SeedType.ARTIST,
          title = "Phoebe Bridgers",
          genre = "Indie Folk / Emo",
          mood = "Melancholic Whispers",
          sonicProfile = SonicProfile(0.42f, 0.82f, 0.35f, 0.85f, 0.92f)
        ),
        FavoriteSeed(
          id = "seed_holocene",
          type = SeedType.SONG,
          title = "Holocene",
          artist = "Bon Iver",
          genre = "Chamber Folk",
          mood = "Widescreen Spiritual",
          sonicProfile = SonicProfile(0.5f, 0.92f, 0.38f, 0.75f, 0.82f)
        )
      )
    )
  )

  val quickSuggestions: List<Pair<String, SeedType>> = listOf(
    "Radiohead" to SeedType.ARTIST,
    "Tame Impala" to SeedType.ARTIST,
    "Frank Ocean" to SeedType.ARTIST,
    "Beach House" to SeedType.ARTIST,
    "SZA" to SeedType.ARTIST,
    "Slowdive" to SeedType.ARTIST,
    "Phoebe Bridgers" to SeedType.ARTIST,
    "The 1975" to SeedType.ARTIST,
    "Fred again.." to SeedType.ARTIST,
    "Khruangbin" to SeedType.ARTIST,
    "Deftones" to SeedType.ARTIST,
    "Boygenius" to SeedType.ARTIST,
    "Alvvays" to SeedType.ARTIST,
    "Japanese Breakfast" to SeedType.ARTIST,
    "Kendrick Lamar" to SeedType.ARTIST,
    "Caroline Polachek" to SeedType.ARTIST,
    "Mac Miller" to SeedType.ARTIST,
    "Bon Iver" to SeedType.ARTIST,
    "Arctic Monkeys" to SeedType.ARTIST,
    "Little Simz" to SeedType.ARTIST,
    "Myth" to SeedType.SONG,
    "Borderline" to SeedType.SONG,
    "Nights" to SeedType.SONG,
    "Decks Dark" to SeedType.SONG,
    "Motion Sickness" to SeedType.SONG,
    "About You" to SeedType.SONG,
    "So Hot You're Hurting My Feelings" to SeedType.SONG,
    "Texas Sun" to SeedType.SONG,
    "Apocalypse" to SeedType.SONG,
    "Bags" to SeedType.SONG,
    "Archie, Marry Me" to SeedType.SONG
  )

  val catalogArtists: List<CatalogArtist> = listOf(
    CatalogArtist(
      id = "artist_men_i_trust",
      name = "Men I Trust",
      origin = "Montreal, Canada",
      genres = listOf("Dream Pop", "Indie Pop", "Chillwave"),
      sonicProfile = SonicProfile(energy = 0.48f, atmosphere = 0.86f, groove = 0.78f, acousticness = 0.45f, vocalPresence = 0.72f),
      vibeSummary = "Velvety basslines, whisper-soft vocals, and warm Rhodes piano woven into effortless late-night grooves.",
      sonicKeywords = listOf("bedroom pop", "velvet bass", "soft vocal", "analog synth", "rhodes"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Beach House", "Tame Impala", "Alvvays", "Crumb"),
      accentColorHex = 0xFF00E5C9,
      topTracks = listOf(
        SampleTrack("Show Me How", "A masterclass in restraint with smooth, rolling bass and dreamy guitars", "3:35", "Essential Gateway"),
        SampleTrack("Tailwhip", "Infectious indie-disco tempo paired with nostalgic melodies", "3:48", "Groovy Deep Cut"),
        SampleTrack("Sugar", "Warm vintage keyboard pads and intimate murmuring vocals", "2:56", "Late Night Vibe")
      )
    ),
    CatalogArtist(
      id = "artist_crumb",
      name = "Crumb",
      origin = "Brooklyn, NY",
      genres = listOf("Psychedelic Jazz", "Dream Pop", "Indie Rock"),
      sonicProfile = SonicProfile(energy = 0.58f, atmosphere = 0.88f, groove = 0.75f, acousticness = 0.4f, vocalPresence = 0.68f),
      vibeSummary = "Hypnotic, trippy blend of vintage surf-rock chords, odd-meter jazz drums, and narcotic vocal lines.",
      sonicKeywords = listOf("trippy", "jazz drums", "phased guitar", "locket", "hypnotic"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Tame Impala", "Men I Trust", "Mild High Club", "Unknown Mortal Orchestra"),
      accentColorHex = 0xFFA855F7,
      topTracks = listOf(
        SampleTrack("Locket", "Swirling psychedelic reverbs and sudden dynamic tempo shifts", "5:18", "Breakthrough Hit"),
        SampleTrack("Ghostride", "Dreamy lo-fi guitar lead with hypnotic laid-back drum bounce", "3:30", "Fan Favorite"),
        SampleTrack("Nina", "Noir jazz chords melting into fuzzy fuzz-pedal distortion", "3:22", "Sonic Journey")
      )
    ),
    CatalogArtist(
      id = "artist_overmono",
      name = "Overmono",
      origin = "Wales, UK",
      genres = listOf("UK Garage", "Electronic", "Breakbeat"),
      sonicProfile = SonicProfile(energy = 0.84f, atmosphere = 0.82f, groove = 0.92f, acousticness = 0.12f, vocalPresence = 0.58f),
      vibeSummary = "Crisp, emotive 2-step garage beats layered with pitched vocal fragments and cinematic bass weight.",
      sonicKeywords = listOf("ukg", "breaks", "emotive electronic", "club bass", "vocal chops"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Fred again..", "Bicep", "Four Tet", "Burial"),
      accentColorHex = 0xFF38BDF8,
      topTracks = listOf(
        SampleTrack("Good Lies", "Sublime vocal sampling over rolling UK garage syncopation", "3:04", "Club Anthem"),
        SampleTrack("So U Kno", "Hypnotic synth bass hook that defined modern underground dancefloors", "5:43", "Underground Classic"),
        SampleTrack("Walk Thru Water", "Lush ambient intro exploding into driving breakbeat rhythms", "4:12", "Atmospheric Peak")
      )
    ),
    CatalogArtist(
      id = "artist_wednesday",
      name = "Wednesday",
      origin = "Asheville, NC",
      genres = listOf("Indie Rock", "Country Gaze", "Alt-Country"),
      sonicProfile = SonicProfile(energy = 0.72f, atmosphere = 0.84f, groove = 0.52f, acousticness = 0.58f, vocalPresence = 0.88f),
      vibeSummary = "Abrasive lap steel guitars roaring through fuzz pedals, paired with storytelling as vivid as southern gothic literature.",
      sonicKeywords = listOf("country gaze", "fuzz lap steel", "raw screaming", "indie rock", "southern emo"),
      tier = DiscoveryDepth.BALANCED,
      similarTo = listOf("Phoebe Bridgers", "Boygenius", "Big Thief", "MJ Lenderman"),
      accentColorHex = 0xFFFF6B6B,
      topTracks = listOf(
        SampleTrack("Chosen to Deserve", "Twangy melodies and candid lyrics that burst into roaring guitar noise", "5:08", "Critically Acclaimed"),
        SampleTrack("Bull Believer", "An 8-minute epic journey ending in devastating shoegaze screams", "8:32", "Masterpiece"),
        SampleTrack("Quarry", "Haunting pedal steel textures narrating neighborhood micro-histories", "3:42", "Atmospheric Gem")
      )
    ),
    CatalogArtist(
      id = "artist_cleo_sol",
      name = "Cleo Sol",
      origin = "London, UK",
      genres = listOf("Neo-Soul", "Contemporary R&B", "Gospel-Soul"),
      sonicProfile = SonicProfile(energy = 0.44f, atmosphere = 0.82f, groove = 0.72f, acousticness = 0.7f, vocalPresence = 0.94f),
      vibeSummary = "Healing, deeply spiritual soul featuring pristine acoustic instrumentation and warm, maternal vocal presence.",
      sonicKeywords = listOf("neo-soul", "sault", "warm piano", "pure vocals", "healing r&b"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("SZA", "Frank Ocean", "Erykah Badu", "Jorja Smith"),
      accentColorHex = 0xFFFFB800,
      topTracks = listOf(
        SampleTrack("When I'm in Your Arms", "Soft finger-snaps, upright bass, and celestial vocal harmonies", "5:01", "Gateway Track"),
        SampleTrack("Sweet Blue", "Pure acoustic guitar introspection and soothing vocal warmth", "4:15", "Acoustic Soul"),
        SampleTrack("Know That You Are Loved", "Affirming lyrical poetry backed by lush gospel organ chords", "3:43", "Spiritual Anthem")
      )
    ),
    CatalogArtist(
      id = "artist_barrie",
      name = "Barrie",
      origin = "Brooklyn, NY",
      genres = listOf("Indie Pop", "Synthpop", "Dream Pop"),
      sonicProfile = SonicProfile(energy = 0.65f, atmosphere = 0.78f, groove = 0.74f, acousticness = 0.38f, vocalPresence = 0.82f),
      vibeSummary = "Sparkling crystalline synthesizers, upbeat motorik beats, and hook-laden melodies for sun-drenched road trips.",
      sonicKeywords = listOf("crystalline synths", "motorik", "bright pop", "alvvays vibe"),
      tier = DiscoveryDepth.BALANCED,
      similarTo = listOf("Alvvays", "Men I Trust", "The Japanese House", "Japanese Breakfast"),
      accentColorHex = 0xFF00E5C9,
      topTracks = listOf(
        SampleTrack("Clovers", "Bright driving bassline and shimmering layered vocal hooks", "3:32", "Infectious Pop"),
        SampleTrack("Frankie", "Vintage drum machines and lush 80s keyboard chords", "3:44", "Fan Favorite"),
        SampleTrack("Jenny", "Intimate, pulsing electronic folk-pop ballad", "3:20", "Dreamy Ballad")
      )
    ),
    CatalogArtist(
      id = "artist_geese",
      name = "Geese",
      origin = "Brooklyn, NY",
      genres = listOf("Art Punk", "Post-Punk", "Country Funk"),
      sonicProfile = SonicProfile(energy = 0.88f, atmosphere = 0.62f, groove = 0.76f, acousticness = 0.42f, vocalPresence = 0.9f),
      vibeSummary = "Unpredictable vocal acrobatics from falsetto to gravel, jagged guitars, and explosive art-rock grooves.",
      sonicKeywords = listOf("art rock", "wild vocals", "jagged guitars", "radiohead energy", "post-punk"),
      tier = DiscoveryDepth.DEEP_CUT,
      similarTo = listOf("Radiohead", "Black Midi", "Fontaines D.C.", "Talking Heads"),
      accentColorHex = 0xFFFF6B6B,
      topTracks = listOf(
        SampleTrack("3D Country", "Wild gospel backing chorus paired with galloping rock riffs", "5:15", "Showstopper"),
        SampleTrack("Cowboy Nudes", "Funky cowbell syncopation and thrilling falsetto vocal deliveries", "2:50", "High Energy"),
        SampleTrack("2122", "Dark apocalyptic post-punk explosion with blistering guitar solos", "3:48", "Raw Power")
      )
    ),
    CatalogArtist(
      id = "artist_faye_webster",
      name = "Faye Webster",
      origin = "Atlanta, GA",
      genres = listOf("Indie Pop", "Country / Pedal Steel", "Alt-R&B"),
      sonicProfile = SonicProfile(energy = 0.46f, atmosphere = 0.76f, groove = 0.78f, acousticness = 0.68f, vocalPresence = 0.86f),
      vibeSummary = "Effortless fusion of swooning Hawaiian pedal steel guitar, muted hip-hop inspired drums, and deadpan indie intimacy.",
      sonicKeywords = listOf("pedal steel", "hip-hop groove", "deadpan vocals", "indie folk", "sweet melody"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Phoebe Bridgers", "Clairo", "Men I Trust", "Boygenius"),
      accentColorHex = 0xFF38BDF8,
      topTracks = listOf(
        SampleTrack("Kingston", "Swooning pedal steel bends and the smoothest gentle bass groove", "3:22", "Signature Sound"),
        SampleTrack("Right Side of My Neck", "Sultry string arrangements meeting effortless indie nonchalance", "2:33", "Fan Favorite"),
        SampleTrack("In a Good Way", "Lush orchestral strings and heartfelt romantic optimism", "3:44", "String Epic")
      )
    ),
    CatalogArtist(
      id = "artist_four_tet",
      name = "Four Tet",
      origin = "London, UK",
      genres = listOf("Folktronica", "IDM", "Microhouse"),
      sonicProfile = SonicProfile(energy = 0.68f, atmosphere = 0.94f, groove = 0.85f, acousticness = 0.48f, vocalPresence = 0.35f),
      vibeSummary = "Intricate collage of organic harp arpeggios, found-sound field recordings, and undulating club kicks.",
      sonicKeywords = listOf("harp loops", "folktronica", "organic techno", "glitch", "ambient house"),
      tier = DiscoveryDepth.BALANCED,
      similarTo = listOf("Fred again..", "Bon Iver", "Radiohead", "Caribou"),
      accentColorHex = 0xFFA855F7,
      topTracks = listOf(
        SampleTrack("Two Thousand and Seventeen", "Hypnotic acoustic dulcimer loops over crunchy hip-hop drums", "4:12", "Timeless Vibe"),
        SampleTrack("Baby", "Glitchy, pitched vocal acrobatics with bouncing microhouse rhythms", "4:24", "Club Banger"),
        SampleTrack("Kool FM", "Raucous jungle rhythms mixed with playful sound design", "5:08", "Rhythm Odyssey")
      )
    ),
    CatalogArtist(
      id = "artist_nilufer_yanya",
      name = "Nilüfer Yanya",
      origin = "London, UK",
      genres = listOf("Indie Rock", "Post-Punk", "Art Pop"),
      sonicProfile = SonicProfile(energy = 0.74f, atmosphere = 0.72f, groove = 0.8f, acousticness = 0.44f, vocalPresence = 0.88f),
      vibeSummary = "Staccato fingerpicked electric guitar riffs, deep smoky vocals, and 90s alternative rock dynamism.",
      sonicKeywords = listOf("staccato guitar", "smoky vocals", "90s alt rock", "breakbeats", "post-punk"),
      tier = DiscoveryDepth.BALANCED,
      similarTo = listOf("The 1975", "Radiohead", "Japanese Breakfast", "St. Vincent"),
      accentColorHex = 0xFF00E5C9,
      topTracks = listOf(
        SampleTrack("stabilise", "Frantic breakbeat drums locked with razor-sharp guitar arpeggios", "3:32", "High Voltage"),
        SampleTrack("Midnight Sun", "Slow-burning groove culminating in fuzzy, grunge guitar catharsis", "4:42", "Fan Favorite"),
        SampleTrack("In Your Head", "Catchy hook over driving, distorted basslines and brisk drums", "3:28", "Indie Rock Hit")
      )
    ),
    CatalogArtist(
      id = "artist_charlotte_day_wilson",
      name = "Charlotte Day Wilson",
      origin = "Toronto, Canada",
      genres = listOf("Contemporary R&B", "Neo-Soul", "Indie Soul"),
      sonicProfile = SonicProfile(energy = 0.45f, atmosphere = 0.85f, groove = 0.7f, acousticness = 0.62f, vocalPresence = 0.96f),
      vibeSummary = "Smoky contralto vocals, church choir harmonies, and minimalist gospel-infused production with deep low end.",
      sonicKeywords = listOf("contralto", "minimalist soul", "choir harmonies", "analog warmth"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Frank Ocean", "SZA", "Daniel Caesar", "Cleo Sol"),
      accentColorHex = 0xFFFFB800,
      topTracks = listOf(
        SampleTrack("Work", "Iconic slow-burning gospel choir and heavy heartbeat kick drum", "3:44", "Breakout Masterpiece"),
        SampleTrack("Mountains", "A cappella choral intro transforming into a lush jazz-soul groove", "4:12", "Choral Soul"),
        SampleTrack("Take Care of You", "Warm Rhodes chords and tender duet harmonies", "3:38", "Intimate Gem")
      )
    ),
    CatalogArtist(
      id = "artist_yeule",
      name = "yeule",
      origin = "Singapore / London",
      genres = listOf("Glitch Pop", "Shoegaze", "Cyberpunk Ambient"),
      sonicProfile = SonicProfile(energy = 0.76f, atmosphere = 0.95f, groove = 0.58f, acousticness = 0.2f, vocalPresence = 0.74f),
      vibeSummary = "Post-human digital shoegaze where crushed 8-bit artifacts collide with crushing walls of grunge distortion.",
      sonicKeywords = listOf("glitch", "digital shoegaze", "cyberpunk", "ambient decay", "distorted beauty"),
      tier = DiscoveryDepth.DEEP_CUT,
      similarTo = listOf("Slowdive", "Caroline Polachek", "Deftones", "Grimes"),
      accentColorHex = 0xFFA855F7,
      topTracks = listOf(
        SampleTrack("sulky baby", "Heartbreaking 90s alt-rock guitar hook soaked in digital fuzz", "4:18", "Melodic Core"),
        SampleTrack("dazies", "Punishing industrial drums underneath ethereal whisper vocals", "3:42", "Digital Catharsis"),
        SampleTrack("Bites on My Neck", "Fast paced trance-pop rhythm distorted into pure sonic bliss", "4:49", "Cyber Club")
      )
    ),
    CatalogArtist(
      id = "artist_aroooj_aftab",
      name = "Arooj Aftab",
      origin = "Lahore, Pakistan / Brooklyn",
      genres = listOf("Neo-Sufi", "Ambient Jazz", "Chamber Folk"),
      sonicProfile = SonicProfile(energy = 0.3f, atmosphere = 0.98f, groove = 0.35f, acousticness = 0.92f, vocalPresence = 0.94f),
      vibeSummary = "Mesmerizing acoustic harp, upright bass, and ancient Urdu poetry sung with breathtaking stillness.",
      sonicKeywords = listOf("harp", "urdu poetry", "stillness", "grammy winner", "ambient acoustic"),
      tier = DiscoveryDepth.DEEP_CUT,
      similarTo = listOf("Bon Iver", "Phoebe Bridgers", "Radiohead", "Anoushka Shankar"),
      accentColorHex = 0xFF38BDF8,
      topTracks = listOf(
        SampleTrack("Mohabbat", "A spellbinding ballad of longing carried by acoustic harp and voice", "7:42", "Grammy Winner"),
        SampleTrack("Last Night", "Dub-reggae inspired bassline meeting delicate jazz flutes", "5:58", "Spiritual Groove"),
        SampleTrack("Baghon Main", "Trance-inducing modal melodies and subtle harmonium drones", "6:14", "Deep Meditation")
      )
    ),
    CatalogArtist(
      id = "artist_fontaines_dc",
      name = "Fontaines D.C.",
      origin = "Dublin, Ireland",
      genres = listOf("Post-Punk", "Alternative Rock", "Gothic Rock"),
      sonicProfile = SonicProfile(energy = 0.88f, atmosphere = 0.78f, groove = 0.74f, acousticness = 0.3f, vocalPresence = 0.92f),
      vibeSummary = "Poetic, brooding Irish lyrics delivered with punk snarling urgency over massive, chiming guitar hooks.",
      sonicKeywords = listOf("irish post-punk", "brooding", "chiming guitars", "dark romantic", "grunge energy"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Radiohead", "Deftones", "The 1975", "The Cure"),
      accentColorHex = 0xFFFF6B6B,
      topTracks = listOf(
        SampleTrack("Starburster", "Breathless, panic-attack vocal flow over hip-hop drum loops and strings", "3:41", "Modern Classic"),
        SampleTrack("I Love You", "Devastating political love letter built on an unstoppable bassline", "5:05", "Anthemic Peak"),
        SampleTrack("Favourite", "Bright, melancholic jangle-pop guitars evoking 80s The Cure", "4:16", "Jangle Romantic")
      )
    ),
    CatalogArtist(
      id = "artist_yussef_dayes",
      name = "Yussef Dayes",
      origin = "London, UK",
      genres = listOf("Contemporary Jazz", "Afrobeat", "Jazz Fusion"),
      sonicProfile = SonicProfile(energy = 0.78f, atmosphere = 0.82f, groove = 0.96f, acousticness = 0.78f, vocalPresence = 0.25f),
      vibeSummary = "Virtuosic, lightning-fast syncopated drumming that blends jungle rhythms, Senegalese percussion, and jazz keys.",
      sonicKeywords = listOf("virtuoso drums", "london jazz", "afro-fusion", "live instruments", "hypnotic groove"),
      tier = DiscoveryDepth.BALANCED,
      similarTo = listOf("Khruangbin", "Tame Impala", "Fred again..", "BADBADNOTGOOD"),
      accentColorHex = 0xFF00E5C9,
      topTracks = listOf(
        SampleTrack("Black Classical Music", "An exhilarating showcase of dynamic live drumming and trumpet", "5:18", "Tribute to Jazz"),
        SampleTrack("Tioga Pass", "Warm saxophone melodies gliding across polyrhythmic drum grooves", "4:56", "Cinematic Groove"),
        SampleTrack("Chasing the Drum", "Explosive live energy packed into an impossible syncopated loop", "6:24", "Drum Showcase")
      )
    ),
    CatalogArtist(
      id = "artist_alvvays_rec",
      name = "Fazerdaze",
      origin = "Auckland, New Zealand",
      genres = listOf("Dream Pop", "Indie Rock", "Fuzz Pop"),
      sonicProfile = SonicProfile(energy = 0.68f, atmosphere = 0.8f, groove = 0.68f, acousticness = 0.42f, vocalPresence = 0.84f),
      vibeSummary = "Bedroom fuzz guitars, sunny nostalgic vocal harmonies, and irresistible upbeat melody writing.",
      sonicKeywords = listOf("bedroom fuzz", "sunny melancholy", "catchy guitar hooks", "kiwi indie"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Alvvays", "Beach House", "Japanese Breakfast", "Phoebe Bridgers"),
      accentColorHex = 0xFF38BDF8,
      topTracks = listOf(
        SampleTrack("Lucky Girl", "Charming lo-fi guitar lead with an instantly unforgettable chorus", "2:49", "Indie Anthem"),
        SampleTrack("Break!", "Punchy 90s alt-pop energy with fuzzed-out bass and drum drive", "3:02", "Upbeat Energy"),
        SampleTrack("Cherry Pie", "Dreamy, reverb-soaked indie pop about longing and sunshine", "3:28", "Nostalgic Pop")
      )
    ),
    CatalogArtist(
      id = "artist_dijon",
      name = "Dijon",
      origin = "Los Angeles, CA",
      genres = listOf("Alternative R&B", "Indie Soul", "Folk-R&B"),
      sonicProfile = SonicProfile(energy = 0.72f, atmosphere = 0.75f, groove = 0.72f, acousticness = 0.7f, vocalPresence = 0.96f),
      vibeSummary = "Spontaneous living-room jam sessions captured with screaming soul vocals, slide guitars, and raw emotional friction.",
      sonicKeywords = listOf("raw live soul", "screaming falsetto", "slide guitar", "intimate jam", "frank ocean peer"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Frank Ocean", "Bon Iver", "SZA", "Mac Miller"),
      accentColorHex = 0xFFFF6B6B,
      topTracks = listOf(
        SampleTrack("Many Times", "Fast-paced acoustic frenzy ending in a joyful chaotic shout-along", "2:42", "Live Energy"),
        SampleTrack("The Dress", "Sexy, slinky 80s drum machine bounce and breathy confessions", "3:10", "Late Night Soul"),
        SampleTrack("Big Mike's", "Simmering slide guitar tension that erupts into cathartic gospel-style shouting", "4:32", "Emotional Tour-de-force")
      )
    ),
    CatalogArtist(
      id = "artist_slow_pulp",
      name = "Slow Pulp",
      origin = "Madison, WI / Chicago",
      genres = listOf("Indie Rock", "Shoegaze", "Dream Pop"),
      sonicProfile = SonicProfile(energy = 0.64f, atmosphere = 0.85f, groove = 0.62f, acousticness = 0.52f, vocalPresence = 0.82f),
      vibeSummary = "Airy acoustic strums colliding with walls of distorted guitars and intimate, confessional singing.",
      sonicKeywords = listOf("shoegaze rock", "airy vocals", "midwest emo touch", "distortion swell"),
      tier = DiscoveryDepth.CLOSE_MATCH,
      similarTo = listOf("Alvvays", "Slowdive", "Phoebe Bridgers", "Deftones"),
      accentColorHex = 0xFF00E5C9,
      topTracks = listOf(
        SampleTrack("Doubt", "Upbeat drum shuffle under whispery melancholic vocals", "3:15", "Catchy Hook"),
        SampleTrack("High", "Slow-burning fuzz anthem with swirling chorus effects", "3:24", "Fuzz Anthem"),
        SampleTrack("Slugs", "Sweet summery guitar arpeggios that explode into warm indie rock distortion", "3:46", "Fan Favorite")
      )
    )
  )
}
