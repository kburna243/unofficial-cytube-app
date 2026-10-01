package com.example.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/**
 * Die Farben der Oberflaeche liegen als beobachtbarer Zustand vor, nicht als Konstanten.
 *
 * Dadurch kostet ein Themenwechsel keine einzige Aenderung an den rund 300 Stellen, die diese
 * Namen benutzen: Compose merkt sich beim Zeichnen, wer welche Farbe gelesen hat, und zeichnet
 * genau diese Stellen neu, sobald [applyPalette] andere Werte einsetzt. Ein App-weites Thema
 * gibt es immer nur einmal, deshalb ist globaler Zustand hier richtig; ein CompositionLocal
 * waere derselbe Effekt mit 300 Zeilen Umbau.
 *
 * Geschrieben wird ausschliesslich ausserhalb der Composition — beim Start und beim Umschalten
 * in den Einstellungen. Waehrend des Zeichnens zu schreiben wuerde einen zusaetzlichen
 * Zeichendurchlauf ausloesen.
 */

// ------------------------------------------------------------------ Flaechen
var MidnightCanvas by mutableStateOf(Color(0xFF15121D))
    private set
var SurfaceDark by mutableStateOf(Color(0xFF1D1A26))
    private set
var SurfaceCard by mutableStateOf(Color(0xFF211E2A))
    private set
/** Fokussierte Kachel. Zieht zur Markenfarbe: tiefer Violettton mit Neon-Glow. */
var CardFocusedSurface by mutableStateOf(Color(0xFF2C1E4A))
    private set

// ------------------------------------------------------------------ Akzente
/**
 * Hauptakzent — Neon-Violett / Electric Amethyst (#9D65FF / #D4BBFF).
 */
var AccentIceBlue by mutableStateOf(Color(0xFF9D65FF))
    private set

/** Markenfarbe (#A675FF / #633AA8), fuer Logo, Systemzeilen und aktive Menuepunkte. */
var AccentVibrantOrange by mutableStateOf(Color(0xFFA675FF))
    private set
var AccentPurple by mutableStateOf(Color(0xFFA675FF))
    private set
var AccentLavender by mutableStateOf(Color(0xFFD4BBFF))
    private set
var AccentDeepViolet by mutableStateOf(Color(0xFF4F3085))
    private set

// Bleiben ueber alle Paletten gleich: sie unterscheiden Chat-Teilnehmer voneinander und
// duerfen dabei nicht mit der Markenfarbe verschmelzen.
val AccentCoral = Color(0xFFFF4A8D)
val AccentAmber = Color(0xFFFFB300)
val AccentNeonPink = Color(0xFFFF4A8D)
val AccentNeonGreen = Color(0xFF00E639)

// ------------------------------------------------------------------ Text
var PureWhite by mutableStateOf(Color(0xFFE7DFF0))
    private set
var TextSubtitleWhite by mutableStateOf(Color(0xFFFFFFFF))
    private set
var TextMuted by mutableStateOf(Color(0xFFCCC3D6))
    private set

// ------------------------------------------------------------------ Fokus & Rahmen
var FocusGlowIceBlue by mutableStateOf(Color(0xFF9D65FF))
    private set
var FocusBorderRing by mutableStateOf(Color(0xFFD4BBFF))
    private set
val SubtleBorder = Color(0x26FFFFFF)

// ------------------------------------------------------------------ Status
// Matrix Neon-Grün (#00E639) fuer Live-Indikator, aktive Auswahl und Fortschritt
val StatusLiveGreen = Color(0xFF00E639)
val StatusLiveGreenBg = Color(0x3300E639)
val StatusReconnectingYellow = Color(0xFFFBBF24)
val StatusOfflineRed = Color(0xFFF87171)
val StatusIdleBlue = Color(0xFF60A5FA)

// Klassische CyTube-Palette fuer die Chat-Namen (Alternative zur Grindhouse-Darstellung)
val ClassicCyan = Color(0xFF1E90FF)
val ClassicOrange = Color(0xFFFF4500)
val ClassicGreen = Color(0xFF00E639)
val ClassicAmber = Color(0xFFF39C12)
val ClassicSystem = Color(0xFFFFB300)

/** Ein vollstaendiges Farbthema. Die Kommentare nennen die Namen aus der Designvorlage. */
data class ThemePalette(
    val id: String,
    val background: Color,
    val surface: Color,
    val surfaceCard: Color,
    val cardFocused: Color,
    val accent: Color,
    val brand: Color,
    val lavender: Color,
    val deepViolet: Color,
    val textPrimary: Color,
    val textMuted: Color
)

/**
 * Die vier waehlbaren Themen. Vorgabe: Grindhouse Neon.
 */
val Palettes = listOf(
    // "Grindhouse Neon" — Tiefe obsidian-violette Basis mit leuchtenden Neon-Akzenten
    ThemePalette(
        id = "cinematic",
        background = Color(0xFF15121D),   // Surface
        surface = Color(0xFF1D1A26),      // Surface Container Low
        surfaceCard = Color(0xFF211E2A),  // Surface Container
        cardFocused = Color(0xFF2C1E4A),  // Focused Card
        accent = Color(0xFF9D65FF),       // Primary Electric Amethyst
        brand = Color(0xFFA675FF),        // Primary Container
        lavender = Color(0xFFD4BBFF),     // Surface Tint / Lavender
        deepViolet = Color(0xFF4F3085),   // Focused Violet
        textPrimary = Color(0xFFE7DFF0),  // On-Surface
        textMuted = Color(0xFFCCC3D6)     // On-Surface-Variant
    ),
    // "Premium Cyber Punk" — kraeftiger und kaelter, fuer Live-Events und Clips.
    ThemePalette(
        id = "cyberpunk",
        background = Color(0xFF0D0C15),   // Dark Eclipse
        surface = Color(0xFF1C182E),      // Cyber Velvet
        surfaceCard = Color(0xFF241F3A),
        cardFocused = Color(0xFF612898),
        accent = Color(0xFFE0AAFF),       // Neon Orchid
        brand = Color(0xFF7B2CBF),        // Royal Plum
        lavender = Color(0xFFB388FF),
        deepViolet = Color(0xFF5A1E90),
        textPrimary = Color(0xFFFFFFFF),  // Pure Ice
        textMuted = Color(0xFFA9A2BC)
    ),
    // "Mystic Editorial" — warmes Schwarzviolett, ruhig und erwachsen, fuer Dokus und Arthouse.
    ThemePalette(
        id = "editorial",
        background = Color(0xFF0E0911),   // Shadow Blackberry
        surface = Color(0xFF22162B),      // Muted Plum
        surfaceCard = Color(0xFF2A1C35),
        cardFocused = Color(0xFF523F62),
        accent = Color(0xFFC77DFF),       // Soft Lavender
        brand = Color(0xFF451E3E),        // Antique Violet
        lavender = Color(0xFFDDB8FF),
        deepViolet = Color(0xFF451E3E),
        textPrimary = Color(0xFFEAE6E8),  // Warm Ash
        textMuted = Color(0xFF9C919A)
    ),
    // Der bisherige Look, damit niemand das gewohnte Bild verliert.
    ThemePalette(
        id = "channelz",
        background = Color(0xFF050505),
        surface = Color(0xFF141418),
        surfaceCard = Color(0xFF1A1A22),
        cardFocused = Color(0xFF543174),
        accent = Color(0xFFA8C7FA),
        brand = Color(0xFF9D4EDD),
        lavender = Color(0xFFC4A8FF),
        deepViolet = Color(0xFF7B2CBF),
        textPrimary = Color(0xFFFFFFFF),
        textMuted = Color(0xFF9AA0A6)
    )
)

val DefaultPaletteId: String = Palettes.first().id

fun paletteOf(id: String): ThemePalette = Palettes.firstOrNull { it.id == id } ?: Palettes.first()

/** Setzt das Thema. Nur ausserhalb der Composition aufrufen (Start, Einstellungen). */
fun applyPalette(id: String) {
    val p = paletteOf(id)
    MidnightCanvas = p.background
    SurfaceDark = p.surface
    SurfaceCard = p.surfaceCard
    CardFocusedSurface = p.cardFocused
    AccentIceBlue = p.accent
    AccentVibrantOrange = p.brand
    AccentPurple = p.brand
    AccentLavender = p.lavender
    AccentDeepViolet = p.deepViolet
    PureWhite = p.textPrimary
    TextSubtitleWhite = p.textPrimary
    TextMuted = p.textMuted
    FocusGlowIceBlue = p.accent
    FocusBorderRing = p.lavender
}
