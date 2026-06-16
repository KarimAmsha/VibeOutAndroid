@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.places

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.AsyncImage
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.Place
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.common.formatPriceLevel
import com.vibeout.talaa.ui.components.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PlacesUiState(
    val state: UiState<List<Place>> = UiState.Loading,
    val search: String = "",
    val savedOnly: Boolean = false,
)

@HiltViewModel
class PlacesViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(PlacesUiState())
    val state: StateFlow<PlacesUiState> = _state.asStateFlow()
    private var searchJob: Job? = null

    init { load() }

    fun load(savedOnly: Boolean = _state.value.savedOnly) = viewModelScope.launch {
        _state.value = _state.value.copy(state = UiState.Loading, savedOnly = savedOnly)
        runCatching {
            if (savedOnly) repository.getSavedPlaces()
            else repository.getPlaces(repository.currentUser.value?.city?.id, _state.value.search.takeIf(String::isNotBlank))
        }.onSuccess { _state.value = _state.value.copy(state = UiState.Success(it)) }
            .onFailure { _state.value = _state.value.copy(state = UiState.Error(it.message ?: "")) }
    }

    fun search(value: String) {
        _state.value = _state.value.copy(search = value)
        searchJob?.cancel()
        searchJob = viewModelScope.launch { delay(350); load(false) }
    }

    fun setSaved(id: String, value: Boolean) = viewModelScope.launch {
        runCatching { repository.setPlaceSaved(id, value) }
            .onSuccess {
                val current = (_state.value.state as? UiState.Success)?.data.orEmpty()
                _state.value = _state.value.copy(state = UiState.Success(current.map { if (it.id == id) it.copy(isSaved = value) else it }.filter { !(_state.value.savedOnly && !it.isSaved) }))
            }
    }
}

@Composable
fun PlacesScreen(
    onOpenPlace: (String) -> Unit,
    onOpenMap: () -> Unit,
    viewModel: PlacesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.places)) },
                actions = { IconButton(onClick = onOpenMap) { Icon(Icons.Default.Map, stringResource(R.string.map)) } },
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = state.search,
                onValueChange = viewModel::search,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                label = { Text(stringResource(R.string.search_places)) },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true,
            )
            TabRow(selectedTabIndex = if (state.savedOnly) 1 else 0) {
                Tab(selected = !state.savedOnly, onClick = { viewModel.load(false) }, text = { Text(stringResource(R.string.recommended)) })
                Tab(selected = state.savedOnly, onClick = { viewModel.load(true) }, text = { Text(stringResource(R.string.saved)) })
            }
            when (val content = state.state) {
                UiState.Loading, UiState.Idle -> LoadingPane()
                is UiState.Error -> ErrorPane(content.message, { viewModel.load() })
                is UiState.Success -> {
                    if (content.data.isEmpty()) EmptyPane(stringResource(R.string.no_places))
                    else LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(content.data, key = { it.id }) { place ->
                            PlaceCard(place, onClick = { onOpenPlace(place.id) }, onSave = { viewModel.setSaved(place.id, !place.isSaved) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceCard(place: Place, onClick: () -> Unit, onSave: () -> Unit) {
    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = place.photos.firstOrNull()?.thumbnailUrl ?: place.photos.firstOrNull()?.url,
                contentDescription = place.name,
                modifier = Modifier.size(88.dp),
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(place.name, style = MaterialTheme.typography.titleMedium)
                Text(listOfNotNull(place.area, place.type.replace('_', ' ')).joinToString(" • "), style = MaterialTheme.typography.bodySmall)
                Text(formatPriceLevel(place.priceLevel) + "  ★ ${"%.1f".format(place.ratingInternal)}")
            }
            IconButton(onClick = onSave) { Icon(if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder, stringResource(if (place.isSaved) R.string.remove_saved else R.string.save_place)) }
        }
    }
}

data class PlaceDetailsUiState(val state: UiState<Place> = UiState.Loading, val saving: Boolean = false)

@HiltViewModel
class PlaceDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    private val id: String = checkNotNull(savedStateHandle["placeId"])
    private val _state = MutableStateFlow(PlaceDetailsUiState())
    val state: StateFlow<PlaceDetailsUiState> = _state.asStateFlow()
    init { load() }
    fun load() = viewModelScope.launch {
        _state.value = PlaceDetailsUiState(UiState.Loading)
        runCatching { repository.getPlace(id) }
            .onSuccess { _state.value = PlaceDetailsUiState(UiState.Success(it)) }
            .onFailure { _state.value = PlaceDetailsUiState(UiState.Error(it.message ?: "")) }
    }
    fun toggleSave() = viewModelScope.launch {
        val place = (_state.value.state as? UiState.Success)?.data ?: return@launch
        _state.value = _state.value.copy(saving = true)
        runCatching { repository.setPlaceSaved(place.id, !place.isSaved) }
            .onSuccess { _state.value = PlaceDetailsUiState(UiState.Success(place.copy(isSaved = !place.isSaved)), false) }
            .onFailure { _state.value = _state.value.copy(saving = false) }
    }
}

@Composable
fun PlaceDetailsScreen(onBack: () -> Unit, onCreateVibe: (String) -> Unit, viewModel: PlaceDetailsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.places), onBack) }) { padding ->
        when (val content = state.state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                val place = content.data
                LazyColumn(Modifier.padding(padding).fillMaxSize()) {
                    item {
                        AsyncImage(
                            model = place.photos.firstOrNull()?.url,
                            contentDescription = place.name,
                            modifier = Modifier.fillMaxWidth().height(240.dp),
                        )
                    }
                    item {
                        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(place.name, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.weight(1f))
                                IconButton(onClick = viewModel::toggleSave, enabled = !state.saving) { Icon(if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder, null) }
                            }
                            Text(listOfNotNull(place.area, place.address).joinToString(" • "))
                            Text(formatPriceLevel(place.priceLevel) + "  ★ ${"%.1f".format(place.ratingInternal)}")
                            place.description?.let { Text(it) }
                            place.safetyNote?.let { AssistChip(onClick = {}, label = { Text(it) }, leadingIcon = { Icon(Icons.Default.VerifiedUser, null) }) }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(onClick = {
                                    val uri = Uri.parse("geo:${place.latitude},${place.longitude}?q=${place.latitude},${place.longitude}(${Uri.encode(place.name)})")
                                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                }) { Icon(Icons.Default.Directions, null); Spacer(Modifier.width(6.dp)); Text(stringResource(R.string.directions)) }
                                Button(onClick = { onCreateVibe(place.id) }) { Icon(Icons.Default.Add, null); Spacer(Modifier.width(6.dp)); Text(stringResource(R.string.create_vibe)) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlacesMapScreen(onBack: () -> Unit, onOpenPlace: (String) -> Unit, viewModel: PlacesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val places = (state.state as? UiState.Success)?.data.orEmpty()
    val first = places.firstOrNull()
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            first?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(39.7667, 30.5256),
            12f,
        )
    }
    Scaffold(topBar = { VibeOutTopBar(stringResource(R.string.map), onBack) }) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            GoogleMap(modifier = Modifier.fillMaxSize(), cameraPositionState = cameraPositionState) {
                places.filter { it.latitude != 0.0 || it.longitude != 0.0 }.forEach { place ->
                    Marker(
                        state = MarkerState(LatLng(place.latitude, place.longitude)),
                        title = place.name,
                        snippet = place.area,
                        onInfoWindowClick = { onOpenPlace(place.id) },
                    )
                }
            }
            if (state.state is UiState.Loading) CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}
