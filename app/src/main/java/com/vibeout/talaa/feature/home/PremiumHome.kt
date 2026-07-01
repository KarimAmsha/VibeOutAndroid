@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.vibeout.talaa.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.MoodType
import com.vibeout.talaa.ui.common.localizedName
import com.vibeout.talaa.ui.components.ChoiceChips
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import java.util.Locale

@Composable
fun PremiumHomeScreen(
    onOpenPlan: (String) -> Unit,
    onOpenPlaces: () -> Unit,
    onCreateVibe: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var selectedMood by remember { mutableStateOf<MoodType?>(null) }

    LaunchedEffect(state.generatedPlanId) {
        state.generatedPlanId?.let {
            onOpenPlan(it)
            viewModel.consumeNavigation()
        }
    }

    VibeScreen {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(Modifier.weight(1f)) {
                    Text(state.user?.firstName.orEmpty(), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(stringResource(R.string.mood_question), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                }
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.surface) {
                    IconButton(onClick = onCreateVibe) { Icon(Icons.Default.Add, stringResource(R.string.create_vibe)) }
                }
            }

            VibeHeroCard(
                eyebrow = state.user?.city?.localizedName(Locale.getDefault()) ?: stringResource(R.string.city),
                title = stringResource(R.string.mood_hint),
                body = stringResource(R.string.welcome_body),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
            )

            Row(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(stringResource(R.string.mood_question), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, modifier = Modifier.weight(1f))
                TextButton(onClick = onOpenPlaces) { Text(stringResource(R.string.places), fontWeight = FontWeight.Bold) }
            }

            LazyVerticalGrid(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                columns = GridCells.Adaptive(148.dp),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(MoodType.entries) { mood ->
                    VibeMoodCard(
                        title = moodLabel(mood),
                        icon = premiumMoodIcon(mood),
                        selected = selectedMood == mood,
                        onClick = { selectedMood = mood },
                        modifier = Modifier.height(122.dp),
                    )
                }
                item {
                    Card(
                        onClick = onOpenPlaces,
                        modifier = Modifier.height(122.dp),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {
                        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Surface(shape = CircleShape, color = BrandEnergy.copy(alpha = 0.14f)) {
                                Icon(Icons.Default.Place, null, tint = BrandEnergy, modifier = Modifier.padding(9.dp).size(22.dp))
                            }
                            Text(stringResource(R.string.places), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    selectedMood?.let { mood ->
        MoodPlanSheet(
            mood = mood,
            generating = state.generating,
            onDismiss = { if (!state.generating) selectedMood = null },
            onGenerate = { duration, min, max, distance, newPeople, note ->
                viewModel.generate(mood, duration, min, max, distance, newPeople, note)
            },
        )
    }
}

@Composable
private fun MoodPlanSheet(
    mood: MoodType,
    generating: Boolean,
    onDismiss: () -> Unit,
    onGenerate: (Int, Int?, Int?, Double?, Boolean, String?) -> Unit,
) {
    var duration by remember { mutableIntStateOf(120) }
    var budget by remember { mutableStateOf("MEDIUM") }
    var distance by remember { mutableStateOf("CITY") }
    var newPeople by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer) {
                    Icon(premiumMoodIcon(mood), null, Modifier.padding(14.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                }
                Column(Modifier.weight(1f)) {
                    Text(moodLabel(mood), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                    Text(stringResource(R.string.plan_customize_hint), color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
                }
            }

            PremiumSectionTitle(stringResource(R.string.duration))
            ChoiceChips(
                values = listOf(
                    "60" to stringResource(R.string.less_than_hour),
                    "120" to stringResource(R.string.two_hours),
                    "180" to stringResource(R.string.three_hours),
                    "360" to stringResource(R.string.half_day),
                ),
                selected = duration.toString(),
                onSelected = { duration = it.toInt() },
            )

            PremiumSectionTitle(stringResource(R.string.budget))
            ChoiceChips(
                values = listOf(
                    "LOW" to stringResource(R.string.budget_low),
                    "MEDIUM" to stringResource(R.string.budget_medium),
                    "OPEN" to stringResource(R.string.budget_open),
                ),
                selected = budget,
                onSelected = { budget = it },
            )

            PremiumSectionTitle(stringResource(R.string.distance))
            ChoiceChips(
                values = listOf(
                    "NEAR" to stringResource(R.string.very_close),
                    "CITY" to stringResource(R.string.inside_city),
                    "FAR" to stringResource(R.string.farther_ok),
                ),
                selected = distance,
                onSelected = { distance = it },
            )

            Surface(
                shape = RoundedCornerShape(18.dp),
                color = MaterialTheme.colorScheme.surface,
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(Icons.Default.Groups, null, tint = BrandEnergy)
                    Spacer(Modifier.width(12.dp))
                    Text(stringResource(R.string.meet_new_people), Modifier.weight(1f), fontWeight = FontWeight.Medium)
                    Switch(checked = newPeople, onCheckedChange = { newPeople = it })
                }
            }

            VibeTextField(
                value = note,
                onValueChange = { note = it },
                label = stringResource(R.string.optional_note),
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = Icons.Default.EditNote,
                singleLine = false,
            )

            VibePrimaryButton(
                text = stringResource(R.string.generate_plan),
                onClick = {
                    val budgets = when (budget) {
                        "LOW" -> 0 to 300
                        "MEDIUM" -> 200 to 700
                        else -> null to null
                    }
                    val km = when (distance) { "NEAR" -> 2.0; "CITY" -> 8.0; else -> 20.0 }
                    onGenerate(duration, budgets.first, budgets.second, km, newPeople, note.trim().takeIf { it.isNotEmpty() })
                },
                loading = generating,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

private fun premiumMoodIcon(mood: MoodType): ImageVector = when (mood) {
    MoodType.BORED -> Icons.Default.SentimentDissatisfied
    MoodType.GO_OUT -> Icons.Default.Explore
    MoodType.QUIET_COFFEE -> Icons.Default.Coffee
    MoodType.STUDY_WORK -> Icons.Default.Laptop
    MoodType.MEET_NEW_PEOPLE -> Icons.Default.Groups
    MoodType.WALK -> Icons.AutoMirrored.Filled.DirectionsWalk
    MoodType.FOOD -> Icons.Default.Restaurant
    MoodType.BUDGET_ACTIVITY -> Icons.Default.Savings
    MoodType.PHOTOGRAPHY -> Icons.Default.PhotoCamera
    MoodType.GROUP_VIBE -> Icons.Default.Celebration
    MoodType.NEW_EXPERIENCE -> Icons.Default.AutoAwesome
    MoodType.TODAY_EVENTS -> Icons.Default.Event
}
