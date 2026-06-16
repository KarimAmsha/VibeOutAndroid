@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.notifications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.NotificationItem
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.common.UiState
import com.vibeout.talaa.ui.components.EmptyPane
import com.vibeout.talaa.ui.components.ErrorPane
import com.vibeout.talaa.ui.components.LoadingPane
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
fun NotificationsScreen(onOpenVibe: (String) -> Unit, viewModel: NotificationsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.notifications)) },
                actions = { IconButton(onClick = viewModel::markAll) { Icon(Icons.Default.DoneAll, stringResource(R.string.mark_all_read)) } },
            )
        }
    ) { padding ->
        when (val content = state) {
            UiState.Loading, UiState.Idle -> LoadingPane(Modifier.padding(padding))
            is UiState.Error -> ErrorPane(content.message, viewModel::load, Modifier.padding(padding))
            is UiState.Success -> {
                if (content.data.isEmpty()) EmptyPane(stringResource(R.string.no_notifications), Modifier.padding(padding))
                else LazyColumn(Modifier.padding(padding).fillMaxSize()) {
                    items(content.data, key = { it.id }) { item ->
                        ListItem(
                            headlineContent = { Text(item.title) },
                            supportingContent = { Text(item.body) },
                            leadingContent = { Icon(Icons.Default.Notifications, null, tint = if (item.readAt == null) MaterialTheme.colorScheme.primary else LocalContentColor.current) },
                            modifier = Modifier.clickable {
                                viewModel.markRead(item.id)
                                (item.dataJson?.get("vibeId") as? String)?.let(onOpenVibe)
                            },
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
