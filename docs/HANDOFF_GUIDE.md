# Handoff-Dokumentation: CyTube „Grindhouse Neon“ Redesign

Dieses Dokument dient als technische und gestalterische Referenz für die Implementierung des CyTube-App Redesigns.

## 1. Design-System: Grindhouse Neon
**Konzept:** High-Octane Cyberpunk, Retro-VHS Ästhetik, tiefe Kontraste mit lebendigen Neon-Akzenten.

### Farbpalette (Design System {{DATA:DESIGN_SYSTEM:DESIGN_SYSTEM_1}})
- **Surface (Hintergrund):** `#15121d` (Tiefes Dunkelviolett/Schwarz)
- **Primary (Akzent 1):** `#9d65ff` (Neon-Violett/Pink) - Nutzung für Branding, Rahmen, primäre Call-to-Actions.
- **Tertiary (Akzent 2):** `#00e639` (Neon-Grün) - Nutzung für Live-Status, aktive Auswahl, Fortschrittsbalken.
- **On-Surface:** `#ffffff` (Reinweiß) - Für maximale Lesbarkeit auf dunklem Grund.
- **Surface-Bright:** `#3b3744` - Für abgesetzte Container und Karten.

### Typografie
- **Font-Family:** `Sora` (Google Fonts)
- **Headlines:** Extrabold, tracking-tighter, oft mit subtilem Text-Shadow (`drop-shadow-[0_0_8px_rgba(212,187,255,0.6)]`).
- **Body/Metadata:** Regular bis Medium, klare serifenlose Hierarchie.

---

## 2. UI-Komponenten & Screens

### Loading Screen (Landscape) - {{DATA:SCREEN:SCREEN_2}}
- **Hintergrund:** Collage-Artwork {{DATA:IMAGE:IMAGE_3}} mit einem weichen radialen Verlauf zum Rand hin (`bg-gradient-to-b from-transparent to-surface/80`).
- **Progress Bar:** Hexagonale Form, dicker neon-violetter Rahmen. Segmente sind schräge Blöcke in Neon-Grün mit intensivem Glow-Effekt.
- **Shader:** Dynamischer Hintergrund-Effekt für zusätzliche Textur (simuliert Rauschen/VHS-Artefakte).

### Channel Selection Hub - {{DATA:SCREEN:SCREEN_9}}
- **Karten-Layout:** Zweispaltiges Grid.
- **Interaktions-Status:** Aktive Auswahl durch einen neon-pinken Rahmen (`ring-2 ring-primary shadow-[0_0_15px_rgba(157,101,255,0.5)]`) hervorgehoben.
- **Glassmorphism:** Karten nutzen `backdrop-blur-md` und eine leichte Transparenz auf dem dunklen Untergrund.

### Live Player HUD - {{DATA:SCREEN:SCREEN_10}}
- **Overlay:** Minimalistisches Interface über dem Video-Stream.
- **Live-Indikator:** Pulsierender neon-grüner Punkt neben dem Zuschauer-Zähler.
- **Seekbar:** Neon-violette Progress-Linie.

### Settings & Details - {{DATA:SCREEN:SCREEN_6}}, {{DATA:SCREEN:SCREEN_8}}
- **Struktur:** Card-in-Card Layout für klare Informationsarchitektur.
- **Toggles/Buttons:** Konsistente Nutzung von abgerundeten Ecken (`ROUND_EIGHT`).

---

## 3. Grafische Assets
- **Banner (16:9):** {{DATA:IMAGE:IMAGE_4}} - Zentrales Logo vor dichter Grindhouse-Collage.
- **App-Icon (3:2):** {{DATA:IMAGE:IMAGE_5}} - Fokus auf das "CyTube APP" Branding für maximale Erkennbarkeit.

---

## 4. Implementierungshinweise für den Agenten
1. **Glow-Effekte:** Exzessive Nutzung von CSS `box-shadow` und `drop-shadow` für alle Neon-Elemente.
2. **Kontrast:** Vermeidung von grauen Zwischentönen; harte Übergänge zwischen tiefem Schwarz und leuchtenden Farben bevorzugen.
3. **Responsivität:** Fokus auf Desktop/Landscape (Querformat) als primäres Target, wie in {{DATA:SCREEN:SCREEN_2}} definiert.
