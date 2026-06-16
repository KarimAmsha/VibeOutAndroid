@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.vibeout.talaa.feature.safety

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vibeout.talaa.R
import com.vibeout.talaa.core.model.ReportReason
import com.vibeout.talaa.data.AppRepository
import com.vibeout.talaa.ui.components.ChoiceChips
import com.vibeout.talaa.ui.designsystem.*
import com.vibeout.talaa.ui.theme.BrandEnergy
import com.vibeout.talaa.ui.theme.BrandMint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun SafetyCenterScreen(onBack: () -> Unit) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.safety_center), onBack) },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                PremiumAccentCard(
                    title = stringResource(R.string.safety_center),
                    body = stringResource(R.string.safety_tip_public),
                    icon = Icons.Default.HealthAndSafety,
                )

                PremiumSectionTitle(stringResource(R.string.safety_center))

                PremiumInfoRow(
                    icon = Icons.Default.Public,
                    title = stringResource(R.string.safety_tip_public),
                    tint = BrandMint,
                )
                PremiumInfoRow(
                    icon = Icons.Default.PrivacyTip,
                    title = stringResource(R.string.safety_tip_personal),
                    tint = BrandEnergy,
                )
                PremiumInfoRow(
                    icon = Icons.Default.Share,
                    title = stringResource(R.string.safety_tip_friend),
                )

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp)) {
                        Icon(Icons.Default.WarningAmber, contentDescription = null)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            stringResource(R.string.uncomfortable),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

data class ReportUiState(
    val sending: Boolean = false,
    val sent: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class ReportViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: AppRepository,
) : ViewModel() {
    val targetType: String = checkNotNull(savedStateHandle["targetType"])
    val targetId: String = checkNotNull(savedStateHandle["targetId"])
    private val _state = MutableStateFlow(ReportUiState())
    val state: StateFlow<ReportUiState> = _state.asStateFlow()

    fun submit(reason: ReportReason, description: String?) = viewModelScope.launch {
        _state.value = ReportUiState(sending = true)
        runCatching {
            repository.report(targetType, targetId, reason.name, description)
        }.onSuccess {
            _state.value = ReportUiState(sent = true)
        }.onFailure {
            _state.value = ReportUiState(error = it.message)
        }
    }
}

@Composable
fun ReportScreen(
    onBack: () -> Unit,
    viewModel: ReportViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    var reason by remember { mutableStateOf(ReportReason.OTHER) }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(state.sent) {
        if (state.sent) onBack()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { VibeTopBar(stringResource(R.string.report), onBack) },
    ) { padding ->
        VibeScreen(Modifier.padding(padding)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                PremiumAccentCard(
                    title = stringResource(R.string.report),
                    body = stringResource(R.string.report_description),
                    icon = Icons.Default.Flag,
                )

                PremiumSectionTitle(stringResource(R.string.report_reason))

                ChoiceChips(
                    values = ReportReason.entries.map { it.name to reportLabel(it) },
                    selected = reason.name,
                    onSelected = { reason = ReportReason.valueOf(it) },
                )

                VibeTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = stringResource(R.string.report_description),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = Icons.Default.EditNote,
                    singleLine = false,
                )

                state.error?.let {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                    ) {
                        Text(
                            it,
                            modifier = Modifier.fillMaxWidth().padding(14.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                        )
                    }
                }

                VibePrimaryButton(
                    text = stringResource(R.string.submit_report),
                    onClick = {
                        viewModel.submit(
                            reason,
                            description.takeIf(String::isNotBlank),
                        )
                    },
                    loading = state.sending,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun reportLabel(reason: ReportReason): String = stringResource(
    when (reason) {
        ReportReason.HARASSMENT -> R.string.report_harassment
        ReportReason.FAKE_PROFILE -> R.string.report_fake
        ReportReason.INAPPROPRIATE_BEHAVIOR -> R.string.report_inappropriate
        ReportReason.SPAM -> R.string.report_spam
        ReportReason.UNSAFE_PLACE -> R.string.report_unsafe
        ReportReason.HATE_OR_ABUSE -> R.string.report_hate
        ReportReason.OTHER -> R.string.report_other
    }
)
