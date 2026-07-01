@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.vibes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.*
import com.vibeout.talaa.core.network.dto.CreateVibeRequest
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.feature.home.moodLabel
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.common.localizedName
import com.vibeout.talaa.ui.components.ChoiceChips
import com.vibeout.talaa.ui.components.ConfirmDialog
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VibesViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Vibe>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Vibe>>> = _state.asStateFlow()

    init {
        repository.currentUser
            .map { it?.city?.id }
            .distinctUntilChanged()
            .onEach { load() }
            .launchIn(viewModelScope)
    }

    fun load() = viewModelScope.launch {
        _state.value = UiState.Loading
        runCatching { repository.getVibes(repository.currentUser.value?.city?.id) }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }
}

@Composable
fun VibesScreen(
    onOpen: (String) -> Unit,
    onCreate: () -> Unit,
    viewModel: VibesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.vibes)) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onCreate,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.create_vibe), fontWeight = FontWeight.Bold) },
                shape = RoundedCornerShape(18.dp),
            )
        },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            when (val content = state) {
                UiState.Loading, UiState.Idle -> PremiumLoadingState(message = stringResource(R.string.loading))
                is UiState.Error -> PremiumErrorState(
                    content.message,
                    stringResource(R.string.retry),
                    viewModel::load,
                )
                is UiState.Success -> {
                    if (content.data.isEmpty()) {
                        PremiumEmptyState(
                            title = stringResource(R.string.empty_state),
                            icon = Icons.Default.Groups,
                            actionLabel = stringResource(R.string.create_vibe),
                            onAction = onCreate,
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(start = 20.dp, top = 12.dp, end = 20.dp, bottom = 100.dp),
                            verticalArrangement = Arrangement.spacedBy(14.dp),
                        ) {
                            item {
                                PremiumAccentCard(
                                    title = stringResource(R.string.open_vibes),
                                    body = stringResource(R.string.welcome_body),
                                    icon = Icons.Default.Celebration,
                                )
                            }
                            items(content.data, key = { it.id }) { vibe ->
                                PremiumVibeCard(vibe = vibe, onClick = { onOpen(vibe.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumVibeCard(vibe: Vibe, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Surface(shape = CircleShape, color = BrandEnergy.copy(alpha = 0.14f)) {
                    Icon(
                        Icons.Default.Groups,
                        contentDescription = null,
                        tint = BrandEnergy,
                        modifier = Modifier.padding(11.dp),
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        vibe.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        listOfNotNull(vibe.meetingArea, vibe.place?.name).joinToString(" • "),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                PremiumStatusChip(vibe.status.replace('_', ' '))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PremiumStatusChip("${vibe.approvedParticipantsCount}/${vibe.maxPeople}", color = BrandMint)
                PremiumStatusChip(vibe.language.uppercase(Locale.ROOT))
            }

            PremiumInfoRow(
                icon = Icons.Default.Schedule,
                title = formatServerDate(vibe.startTime),
                subtitle = vibe.creator?.displayName ?: stringResource(R.string.organizer),
            )
        }
    }
}

data class CreateVibeUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val createdId: String? = null,
)

@HiltViewModel
class CreateVibeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    val initialPlaceId: String? = savedStateHandle.get<String>("placeId")?.takeIf { it != "none" }
    val initialMood: String? = savedStateHandle.get<String>("mood")?.takeIf { it != "none" }
    val city: City? = repository.currentUser.value?.city
    val cityId: String? = city?.id
    private val _state = MutableStateFlow(CreateVibeUiState())
    val state: StateFlow<CreateVibeUiState> = _state.asStateFlow()

    fun create(request: CreateVibeRequest) = viewModelScope.launch {
        _state.value = CreateVibeUiState(loading = true)
        runCatching { repository.createVibe(request) }
            .onSuccess { _state.value = CreateVibeUiState(createdId = it.id) }
            .onFailure { _state.value = CreateVibeUiState(error = it.message) }
    }
}

@Composable
fun CreateVibeScreen(
    onBack: () -> Unit,
    onCreated: (String) -> Unit,
    viewModel: CreateVibeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var mood by rememberSaveable { mutableStateOf(viewModel.initialMood ?: MoodType.GROUP_VIBE.name) }
    var meetingArea by rememberSaveable { mutableStateOf("") }
    var start by remember { mutableStateOf(LocalDateTime.now().plusHours(2)) }
    var end by remember { mutableStateOf(LocalDateTime.now().plusHours(4)) }
    var maxPeople by rememberSaveable { mutableIntStateOf(4) }
    var language by rememberSaveable {
        mutableStateOf(Locale.getDefault().language.ifBlank { "en" })
    }
    var approvalRequired by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(state.createdId) {
        state.createdId?.let(onCreated)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.create_vibe), onBack) },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                item {
                    PremiumAccentCard(
                        title = stringResource(R.string.create_vibe),
                        body = stringResource(R.string.profile_setup_body),
                        icon = Icons.Default.Celebration,
                    )
                }
                item {
                    VibeTextField(
                        title,
                        { title = it },
                        stringResource(R.string.title),
                        Modifier.fillMaxWidth(),
                        Icons.Default.Title,
                    )
                }
                item {
                    VibeTextField(
                        description,
                        { description = it },
                        stringResource(R.string.description),
                        Modifier.fillMaxWidth(),
                        Icons.Default.Edit,
                        singleLine = false,
                    )
                }
                item {
                    PremiumSectionTitle(stringResource(R.string.mood_question))
                    ChoiceChips(
                        values = MoodType.entries.map { it.name to moodLabel(it) },
                        selected = mood,
                        onSelected = { mood = it },
                    )
                }
                item {
                    // The outing's city is automatically your current city.
                    PremiumInfoRow(
                        icon = Icons.Default.LocationCity,
                        title = viewModel.city?.localizedName(LocalConfiguration.current.locales[0])
                            ?: stringResource(R.string.city),
                        subtitle = stringResource(R.string.city),
                        tint = BrandMint,
                    )
                }
                item {
                    VibeTextField(
                        meetingArea,
                        { meetingArea = it },
                        stringResource(R.string.meeting_area),
                        Modifier.fillMaxWidth(),
                        Icons.Default.LocationOn,
                    )
                }
                item {
                    DateTimeField(stringResource(R.string.start_time), start) {
                        start = it
                        if (end <= it) end = it.plusHours(2)
                    }
                }
                item {
                    DateTimeField(stringResource(R.string.end_time), end) { end = it }
                }
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        elevation = CardDefaults.cardElevation(0.dp),
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "${stringResource(R.string.max_people)}: $maxPeople",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                            )
                            Slider(
                                value = maxPeople.toFloat(),
                                onValueChange = { maxPeople = it.toInt() },
                                valueRange = 2f..20f,
                                steps = 17,
                            )
                        }
                    }
                }
                item {
                    VibeTextField(
                        language,
                        { language = it.take(5) },
                        stringResource(R.string.language),
                        Modifier.fillMaxWidth(),
                        Icons.Default.Language,
                    )
                }
                item {
                    PremiumInfoRow(
                        icon = Icons.Default.HowToReg,
                        title = stringResource(R.string.approval_required),
                        trailing = {
                            Switch(
                                checked = approvalRequired,
                                onCheckedChange = { approvalRequired = it },
                            )
                        },
                    )
                }
                state.error?.let { error ->
                    item {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                        ) {
                            Text(
                                error,
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer,
                            )
                        }
                    }
                }
                item {
                    VibePrimaryButton(
                        text = stringResource(R.string.publish_vibe),
                        onClick = {
                            val actualCityId = viewModel.cityId ?: return@VibePrimaryButton
                            viewModel.create(
                                CreateVibeRequest(
                                    title = title.trim(),
                                    description = description.trim().takeIf(String::isNotEmpty),
                                    mood = mood,
                                    placeId = viewModel.initialPlaceId?.takeIf { it != "none" },
                                    cityId = actualCityId,
                                    meetingArea = meetingArea.trim().takeIf(String::isNotEmpty),
                                    startTime = start.atZone(ZoneId.systemDefault()).toInstant().toString(),
                                    endTime = end.atZone(ZoneId.systemDefault()).toInstant().toString(),
                                    maxPeople = maxPeople,
                                    language = language,
                                    visibility = VibeVisibility.PUBLIC.name,
                                    approvalRequired = approvalRequired,
                                )
                            )
                        },
                        enabled = title.length >= 3 && end > start,
                        loading = state.loading,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                item { Spacer(Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
private fun DateTimeField(
    label: String,
    value: LocalDateTime,
    onValue: (LocalDateTime) -> Unit,
) {
    val context = LocalContext.current
    PremiumInfoRow(
        icon = Icons.Default.Schedule,
        title = label,
        subtitle = value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
        onClick = {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            onValue(LocalDateTime.of(year, month + 1, day, hour, minute))
                        },
                        value.hour,
                        value.minute,
                        true,
                    ).show()
                },
                value.year,
                value.monthValue - 1,
                value.dayOfMonth,
            ).show()
        },
        trailing = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
    )
}

data class VibeDetailsUiState(
    val state: UiState<Vibe> = UiState.Loading,
    val actionLoading: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class VibeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val id: String = checkNotNull(savedStateHandle["vibeId"])
    val currentUserId: String? = repository.currentUser.value?.id
    private val _state = MutableStateFlow(VibeDetailsUiState())
    val state: StateFlow<VibeDetailsUiState> = _state.asStateFlow()

    init { load() }

    fun load() = viewModelScope.launch {
        _state.value = VibeDetailsUiState(UiState.Loading)
        runCatching { repository.getVibe(id) }
            .onSuccess { _state.value = VibeDetailsUiState(UiState.Success(it)) }
            .onFailure { _state.value = VibeDetailsUiState(UiState.Error(it.message ?: "")) }
    }

    fun join(message: String?) = action { repository.joinVibe(id, message); load() }
    fun leave() = action { repository.leaveVibe(id); load() }
    fun cancel() = action { repository.cancelVibe(id, null); load() }
    fun approve(userId: String) = action { repository.approveParticipant(id, userId); load() }
    fun reject(userId: String) = action { repository.rejectParticipant(id, userId); load() }

    private fun action(block: suspend () -> Unit) = viewModelScope.launch {
        _state.value = _state.value.copy(actionLoading = true, error = null)
        runCatching { block() }
            .onFailure { _state.value = _state.value.copy(actionLoading = false, error = it.message) }
    }
}

@Composable
fun VibeDetailsScreen(
    onBack: () -> Unit,
    onChat: (String) -> Unit,
    onReport: (String, String) -> Unit,
    viewModel: VibeDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    var joinDialog by remember { mutableStateOf(false) }
    var joinMessage by remember { mutableStateOf("") }
    val sharedFrom = stringResource(R.string.shared_from_vibeout)
    val shareLabel = stringResource(R.string.share_vibe)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.vibes),
                onBack = onBack,
                actions = {
                    IconButton(onClick = {
                        val vibe = (state.state as? UiState.Success)?.data
                        if (vibe != null) {
                            val details = listOfNotNull(
                                vibe.title,
                                formatServerDate(vibe.startTime),
                                vibe.place?.name ?: vibe.meetingArea,
                            ).joinToString("\n")
                            val send = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "$details\n\n$sharedFrom")
                            }
                            context.startActivity(Intent.createChooser(send, shareLabel))
                        }
                    }) {
                        Icon(Icons.Default.Share, shareLabel)
                    }
                    IconButton(onClick = {
                        val vibe = (state.state as? UiState.Success)?.data
                        if (vibe != null) onReport("VIBE", vibe.id)
                    }) {
                        Icon(Icons.Default.Flag, stringResource(R.string.report))
                    }
                },
            )
        },
    ) { padding ->
        when (val content = state.state) {
            UiState.Loading, UiState.Idle -> PremiumLoadingState(Modifier.padding(padding))
            is UiState.Error -> PremiumErrorState(
                content.message,
                stringResource(R.string.retry),
                viewModel::load,
                Modifier.padding(padding),
            )
            is UiState.Success -> {
                val vibe = content.data
                LazyColumn(
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    item {
                        PremiumAccentCard(
                            title = vibe.title,
                            body = vibe.description.orEmpty(),
                            icon = Icons.Default.Groups,
                        )
                    }
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            PremiumStatusChip(vibe.status.replace('_', ' '))
                            PremiumStatusChip(
                                "${vibe.approvedParticipantsCount}/${vibe.maxPeople}",
                                color = BrandMint,
                            )
                            PremiumStatusChip(vibe.language.uppercase(Locale.ROOT))
                        }
                    }
                    item {
                        PremiumInfoRow(
                            icon = Icons.Default.Place,
                            title = vibe.place?.name ?: vibe.meetingArea.orEmpty(),
                            subtitle = formatServerDate(vibe.startTime),
                        )
                    }
                    item {
                        PremiumInfoRow(
                            icon = Icons.Default.Person,
                            title = vibe.creator?.displayName ?: stringResource(R.string.organizer),
                            subtitle = stringResource(R.string.organizer),
                            tint = BrandEnergy,
                        )
                    }
                    item {
                        PremiumInfoRow(
                            icon = Icons.Default.Groups,
                            title = "${vibe.approvedParticipantsCount}/${vibe.maxPeople}",
                            subtitle = stringResource(R.string.participants),
                            tint = BrandMint,
                        )
                    }
                    state.error?.let { error ->
                        item {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.errorContainer,
                            ) {
                                Text(
                                    error,
                                    Modifier.fillMaxWidth().padding(14.dp),
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                )
                            }
                        }
                    }
                    item {
                        val status = vibe.currentUserParticipationStatus
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            when (status) {
                                "APPROVED" -> {
                                    VibePrimaryButton(
                                        text = stringResource(R.string.open_chat),
                                        onClick = { onChat(vibe.id) },
                                        modifier = Modifier.weight(1f),
                                    )
                                    VibeSecondaryButton(
                                        text = stringResource(R.string.leave),
                                        onClick = viewModel::leave,
                                    )
                                }
                                "PENDING" -> {
                                    PremiumInfoRow(
                                        icon = Icons.Default.HourglassTop,
                                        title = stringResource(R.string.pending_approval),
                                        modifier = Modifier.weight(1f),
                                    )
                                }
                                else -> {
                                    VibePrimaryButton(
                                        text = stringResource(R.string.join),
                                        onClick = { joinDialog = true },
                                        modifier = Modifier.weight(1f),
                                        loading = state.actionLoading,
                                    )
                                }
                            }
                        }
                    }

                    val isCreator = viewModel.currentUserId != null && viewModel.currentUserId == vibe.creatorId
                    val pending = vibe.participants.filter { it.status == "PENDING" }
                    if (isCreator && pending.isNotEmpty()) {
                        item {
                            PremiumSectionTitle(stringResource(R.string.participants_requests))
                        }
                        items(pending, key = { it.id }) { participant ->
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                elevation = CardDefaults.cardElevation(0.dp),
                            ) {
                                Column(
                                    Modifier.fillMaxWidth().padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Text(
                                        participant.user?.displayName ?: participant.userId,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    participant.joinMessage?.let {
                                        Text(it, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { viewModel.approve(participant.userId) },
                                            shape = RoundedCornerShape(14.dp),
                                        ) {
                                            Text(stringResource(R.string.approve))
                                        }
                                        OutlinedButton(
                                            onClick = { viewModel.reject(participant.userId) },
                                            shape = RoundedCornerShape(14.dp),
                                        ) {
                                            Text(stringResource(R.string.reject))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (joinDialog) {
        AlertDialog(
            onDismissRequest = { joinDialog = false },
            shape = RoundedCornerShape(28.dp),
            title = { Text(stringResource(R.string.join), fontWeight = FontWeight.ExtraBold) },
            text = {
                VibeTextField(
                    joinMessage,
                    { joinMessage = it },
                    stringResource(R.string.join_message),
                    Modifier.fillMaxWidth(),
                    Icons.Default.Edit,
                    singleLine = false,
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        joinDialog = false
                        viewModel.join(joinMessage)
                    },
                ) {
                    Text(stringResource(R.string.join))
                }
            },
            dismissButton = {
                TextButton(onClick = { joinDialog = false }) {
                    Text(stringResource(R.string.cancel))
                }
            },
        )
    }
}

data class ChatUiState(
    val state: UiState<List<ChatMessage>> = UiState.Loading,
    val sending: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val vibeId: String = checkNotNull(savedStateHandle["vibeId"])
    val currentUserId: String? = repository.currentUser.value?.id
    private val _state = MutableStateFlow(ChatUiState())
    val state: StateFlow<ChatUiState> = _state.asStateFlow()
    private var polling: Job? = null

    init { startPolling() }

    private fun startPolling() {
        polling = viewModelScope.launch {
            while (true) {
                load(silent = _state.value.state is UiState.Success)
                delay(5000)
            }
        }
    }

    fun load(silent: Boolean = false) = viewModelScope.launch {
        if (!silent) _state.value = _state.value.copy(state = UiState.Loading)
        runCatching { repository.getMessages(vibeId) }
            .onSuccess { _state.value = ChatUiState(UiState.Success(it)) }
            .onFailure {
                if (!silent) _state.value = ChatUiState(UiState.Error(it.message ?: ""))
            }
    }

    fun send(message: String) = viewModelScope.launch {
        if (message.isBlank()) return@launch
        _state.value = _state.value.copy(sending = true, error = null)
        runCatching { repository.sendMessage(vibeId, message) }
            .onSuccess { load(true) }
            .onFailure {
                _state.value = _state.value.copy(sending = false, error = it.message)
            }
    }

    fun block(userId: String) = viewModelScope.launch {
        runCatching { repository.blockUser(userId) }
            .onSuccess { load(true) }
            .onFailure { _state.value = _state.value.copy(error = it.message) }
    }
}

@Composable
fun ChatScreen(
    onBack: () -> Unit,
    onReport: (String, String) -> Unit,
    viewModel: ChatViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    var menuForMessageId by remember { mutableStateOf<String?>(null) }
    var blockTarget by remember { mutableStateOf<Pair<String, String>?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.chat), onBack) },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 10.dp,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    VibeTextField(
                        value = text,
                        onValueChange = { text = it },
                        label = stringResource(R.string.message_hint),
                        modifier = Modifier.weight(1f),
                        leadingIcon = Icons.Default.ChatBubbleOutline,
                        singleLine = false,
                    )
                    Spacer(Modifier.width(8.dp))
                    FloatingActionButton(
                        onClick = {
                            val message = text
                            text = ""
                            viewModel.send(message)
                        },
                        modifier = Modifier.size(52.dp),
                        shape = CircleShape,
                    ) {
                        if (state.sending) {
                            CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                        } else {
                            Icon(Icons.AutoMirrored.Filled.Send, stringResource(R.string.send))
                        }
                    }
                }
            }
        },
    ) { padding ->
        when (val content = state.state) {
            UiState.Loading, UiState.Idle -> PremiumLoadingState(Modifier.padding(padding))
            is UiState.Error -> PremiumErrorState(
                content.message,
                stringResource(R.string.retry),
                { viewModel.load() },
                Modifier.padding(padding),
            )
            is UiState.Success -> {
                if (content.data.isEmpty()) {
                    PremiumEmptyState(
                        title = stringResource(R.string.no_messages),
                        icon = Icons.Default.ChatBubbleOutline,
                        modifier = Modifier.padding(padding),
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(padding).fillMaxSize(),
                        contentPadding = PaddingValues(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        items(content.data, key = { it.id }) { message ->
                            val mine = message.senderId == viewModel.currentUserId
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (mine) Arrangement.End else Arrangement.Start,
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(
                                        topStart = 20.dp,
                                        topEnd = 20.dp,
                                        bottomStart = if (mine) 20.dp else 6.dp,
                                        bottomEnd = if (mine) 6.dp else 20.dp,
                                    ),
                                    color = if (mine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                                    border = if (mine) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                                    contentColor = if (mine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.widthIn(max = 310.dp),
                                ) {
                                    Column(Modifier.padding(13.dp)) {
                                        if (!mine) {
                                            Text(
                                                message.sender?.displayName ?: message.senderId,
                                                style = MaterialTheme.typography.labelMedium,
                                                fontWeight = FontWeight.Bold,
                                            )
                                            Spacer(Modifier.height(3.dp))
                                        }
                                        Text(message.message)
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End,
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Text(
                                                formatServerDate(message.createdAt),
                                                style = MaterialTheme.typography.labelSmall,
                                            )
                                            if (!mine) {
                                                Box {
                                                    IconButton(
                                                        onClick = { menuForMessageId = message.id },
                                                        modifier = Modifier.size(28.dp),
                                                    ) {
                                                        Icon(
                                                            Icons.Default.MoreVert,
                                                            contentDescription = stringResource(R.string.report),
                                                            modifier = Modifier.size(15.dp),
                                                        )
                                                    }
                                                    DropdownMenu(
                                                        expanded = menuForMessageId == message.id,
                                                        onDismissRequest = { menuForMessageId = null },
                                                    ) {
                                                        DropdownMenuItem(
                                                            text = { Text(stringResource(R.string.report_message)) },
                                                            leadingIcon = { Icon(Icons.Default.Flag, contentDescription = null) },
                                                            onClick = {
                                                                menuForMessageId = null
                                                                onReport("MESSAGE", message.id)
                                                            },
                                                        )
                                                        DropdownMenuItem(
                                                            text = { Text(stringResource(R.string.report_user)) },
                                                            leadingIcon = { Icon(Icons.Default.PersonOff, contentDescription = null) },
                                                            onClick = {
                                                                menuForMessageId = null
                                                                onReport("USER", message.senderId)
                                                            },
                                                        )
                                                        DropdownMenuItem(
                                                            text = { Text(stringResource(R.string.block_user)) },
                                                            leadingIcon = { Icon(Icons.Default.Block, contentDescription = null) },
                                                            onClick = {
                                                                menuForMessageId = null
                                                                blockTarget = message.senderId to
                                                                    (message.sender?.displayName ?: message.senderId)
                                                            },
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    blockTarget?.let { (userId, name) ->
        ConfirmDialog(
            title = stringResource(R.string.block_user_confirm),
            body = name,
            confirmLabel = stringResource(R.string.block_user),
            destructive = true,
            onConfirm = {
                viewModel.block(userId)
                blockTarget = null
            },
            onDismiss = { blockTarget = null },
        )
    }
}

private fun formatServerDate(value: String): String = runCatching {
    Instant.parse(value)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}.getOrDefault(value)
