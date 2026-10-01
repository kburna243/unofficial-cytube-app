package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AccentNeonGreen
import com.example.ui.theme.AccentNeonPink
import com.example.ui.theme.MidnightCanvas
import com.example.ui.theme.PureWhite
import com.example.ui.theme.StatusLiveGreen
import kotlinx.coroutines.delay

/**
 * Hexagonal Capsule Shape for Grindhouse Neon progress indicator
 * polygon(5% 0%, 95% 0%, 100% 50%, 95% 100%, 5% 100%, 0% 5%)
 */
class HexagonCapsuleShape(private val cornerFraction: Float = 0.05f) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            val cutX = size.width * cornerFraction
            val midY = size.height * 0.5f
            moveTo(cutX, 0f)
            lineTo(size.width - cutX, 0f)
            lineTo(size.width, midY)
            lineTo(size.width - cutX, size.height)
            lineTo(cutX, size.height)
            lineTo(0f, midY)
            close()
        }
        return Outline.Generic(path)
    }
}

/**
 * Animated Grindhouse Neon Splash & Loading Screen.
 * Implements the design from loading_screen_soft_background (hexagonal glowing frame,
 * skewed matrix-green segments, pulsing neon logo and SYS_BOOT counter).
 */
@Composable
fun SplashScreenView(
    onFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = if (startAnimation) 1.0f else 0.0f,
        animationSpec = tween(durationMillis = 2400, easing = LinearEasing),
        label = "splashProgress"
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2700)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MidnightCanvas),
        contentAlignment = Alignment.Center
    ) {
        // 1. Splash Screen Main Background Artwork
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = "CyTube App Splash Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.65f
        )

        // 2. Soft Dark Vignette Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.70f),
                            Color.Black.copy(alpha = 0.30f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        // 3. CRT Television Scanlines Overlay
        ScanlinesOverlay(modifier = Modifier.fillMaxSize())

        // 4. Moving Scanline Bar
        ScanlineBarOverlay(modifier = Modifier.fillMaxSize())

        // 5. Center Neon Boot Unit
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Pulsing CyTube App Logo
            val infiniteTransition = rememberInfiniteTransition(label = "neonLogoPulse")
            val logoScale by infiniteTransition.animateFloat(
                initialValue = 0.96f,
                targetValue = 1.04f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "logoScale"
            )
            val logoAlpha by infiniteTransition.animateFloat(
                initialValue = 0.85f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1200, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "logoAlpha"
            )

            Image(
                painter = painterResource(id = R.drawable.splash_loading_text),
                contentDescription = "CyTube Logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(135.dp)
                    .drawBehind {
                        // Ambient Neon Magenta Glow
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color(0x99D800FF), Color.Transparent)
                            ),
                            radius = size.width * 0.65f,
                            center = center
                        )
                    }
                    .graphicsLayer {
                        scaleX = logoScale
                        scaleY = logoScale
                        alpha = logoAlpha
                    }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Subtitle: INITIALIZING NEON CORE...
            val textFlickerAlpha by infiniteTransition.animateFloat(
                initialValue = 0.75f,
                targetValue = 1.0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(350, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "textFlickerAlpha"
            )

            Text(
                text = "INITIALIZING NEON CORE...",
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    color = StatusLiveGreen.copy(alpha = textFlickerAlpha),
                    letterSpacing = 3.sp
                )
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Hexagonal Progress Bar Unit
            HexagonNeonProgressBar(
                progress = animatedProgress,
                modifier = Modifier
                    .fillMaxWidth(0.50f)
                    .height(44.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // SYS_BOOT: X% in Monospace Bold
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SYS_BOOT: ",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = StatusLiveGreen,
                        letterSpacing = 1.5.sp
                    )
                )
                Text(
                    text = "${(animatedProgress * 100).toInt()}%",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 15.sp,
                        color = StatusLiveGreen,
                        letterSpacing = 1.5.sp
                    )
                )
            }
        }

        // 6. Bottom VCR Audio & Status Indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 36.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "[PLAY ▶]",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = StatusLiveGreen.copy(alpha = 0.9f)
                )
            )
            Text(
                text = "STEREO • HI-FI // 24/7 SYNC",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00DDFF).copy(alpha = 0.75f)
                )
            )
        }
    }
}

/**
 * Custom Hexagonal Neon Progress Bar with skewed matrix green blocks
 */
@Composable
fun HexagonNeonProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val borderColor = Color(0xFFD800FF)
    val segmentColor = Color(0xFF00FF41)

    Box(
        modifier = modifier
            .background(Color(0xE60A0612), RoundedCornerShape(10.dp))
            .border(2.5.dp, borderColor, RoundedCornerShape(10.dp))
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val clampedProgress = progress.coerceIn(0f, 1f)
            val fillWidth = size.width * clampedProgress
            val segmentWidth = 18.dp.toPx()
            val segmentSpacing = 5.dp.toPx()
            val skewOffset = 8.dp.toPx()

            clipRect(0f, 0f, fillWidth, size.height) {
                var currentX = 0f
                while (currentX < size.width + skewOffset) {
                    val segmentPath = Path().apply {
                        moveTo(currentX + skewOffset, 0f)
                        lineTo(currentX + segmentWidth + skewOffset, 0f)
                        lineTo(currentX + segmentWidth, size.height)
                        lineTo(currentX, size.height)
                        close()
                    }
                    drawPath(
                        path = segmentPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF85FF83),
                                segmentColor,
                                Color(0xFF00A827)
                            )
                        )
                    )
                    currentX += segmentWidth + segmentSpacing
                }
            }
        }
    }
}

/**
 * Scanlines visual overlay replicating vintage CRT television scanlines.
 */
@Composable
fun ScanlinesOverlay(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val scanlineSpacing = 4.dp.toPx()
        var y = 0f
        while (y < size.height) {
            drawLine(
                color = Color.Black.copy(alpha = 0.20f),
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = 1.5f
            )
            y += scanlineSpacing
        }
    }
}

/**
 * Subtle moving scanline bar animation
 */
@Composable
fun ScanlineBarOverlay(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "scanlineBar")
    val yPos by transition.animateFloat(
        initialValue = -0.1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "yPos"
    )

    Canvas(modifier = modifier) {
        val barHeight = 60.dp.toPx()
        val currentY = size.height * yPos
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0x1A9D65FF),
                    Color(0x269D65FF),
                    Color.Transparent
                ),
                startY = currentY,
                endY = currentY + barHeight
            ),
            topLeft = Offset(0f, currentY),
            size = Size(size.width, barHeight)
        )
    }
}

/**
 * Backwards compatibility alias for ChannelZRetroLoadingBar.
 */
@Composable
fun ChannelZRetroLoadingBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    HexagonNeonProgressBar(progress = progress, modifier = modifier)
}

@Composable
fun AnimatedRetroLoadingBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    HexagonNeonProgressBar(progress = progress, modifier = modifier)
}
