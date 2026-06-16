@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.places

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.vibeout.talaa.ui.components.ChoiceChips
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
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
            else repository.getPlaces(
                repository.currentUser.value?.city?.id,
                _state.value.search.takeIf(String::isNotBlank),
            )
        }.onSuccess {
            _state.value = _state.value.copy(state = UiState.Success(it))
        }.onFailure {
            _state.value = _state.value.copy(state = UiState.Error(it.message ?: ""))
        }
    }

    fun search(value: String) {
        _state.value = _state.value.copy(search = value)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(350)
            load(false)
        }
    }

    fun setSaved(id: String, value: Boolean) = viewModelScope.launch {
        runCatching { repository.setPlaceSaved(id, value) }.onSuccess {
            val current = (_state.value.state as? UiState.Success)?.data.orEmpty()
            _state.value = _state.value.copy(
                state = UiState.Success(
                    current
                        .map { if (it.id == id) it.copy(isSaved = value) else it }
                        .filter { !(_state.value.savedOnly && !it.isSaved) }
                )
            )
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.places),
                actions = {
                    IconButton(onClick = onOpenMap) {
                        Icon(Icons.Default.Map, stringResource(R.string.map))
                    }
                },
            )
        },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(Modifier.fillMaxSize()) {
                VibeTextField(
                    value = state.search,
                    onValueChange = viewModel::search,
                    label = stringResource(R.string.search_places),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                    leadingIcon = Icons.Default.Search,
                    trailingContent = if (state.search.isNotEmpty()) {
                        {
                            IconButton(onClick = { viewModel.search("") }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                    } else null,
                )

                ChoiceChips(
                    values = listOf(
                        "recommended" to stringResource(R.string.recommended),
                        "saved" to stringResource(R.string.saved),
                    ),
                    selected = if (state.savedOnly) "saved" else "recommended",
                    onSelected = { viewModel.load(it == "saved") },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                )

                when (val content = state.state) {
                    UiState.Loading, UiState.Idle -> PremiumLoadingState(Modifier.weight(1f), stringResource(R.string.loading))
                    is UiState.Error -> PremiumErrorState(
                        title = content.message,
                        retryLabel = stringResource(R.string.retry),
                        onRetry = { viewModel.load() },
                        modifier = Modifier.weight(1f),
                    )
                    is UiState.Success -> {
                        if (content.data.isEmpty()) {
                            PremiumEmptyState(
                                title = stringResource(R.string.no_places),
                                modifier = Modifier.weight(1f),
                                icon = Icons.Default.Place,
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 14.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp),
                            ) {
                                items(content.data, key = { it.id }) { place ->
                                    PremiumPlaceCard(
                                        place = place,
                                        onClick = { onOpenPlace(place.id) },
                                        onSave = { viewModel.setSaved(place.id, !place.isSaved) },
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

@Composable
private fun PremiumPlaceCard(place: Place, onClick: () -> Unit, onSave: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column {
            Box {
                AsyncImage(
                    model = place.photos.firstOrNull()?.thumbnailUrl ?: place.photos.firstOrNull()?.url,
                    contentDescription = place.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(164.dp),
                )
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    modifier = Modifier.align(Alignment.TopEnd).padding(10.dp),
                ) {
                    IconButton(onClick = onSave) {
                        Icon(
                            if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            stringResource(if (place.isSaved) R.string.remove_saved else R.string.save_place),
                            tint = if (place.isSaved) BrandEnergy else MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        place.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.weight(1f),
                    )
                    PremiumStatusChip(
                        text = "★ ${"%.1f".format(place.ratingInternal)}",
                        color = BrandEnergy,
                    )
                }
                Text(
                    listOfNotNull(place.area, place.type.replace('_', ' ')).joinToString(" • "),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    PremiumStatusChip(formatPriceLevel(place.priceLevel))
                    if (place.isPartner) {
                        PremiumStatusChip(stringResource(R.string.verified), color = BrandMint)
                    }
                }
            }
        }
    }
}

data class PlaceDetailsUiState(
    val state: UiState<Place> = UiState.Loading,
    val saving: Boolean = false,
)

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
            .onSuccess {
                _state.value = PlaceDetailsUiState(
                    UiState.Success(place.copy(isSaved = !place.isSaved)),
                    false,
                )
            }
            .onFailure { _state.value = _state.value.copy(saving = false) }
    }
}

@Composable
fun PlaceDetailsScreen(
    onBack: () -> Unit,
    onCreateVibe: (String) -> Unit,
    viewModel: PlaceDetailsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.places),
                onBack = onBack,
                actions = {
                    val place = (state.state as? UiState.Success)?.data
                    if (place != null) {
                        IconButton(onClick = viewModel::toggleSave, enabled = !state.saving) {
                            Icon(
                                if (place.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                            )
                        }
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
                val place = content.data
                LazyColumn(
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 28.dp),
                ) {
                    item {
                        AsyncImage(
                            model = place.photos.firstOrNull()?.url,
                            contentDescription = place.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(270.dp),
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Row(verticalAlignment = Alignment.Top) {
                                Column(Modifier.weight(1f)) {
                                    Text(
                                        place.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        listOfNotNull(place.area, place.address).joinToString(" • "),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                                PremiumStatusChip("★ ${"%.1f".format(place.ratingInternal)}", color = BrandEnergy)
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                PremiumStatusChip(formatPriceLevel(place.priceLevel))
                                PremiumStatusChip(place.type.replace('_', ' '))
                            }

                            place.description?.takeIf { it.isNotBlank() }?.let {
                                Text(it, style = MaterialTheme.typography.bodyLarge)
                            }

                            place.safetyNote?.takeIf { it.isNotBlank() }?.let {
                                PremiumInfoRow(
                                    icon = Icons.Default.VerifiedUser,
                                    title = stringResource(R.string.safety_note),
                                    subtitle = it,
                                    tint = BrandMint,
                                )
                            }

                            PremiumInfoRow(
                                icon = Icons.Default.LocationOn,
                                title = place.area ?: stringResource(R.string.city),
                                subtitle = place.address,
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                            ) {
                                VibeSecondaryButton(
                                    text = stringResource(R.string.directions),
                                    onClick = {
                                        val uri = Uri.parse(
                                            "geo:${place.latitude},${place.longitude}?q=${place.latitude},${place.longitude}(${Uri.encode(place.name)})"
                                        )
                                        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                                VibePrimaryButton(
                                    text = stringResource(R.string.create_vibe),
                                    onClick = { onCreateVibe(place.id) },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PlacesMapScreen(
    onBack: () -> Unit,
    onOpenPlace: (String) -> Unit,
    viewModel: PlacesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val places = (state.state as? UiState.Success)?.data.orEmpty()
    val first = places.firstOrNull()
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            first?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(39.7667, 30.5256),
            12f,
        )
    }

    Scaffold(
        topBar = { VibeTopBar(stringResource(R.string.map), onBack) },
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                places.filter { it.latitude != 0.0 || it.longitude != 0.0 }.forEach { place ->
                    Marker(
                        state = remember(place.id, place.latitude, place.longitude) {
                            MarkerState(
                                LatLng(place.latitude, place.longitude)
                            )
                        },
                        title = place.name,
                        snippet = place.area,
                        onInfoWindowClick = { onOpenPlace(place.id) },
                    )
                }
            }
            if (state.state is UiState.Loading) {
                PremiumLoadingState(Modifier.fillMaxSize())
            } else {
                Surface(
                    shape = RoundedCornerShape(999.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.94f),
                    shadowElevation = 6.dp,
                    modifier = Modifier.align(Alignment.TopCenter).padding(14.dp),
                ) {
                    Text(
                        "${places.size} ${stringResource(R.string.places)}",
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 9.dp),
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}
