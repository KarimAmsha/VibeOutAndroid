@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.vibeout.talaa.feature.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.NotificationItem
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow<UiState<List<NotificationItem>>>(UiState.Loading)
    val state: StateFlow<UiState<List<NotificationItem>>> = _state.asStateFlow()

    init { load() }

    fun load() = viewModelScope.launch {
        _state.value = UiState.Loading
        runCatching { repository.getNotifications() }
            .onSuccess { _state.value = UiState.Success(it) }
            .onFailure { _state.value = UiState.Error(it.message ?: "") }
    }

    fun markRead(id: String) = viewModelScope.launch {
        runCatching { repository.markNotificationRead(id) }.onSuccess { load() }
    }

    fun markAll() = viewModelScope.launch {
        runCatching { repository.markAllNotificationsRead() }.onSuccess { load() }
    }
}

@Composable
fun NotificationsScreen(
    onOpenVibe: (String) -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            VibeTopBar(
                title = stringResource(R.string.notifications),
                actions = {
                    IconButton(onClick = viewModel::markAll) {
                        Icon(Icons.Default.DoneAll, stringResource(R.string.mark_all_read))
                    }
                },
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
                            title = stringResource(R.string.no_notifications),
                            icon = Icons.Default.Notifications,
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(content.data, key = { it.id }) { item ->
                                NotificationCard(
                                    item = item,
                                    onClick = {
                                        viewModel.markRead(item.id)
                                        (item.dataJson?.get("vibeId") as? String)?.let(onOpenVibe)
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

@Composable
private fun NotificationCard(item: NotificationItem, onClick: () -> Unit) {
    val unread = item.readAt == null
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (unread) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp,
            if (unread) MaterialTheme.colorScheme.primary.copy(alpha = 0.24f)
            else MaterialTheme.colorScheme.outlineVariant,
        ),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Surface(
                shape = CircleShape,
                color = if (unread) BrandEnergy.copy(alpha = 0.14f) else MaterialTheme.colorScheme.surfaceVariant,
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    tint = if (unread) BrandEnergy else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(11.dp).size(22.dp),
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                    )
                    if (unread) {
                        Box(Modifier.size(8.dp).padding(0.dp))
                        Surface(shape = CircleShape, color = BrandEnergy, modifier = Modifier.size(8.dp)) {}
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(item.body, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(8.dp))
                Text(
                    formatNotificationDate(item.createdAt),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun formatNotificationDate(value: String): String = runCatching {
    Instant.parse(value)
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
}.getOrDefault(value)
