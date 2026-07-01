@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.*
import com.vibeout.talaa.core.network.dto.GeneratePlanRequest
import com.vibeout.talaa.core.storage.AppPreferences
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val user: User? = null,
    val generating: Boolean = false,
    val generatedPlanId: String? = null,
    val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository,
    private val appPreferences: AppPreferences,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState(user = repository.currentUser.value))
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { repository.getMe() }.onSuccess { _state.value = _state.value.copy(user = it) }
        }
        // Keep the user (and thus the city used for plans) in sync with changes
        // made elsewhere, e.g. the city switcher in Settings.
        repository.currentUser
            .onEach { user -> if (user != null) _state.value = _state.value.copy(user = user) }
            .launchIn(viewModelScope)
    }

    fun generate(
        mood: MoodType,
        durationMinutes: Int,
        budgetMin: Int?,
        budgetMax: Int?,
        distanceKm: Double?,
        wantsNewPeople: Boolean,
        prompt: String?,
    ) {
        val cityId = _state.value.user?.city?.id ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(generating = true, error = null, generatedPlanId = null)
            runCatching {
                repository.generatePlan(
                    GeneratePlanRequest(
                        cityId = cityId,
                        mood = mood.name,
                        budgetMin = budgetMin,
                        budgetMax = budgetMax,
                        durationMinutes = durationMinutes,
                        preferredDistanceKm = distanceKm,
                        wantsNewPeople = wantsNewPeople,
                        prompt = prompt?.trim()?.takeIf { it.isNotEmpty() },
                    )
                )
            }.onSuccess {
                appPreferences.setLastMood(mood.name)
                _state.value = _state.value.copy(generating = false, generatedPlanId = it.id)
            }.onFailure {
                _state.value = _state.value.copy(generating = false, error = it.message)
            }
        }
    }

    fun consumeNavigation() { _state.value = _state.value.copy(generatedPlanId = null) }
}

@Composable
fun HomeScreen(
    onOpenPlan: (String) -> Unit,
    onOpenPlaces: () -> Unit,
    onCreateVibe: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var selectedMood by remember { mutableStateOf<MoodType?>(null) }

    LaunchedEffect(state.generatedPlanId) {
        state.generatedPlanId?.let { onOpenPlan(it); viewModel.consumeNavigation() }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateVibe) { Icon(Icons.Default.Add, stringResource(R.string.create_vibe)) }
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    state.user?.firstName?.let { stringResource(R.string.mood_question) } ?: stringResource(R.string.mood_question),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(Modifier.height(4.dp))
                Text(stringResource(R.string.mood_hint), style = MaterialTheme.typography.bodyLarge)
                state.error?.let {
                    Spacer(Modifier.height(8.dp))
                    AssistChip(onClick = {}, label = { Text(it) }, leadingIcon = { Icon(Icons.Default.ErrorOutline, null) })
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(MoodType.entries) { mood ->
                    ElevatedCard(
                        modifier = Modifier.height(116.dp).clickable { selectedMood = mood },
                    ) {
                        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Icon(moodIcon(mood), null, tint = MaterialTheme.colorScheme.primary)
                            Text(moodLabel(mood), style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
                item {
                    OutlinedCard(modifier = Modifier.height(116.dp).clickable(onClick = onOpenPlaces)) {
                        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
                            Icon(Icons.Default.Place, null, tint = MaterialTheme.colorScheme.secondary)
                            Text(stringResource(R.string.places), style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }

    selectedMood?.let { mood ->
        PlanOptionsSheet(
            mood = mood,
            loading = state.generating,
            onDismiss = { if (!state.generating) selectedMood = null },
            onGenerate = { duration, min, max, distance, newPeople, note ->
                viewModel.generate(mood, duration, min, max, distance, newPeople, note)
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlanOptionsSheet(
    mood: MoodType,
    loading: Boolean,
    onDismiss: () -> Unit,
    onGenerate: (Int, Int?, Int?, Double?, Boolean, String?) -> Unit,
) {
    var duration by remember { mutableIntStateOf(120) }
    var budget by remember { mutableStateOf("MEDIUM") }
    var distance by remember { mutableStateOf("CITY") }
    var newPeople by remember { mutableStateOf(false) }
    var note by remember { mutableStateOf("") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(20.dp)) {
            Text(moodLabel(mood), style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(18.dp))
            Text(stringResource(R.string.duration), style = MaterialTheme.typography.titleMedium)
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
            Spacer(Modifier.height(14.dp))
            Text(stringResource(R.string.budget), style = MaterialTheme.typography.titleMedium)
            ChoiceChips(
                values = listOf(
                    "LOW" to stringResource(R.string.budget_low),
                    "MEDIUM" to stringResource(R.string.budget_medium),
                    "OPEN" to stringResource(R.string.budget_open),
                ),
                selected = budget,
                onSelected = { budget = it },
            )
            Spacer(Modifier.height(14.dp))
            Text(stringResource(R.string.distance), style = MaterialTheme.typography.titleMedium)
            ChoiceChips(
                values = listOf(
                    "NEAR" to stringResource(R.string.very_close),
                    "CITY" to stringResource(R.string.inside_city),
                    "FAR" to stringResource(R.string.farther_ok),
                ),
                selected = distance,
                onSelected = { distance = it },
            )
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(checked = newPeople, onCheckedChange = { newPeople = it })
                Spacer(Modifier.width(10.dp))
                Text(stringResource(R.string.meet_new_people))
            }
            OutlinedTextField(note, { note = it }, modifier = Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.optional_note)) })
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = {
                    val budgets = when (budget) {
                        "LOW" -> 0 to 300
                        "MEDIUM" -> 200 to 700
                        else -> null to null
                    }
                    val km = when (distance) { "NEAR" -> 2.0; "CITY" -> 8.0; else -> 20.0 }
                    onGenerate(duration, budgets.first, budgets.second, km, newPeople, note)
                },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (loading) CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
                else Text(stringResource(R.string.generate_plan))
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

data class PlanUiState(val state: UiState<AiPlanResult> = UiState.Loading, val selecting: Boolean = false)

@HiltViewModel
class PlanResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val planId: String = checkNotNull(savedStateHandle["planId"])
    private val _state = MutableStateFlow(PlanUiState())
    val state: StateFlow<PlanUiState> = _state.asStateFlow()

    init { load() }
    fun load() = viewModelScope.launch {
        _state.value = PlanUiState(UiState.Loading)
        runCatching { repository.getPlan(planId) }
            .onSuccess { _state.value = PlanUiState(UiState.Success(it)) }
            .onFailure { _state.value = PlanUiState(UiState.Error(it.message ?: "")) }
    }
    fun select(index: Int) = viewModelScope.launch {
        _state.value = _state.value.copy(selecting = true)
        runCatching { repository.selectPlan(planId, index) }
            .onSuccess { _state.value = PlanUiState(UiState.Success(it), false) }
            .onFailure { _state.value = _state.value.copy(selecting = false) }
    }
}

@Composable
fun PlanResultScreen(
    onBack: () -> Unit,
    onOpenPlace: (String) -> Unit,
    onStartOuting: (placeId: String, mood: String?) -> Unit,
    viewModel: PlanResultViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = { VibeOutTopBar(stringResource(R.string.plan_results), onBack) },
    ) { padding ->
        when (val result = state.state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(result.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                if (result.data.plans.isEmpty()) EmptyPane(stringResource(R.string.no_plans), Modifier.padding(padding))
                else androidx.compose.foundation.lazy.LazyColumn(
                    Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    item {
                        Text(
                            stringResource(R.string.plan_pick_hint),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                    items(result.data.plans.size) { index ->
                        val plan = result.data.plans[index]
                        val firstPlaceId = plan.items.firstNotNullOfOrNull { it.placeId }
                        ElevatedCard(
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ),
                        ) {
                            Column(Modifier.padding(18.dp)) {
                                Text(plan.title, style = MaterialTheme.typography.titleLarge)
                                plan.summary?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
                                if (plan.estimatedCostMin != null || plan.estimatedCostMax != null) {
                                    Spacer(Modifier.height(8.dp))
                                    Text("${stringResource(R.string.estimated_cost)}: ${plan.estimatedCostMin ?: 0}–${plan.estimatedCostMax ?: 0} TL")
                                }
                                Spacer(Modifier.height(10.dp))
                                plan.items.forEach { item ->
                                    ListItem(
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                        headlineContent = { Text(item.title) },
                                        supportingContent = { item.reason?.let { Text(it) } },
                                        leadingContent = {
                                            if (item.time != null) Text(item.time, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                                            else Icon(Icons.Default.Schedule, null)
                                        },
                                        trailingContent = {
                                            item.placeId?.let { id -> IconButton(onClick = { onOpenPlace(id) }) { Icon(Icons.Default.ChevronRight, null) } }
                                        }
                                    )
                                }
                                Spacer(Modifier.height(6.dp))
                                Button(
                                    onClick = { onStartOuting(firstPlaceId ?: "none", result.data.mood) },
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Icon(Icons.Default.Groups, null, Modifier.size(18.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text(stringResource(R.string.start_outing_from_plan))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun moodLabel(mood: MoodType): String = stringResource(
    when (mood) {
        MoodType.BORED -> R.string.mood_bored
        MoodType.GO_OUT -> R.string.mood_go_out
        MoodType.QUIET_COFFEE -> R.string.mood_quiet_coffee
        MoodType.STUDY_WORK -> R.string.mood_study_work
        MoodType.MEET_NEW_PEOPLE -> R.string.mood_meet_people
        MoodType.WALK -> R.string.mood_walk
        MoodType.FOOD -> R.string.mood_food
        MoodType.BUDGET_ACTIVITY -> R.string.mood_budget_activity
        MoodType.PHOTOGRAPHY -> R.string.mood_photography
        MoodType.GROUP_VIBE -> R.string.mood_group
        MoodType.NEW_EXPERIENCE -> R.string.mood_new_experience
        MoodType.TODAY_EVENTS -> R.string.mood_today_events
    }
)

private fun moodIcon(mood: MoodType) = when (mood) {
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
