@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vibeout.talaa.R
import com.vibeout.talaa.ui.designsystem.*

@Composable
fun VibeOutTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    VibeTopBar(title = title, onBack = onBack, actions = actions)
}

@Composable
fun LoadingPane(modifier: Modifier = Modifier) {
    PremiumLoadingState(modifier, stringResource(R.string.loading))
}

@Composable
fun ErrorPane(message: String, onRetry: (() -> Unit)? = null, modifier: Modifier = Modifier) {
    if (onRetry == null) {
        PremiumEmptyState(
            title = message.ifBlank { stringResource(R.string.error_generic) },
            modifier = modifier,
        )
    } else {
        PremiumErrorState(
            title = message.ifBlank { stringResource(R.string.error_generic) },
            retryLabel = stringResource(R.string.retry),
            onRetry = onRetry,
            modifier = modifier,
        )
    }
}

@Composable
fun EmptyPane(message: String, modifier: Modifier = Modifier) {
    PremiumEmptyState(message, modifier)
}

@Composable
fun SectionTitle(text: String, modifier: Modifier = Modifier) {
    PremiumSectionTitle(text, modifier.padding(horizontal = 16.dp, vertical = 6.dp))
}

@Composable
fun ChoiceChips(
    values: List<Pair<String, String>>,
    selected: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        values.forEach { (value, label) ->
            FilterChip(
                selected = value == selected,
                onClick = { onSelected(value) },
                label = { Text(label) },
                shape = RoundedCornerShape(999.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        }
    }
}

@Composable
fun ConfirmDialog(title: String, body: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text(title, fontWeight = FontWeight.ExtraBold) },
        text = { Text(body, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        confirmButton = {
            Button(onClick = onConfirm, shape = RoundedCornerShape(14.dp)) {
                Text(stringResource(R.string.done), fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        },
    )
}

@Composable
fun SimpleListPicker(
    title: String,
    items: List<Pair<String, String>>,
    onPick: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text(title, fontWeight = FontWeight.ExtraBold) },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(items, key = { it.first }) { item ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth().clickable { onPick(item.first) },
                    ) {
                        Row(Modifier.fillMaxWidth().padding(14.dp)) {
                            Text(item.second, Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
                            Icon(Icons.Default.ChevronRight, contentDescription = null)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.close)) }
        },
    )
}
