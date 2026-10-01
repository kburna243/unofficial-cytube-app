---
name: Grindhouse Neon
colors:
  surface: '#15121d'
  surface-dim: '#15121d'
  surface-bright: '#3b3744'
  surface-container-lowest: '#100c18'
  surface-container-low: '#1d1a26'
  surface-container: '#211e2a'
  surface-container-high: '#2c2834'
  surface-container-highest: '#373340'
  on-surface: '#e7dff0'
  on-surface-variant: '#ccc3d6'
  inverse-surface: '#e7dff0'
  inverse-on-surface: '#322e3b'
  outline: '#968da0'
  outline-variant: '#4a4454'
  surface-tint: '#d4bbff'
  primary: '#d4bbff'
  on-primary: '#40008c'
  primary-container: '#a675ff'
  on-primary-container: '#38007b'
  inverse-primary: '#7438d4'
  secondary: '#ffb1c4'
  on-secondary: '#65002e'
  secondary-container: '#ff4a8d'
  on-secondary-container: '#590028'
  tertiary: '#00e639'
  on-tertiary: '#003907'
  tertiary-container: '#00a827'
  on-tertiary-container: '#003205'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#ebdcff'
  primary-fixed-dim: '#d4bbff'
  on-primary-fixed: '#260058'
  on-primary-fixed-variant: '#5b11bb'
  secondary-fixed: '#ffd9e1'
  secondary-fixed-dim: '#ffb1c4'
  on-secondary-fixed: '#3f001a'
  on-secondary-fixed-variant: '#8f0044'
  tertiary-fixed: '#72ff70'
  tertiary-fixed-dim: '#00e639'
  on-tertiary-fixed: '#002203'
  on-tertiary-fixed-variant: '#00530e'
  background: '#15121d'
  on-background: '#e7dff0'
  surface-variant: '#373340'
typography:
  display-lg:
    fontFamily: Sora
    fontSize: 28px
    fontWeight: '800'
    lineHeight: 34px
    letterSpacing: 0.5px
  headline-md:
    fontFamily: Sora
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
  title-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '700'
    lineHeight: 24px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 22px
  label-caps:
    fontFamily: JetBrains Mono
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 1px
  chat-subtitle:
    fontFamily: Inter
    fontSize: 15px
    fontWeight: '600'
    lineHeight: 20px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  unit: 4px
  xs: 4px
  sm: 8px
  md: 12px
  base: 16px
  lg: 24px
  xl: 32px
  gutter: 16px
  margin-mobile: 16px
  margin-desktop: 48px
---

## Brand & Style

The design system is a high-energy fusion of **Retro-Futurism** and **Grindhouse Cinema**. It draws heavily from the 80s VHS aesthetic, specifically the gritty, detailed, and vibrant visuals of underground midnight screenings. The target audience consists of digital natives and subculture enthusiasts who value community-driven video streaming.

The UI should evoke a sense of **electrified immersion**. It balances a "dirty" textured backdrop with "clean" high-tech neon overlays. By mixing **Glassmorphism** with **Retro / Vaporwave** elements, the interface feels like a sophisticated head-up display (HUD) found inside a futuristic projection booth.

**Key Visual Pillars:**
- **Atmospheric Depth:** Using smoky violet-black gradients instead of flat blacks to prevent visual clipping.
- **Phosphorescent Accents:** Utilizing high-vibrancy neons (Pink, Green, Cyan) to guide the eye and signify active states.
- **Cinematic Texture:** Subtle use of scanlines, grain, or glowing borders to maintain the "Grindhouse" narrative.

## Colors

The palette is built on a "Midnight Obsidian" base to ensure that video content remains the focal point while neon elements "pop" with maximum luminance.

- **Primary (Electric Amethyst):** Used for primary actions and focus states.
- **Secondary (Retro Synth Pink):** Reserved for brand moments, featured highlights, and secondary CTAs.
- **Tertiary (Matrix Green):** Exclusively for "Live" indicators, progress bars, and success states.
- **Neutral:** A range of deep violets and smoky grays to build hierarchy without losing the thematic tint.
- **Glass Accents:** Semi-transparent layers (`#161124EB`) provide a "HUD" feel for overlays and navigation.

## Typography

The typographic system utilizes **Sora** for its geometric, futuristic personality in headings, while **Inter** ensures high legibility for dense chat streams and metadata. **JetBrains Mono** is used for technical labels and timestamps to reinforce the "terminal" or "broadcast deck" aesthetic.

**Mobile Considerations:**
- Headline sizes are capped at 28px to ensure room titles don't wrap aggressively on small screens.
- Chat subtitles use a semi-bold weight and a subtle text-shadow (defined in the components section) to remain legible over varied video backgrounds.
- Letter spacing is increased on labels to improve glanceability in low-light environments.

## Layout & Spacing

The design system follows a **fluid grid** model optimized for mobile-first consumption.

- **Mobile:** Single column layout with a fixed 16:9 video container at the top. Content uses 16px side margins.
- **Grid System:** A modular 4px-base rhythm governs all padding and margins.
- **Information Density:** Spacing is kept compact (`8px` to `12px` between elements) to allow users to see more chat history and queue items simultaneously.
- **Safe Areas:** For TV or landscape modes, a 5% "overscan" margin is applied to all HUD elements to ensure controls are not clipped by device corners or notches.

## Elevation & Depth

Visual hierarchy is achieved through **Tonal Layering** and **Glassmorphism**, avoiding traditional "realistic" drop shadows in favor of ambient neon glows.

- **Layering:** Backgrounds are the deepest obsidian. Content cards (`#1E1830`) sit slightly above. Active/Focused cards use a lighter violet (`#4F3085`) to "lift" them toward the user.
- **Glows:** Instead of black shadows, use color-tinted outer glows. For example, a focused button should have a soft `2px` blur glow using its primary neon color.
- **Backdrop Blurs:** Navigation bars and modal sheets must use a `20px` backdrop blur with 92% opacity. This maintains the "Grindhouse" atmosphere by allowing the background textures and video movement to bleed through subtly.

## Shapes

The shape language is **Rounded**, striking a balance between modern software and the curved corners of vintage television tubes.

- **Standard Radius:** 0.5rem (8px) for input fields and small buttons.
- **Large Radius:** 1rem (16px) for main content cards and channel tiles.
- **Pill Shapes:** Used exclusively for status indicators (e.g., "LIVE" badges) and filter chips to distinguish them from actionable buttons.

## Components

### Buttons
- **Neon Primary:** Solid `#9D65FF` background with `#0B0813` text. On focus, add a 4px glow of `#C4A8FF`.
- **Grindhouse Ghost:** Transparent background with a `1.5px` border of `#FFFFFF26`. Text is white.

### Cards (Channel/Video)
- **Container:** `#1E1830` with a subtle `1px` stroke of `#FFFFFF15`. 
- **Focus State:** Stroke changes to `#9D65FF` or `#00FF41` with a slight scale-up (`1.04x`).

### Chat & Subtitles
- **Messages:** Text uses `#F5F3F7`. Usernames should be color-coded based on roles (Pink, Cyan, or Amber).
- **Video Overlay:** Chat appearing over video must have a `SurfaceDark` (60% alpha) capsule background and a `2px` black blur shadow on the text.

### Inputs
- **Fields:** Deep violet background (`#1E1830`) with a `1.5px` bottom-only or all-around border that glows when focused.

### Status Indicators
- **Live Badge:** Matrix Green (`#00FF41`) text on a 20% opacity green background. Must include a pulsing animation to indicate "active" state.