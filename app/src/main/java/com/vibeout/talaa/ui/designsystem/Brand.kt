package com.vibeout.talaa.ui.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import com.vibeout.talaa.ui.theme.BrandNight

@Composable
fun VibeLogoMark(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(20.dp)).background(containerColor),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(Modifier.fillMaxSize().padding(14.dp)) {
            val stroke = size.minDimension * 0.12f
            val route = Path().apply {
                moveTo(size.width * 0.14f, size.height * 0.74f)
                cubicTo(size.width * 0.34f, size.height * 0.67f, size.width * 0.34f, size.height * 0.38f, size.width * 0.60f, size.height * 0.34f)
                cubicTo(size.width * 0.73f, size.height * 0.31f, size.width * 0.82f, size.height * 0.20f, size.width * 0.87f, size.height * 0.11f)
            }
            drawPath(route, contentColor, style = Stroke(stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
            val launch = Path().apply {
                moveTo(size.width * 0.58f, size.height * 0.14f)
                lineTo(size.width * 0.89f, size.height * 0.09f)
                lineTo(size.width * 0.80f, size.height * 0.40f)
            }
            drawPath(launch, contentColor, style = Stroke(stroke, cap = StrokeCap.Round, join = StrokeJoin.Round))
            drawCircle(BrandEnergy, size.minDimension * 0.09f, Offset(size.width * 0.34f, size.height * 0.65f))
            drawCircle(contentColor, size.minDimension * 0.025f, Offset(size.width * 0.34f, size.height * 0.65f))
        }
    }
}

@Composable
fun VibeScreen(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val primaryGlow = MaterialTheme.colorScheme.primary.copy(alpha = 0.035f)
    Box(modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(primaryGlow, size.minDimension * 0.42f, Offset(size.width, 0f))
            drawCircle(BrandEnergy.copy(alpha = 0.035f), size.minDimension * 0.30f, Offset(0f, size.height))
        }
        content()
    }
}

@Composable
fun VibeLogoLockup(
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    lightOnDark: Boolean = false,
) {
    val main = if (lightOnDark) Color.White else MaterialTheme.colorScheme.onBackground
    val support = if (lightOnDark) Color.White.copy(alpha = 0.70f) else MaterialTheme.colorScheme.onSurfaceVariant
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        VibeLogoMark(
            Modifier.size(58.dp),
            containerColor = if (lightOnDark) Color.White else MaterialTheme.colorScheme.primary,
            contentColor = if (lightOnDark) BrandNight else MaterialTheme.colorScheme.onPrimary,
        )
        Column {
            Text(title, color = main, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
            subtitle?.let { Text(it, color = support, style = MaterialTheme.typography.bodyMedium) }
        }
    }
}

@Composable
fun VibeHeroCard(
    eyebrow: String,
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = BrandNight),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Box(Modifier.fillMaxWidth()) {
            Canvas(Modifier.matchParentSize()) {
                drawCircle(BrandEnergy.copy(alpha = 0.22f), size.width * 0.30f, Offset(size.width, 0f))
                drawCircle(BrandMint.copy(alpha = 0.14f), size.width * 0.23f, Offset(0f, size.height))
            }
            Column(Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(eyebrow, color = BrandMint, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(title, color = Color.White, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text(body, color = Color.White.copy(alpha = 0.72f), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun VibeMoodCard(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val container = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val content = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = container),
        border = if (selected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Surface(
                shape = CircleShape,
                color = if (selected) Color.White.copy(alpha = 0.14f) else MaterialTheme.colorScheme.primaryContainer,
            ) {
                Icon(icon, null, tint = if (selected) Color.White else MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(9.dp).size(22.dp))
            }
            Text(title, color = content, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}
