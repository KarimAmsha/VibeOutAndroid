@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.vibes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.vibeout.talaa.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class VibesViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<Vibe>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Vibe>>> = _state.asStateFlow()
    init { load() }
    fun load() = viewModelScope.launch {
        _state.value = UiState.Loading
        runCatching { repository.getVibes(repository.currentUser.value?.city?.id) }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }
}

@Composable
fun VibesScreen(onOpen: (String) -> Unit, onCreate: () -> Unit, viewModel: VibesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.vibes)) }) },
        floatingActionButton = { FloatingActionButton(onClick = onCreate) { Icon(Icons.Default.Add, stringResource(R.string.create_vibe)) } },
    ) { padding ->
        when (val content = state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                if (content.data.isEmpty()) EmptyPane(stringResource(R.string.empty_state), Modifier.padding(padding))
                else LazyColumn(
                    Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(content.data, key = { it.id }) { vibe ->
                        ElevatedCard(Modifier.fillMaxWidth().clickable { onOpen(vibe.id) }) {
                            Column(Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(vibe.title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                                    AssistChip(onClick = {}, label = { Text(vibe.status.replace('_', ' ')) })
                                }
                                Text(listOfNotNull(vibe.meetingArea, vibe.place?.name).joinToString(" • "))
                                Text("${vibe.language.uppercase(Locale.ROOT)} • ${vibe.approvedParticipantsCount}/${vibe.maxPeople}")
                                Text(formatServerDate(vibe.startTime), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class CreateVibeUiState(val loading: Boolean = false, val error: String? = null, val createdId: String? = null)

@HiltViewModel
class CreateVibeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    val initialPlaceId: String? = savedStateHandle["placeId"]
    val cityId: String? = repository.currentUser.value?.city?.id
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
fun CreateVibeScreen(onBack: () -> Unit, onCreated: (String) -> Unit, viewModel: CreateVibeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var mood by rememberSaveable { mutableStateOf(MoodType.GROUP_VIBE.name) }
    var meetingArea by rememberSaveable { mutableStateOf("") }
    var start by remember { mutableStateOf(LocalDateTime.now().plusHours(2)) }
    var end by remember { mutableStateOf(LocalDateTime.now().plusHours(4)) }
    var maxPeople by rememberSaveable { mutableIntStateOf(4) }
    var language by rememberSaveable { mutableStateOf(Locale.getDefault().language.ifBlank { "en" }) }
    var approvalRequired by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(state.createdId) { state.createdId?.let(onCreated) }

    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.create_vibe), onBack) }) { padding ->
        LazyColumn(
            Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item { OutlinedTextField(title, { title = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.title)) }) }
            item { OutlinedTextField(description, { description = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.description)) }, minLines = 3) }
            item {
                Text(stringResource(R.string.mood_question), style = MaterialTheme.typography.titleMedium)
                ChoiceChips(
                    values = MoodType.entries.map { it.name to moodLabel(it) },
                    selected = mood,
                    onSelected = { mood = it },
                )
            }
            item { OutlinedTextField(meetingArea, { meetingArea = it }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.city)) }) }
            item { DateTimeField(stringResource(R.string.start_time), start) { start = it; if (end <= it) end = it.plusHours(2) } }
            item { DateTimeField(stringResource(R.string.end_time), end) { end = it } }
            item {
                Text("${stringResource(R.string.max_people)}: $maxPeople", style = MaterialTheme.typography.titleMedium)
                Slider(value = maxPeople.toFloat(), onValueChange = { maxPeople = it.toInt() }, valueRange = 2f..20f, steps = 17)
            }
            item { OutlinedTextField(language, { language = it.take(5) }, Modifier.fillMaxWidth(), label = { Text(stringResource(R.string.language)) }) }
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = approvalRequired, onCheckedChange = { approvalRequired = it })
                    Spacer(Modifier.width(10.dp))
                    Text(stringResource(R.string.approval_required))
                }
            }
            state.error?.let { item { Text(it, color = MaterialTheme.colorScheme.error) } }
            item {
                Button(
                    onClick = {
                        val actualCityId = viewModel.cityId ?: return@Button
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
                    enabled = !state.loading && title.length >= 3 && end > start,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    if (state.loading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    else Text(stringResource(R.string.publish_vibe))
                }
            }
        }
    }
}


@Composable
private fun DateTimeField(label: String, value: LocalDateTime, onValue: (LocalDateTime) -> Unit) {
    val context = LocalContext.current
    OutlinedButton(
        onClick = {
            DatePickerDialog(context, { _, year, month, day ->
                TimePickerDialog(context, { _, hour, minute ->
                    onValue(LocalDateTime.of(year, month + 1, day, hour, minute))
                }, value.hour, value.minute, true).show()
            }, value.year, value.monthValue - 1, value.dayOfMonth).show()
        },
        modifier = Modifier.fillMaxWidth(),
    ) {
        Icon(Icons.Default.Schedule, null)
        Spacer(Modifier.width(8.dp))
        Text("$label: ${value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}")
    }
}

data class VibeDetailsUiState(val state: UiState<Vibe> = UiState.Loading, val actionLoading: Boolean = false, val error: String? = null)

@HiltViewModel
class VibeDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val id: String = checkNotNull(savedStateHandle["vibeId"])
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
    var joinDialog by remember { mutableStateOf(false) }
    var joinMessage by remember { mutableStateOf("") }
    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.vibes), onBack) }) { padding ->
        when (val content = state.state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                val vibe = content.data
                LazyColumn(
                    Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        Text(vibe.title, style = MaterialTheme.typography.headlineMedium)
                        Text(vibe.description.orEmpty())
                        AssistChip(onClick = {}, label = { Text(vibe.status.replace('_', ' ')) })
                    }
                    item {
                        ListItem(headlineContent = { Text(vibe.place?.name ?: vibe.meetingArea.orEmpty()) }, supportingContent = { Text(formatServerDate(vibe.startTime)) }, leadingContent = { Icon(Icons.Default.Place, null) })
                        ListItem(headlineContent = { Text(vibe.creator?.displayName ?: stringResource(R.string.organizer)) }, supportingContent = { Text(stringResource(R.string.organizer)) }, leadingContent = { Icon(Icons.Default.Person, null) })
                        ListItem(headlineContent = { Text("${vibe.approvedParticipantsCount}/${vibe.maxPeople}") }, supportingContent = { Text(stringResource(R.string.participants)) }, leadingContent = { Icon(Icons.Default.Groups, null) })
                    }
                    state.error?.let { item { Text(it, color = MaterialTheme.colorScheme.error) } }
                    item {
                        val status = vibe.currentUserParticipationStatus
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            when (status) {
                                "APPROVED" -> {
                                    Button(onClick = { onChat(vibe.id) }, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.open_chat)) }
                                    OutlinedButton(onClick = viewModel::leave) { Text(stringResource(R.string.leave)) }
                                }
                                "PENDING" -> AssistChip(onClick = {}, label = { Text(stringResource(R.string.pending_approval)) })
                                else -> Button(onClick = { joinDialog = true }, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.join)) }
                            }
                            IconButton(onClick = { onReport("VIBE", vibe.id) }) { Icon(Icons.Default.Flag, stringResource(R.string.report)) }
                        }
                    }
                    if (vibe.participants.any { it.status == "PENDING" }) {
                        item { SectionTitle(stringResource(R.string.participants_requests)) }
                        items(vibe.participants.filter { it.status == "PENDING" }, key = { it.id }) { participant ->
                            ElevatedCard {
                                Column(Modifier.padding(14.dp)) {
                                    Text(participant.user?.displayName ?: participant.userId)
                                    participant.joinMessage?.let { Text(it) }
                                    Row {
                                        TextButton(onClick = { viewModel.approve(participant.userId) }) { Text(stringResource(R.string.approve)) }
                                        TextButton(onClick = { viewModel.reject(participant.userId) }) { Text(stringResource(R.string.reject)) }
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
            title = { Text(stringResource(R.string.join)) },
            text = { OutlinedTextField(joinMessage, { joinMessage = it }, label = { Text(stringResource(R.string.join_message)) }) },
            confirmButton = { TextButton(onClick = { joinDialog = false; viewModel.join(joinMessage) }) { Text(stringResource(R.string.join)) } },
            dismissButton = { TextButton(onClick = { joinDialog = false }) { Text(stringResource(R.string.cancel)) } },
        )
    }
}

data class ChatUiState(val state: UiState<List<ChatMessage>> = UiState.Loading, val sending: Boolean = false, val error: String? = null)

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val vibeId: String = checkNotNull(savedStateHandle["vibeId"])
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
            .onFailure { if (!silent) _state.value = ChatUiState(UiState.Error(it.message ?: "")) }
    }
    fun send(message: String) = viewModelScope.launch {
        if (message.isBlank()) return@launch
        _state.value = _state.value.copy(sending = true, error = null)
        runCatching { repository.sendMessage(vibeId, message) }
            .onSuccess { load(true) }
            .onFailure { _state.value = _state.value.copy(sending = false, error = it.message) }
    }
}

@Composable
fun ChatScreen(onBack: () -> Unit, onReport: (String, String) -> Unit, viewModel: ChatViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var text by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = { VibeOutTopBar(stringResource(R.string.chat), onBack) },
        bottomBar = {
            Surface(shadowElevation = 6.dp) {
                Row(Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(text, { text = it }, modifier = Modifier.weight(1f), placeholder = { Text(stringResource(R.string.message_hint)) }, maxLines = 4)
                    IconButton(onClick = { val message = text; text = ""; viewModel.send(message) }, enabled = !state.sending) { Icon(Icons.Default.Send, stringResource(R.string.send)) }
                }
            }
        },
    ) { padding ->
        when (val content = state.state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, { viewModel.load() }, Modifier.padding(padding))
            is UiState.Success -> {
                if (content.data.isEmpty()) EmptyPane(stringResource(R.string.no_messages), Modifier.padding(padding))
                else LazyColumn(
                    Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(content.data, key = { it.id }) { message ->
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(12.dp)) {
                                Text(message.sender?.displayName ?: message.senderId, style = MaterialTheme.typography.labelLarge)
                                Text(message.message)
                                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                    Text(formatServerDate(message.createdAt), style = MaterialTheme.typography.labelSmall)
                                    IconButton(onClick = { onReport("MESSAGE", message.id) }, modifier = Modifier.size(28.dp)) { Icon(Icons.Default.Flag, null, modifier = Modifier.size(16.dp)) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun formatServerDate(value: String): String = runCatching {
    Instant.parse(value).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}.getOrDefault(value)
