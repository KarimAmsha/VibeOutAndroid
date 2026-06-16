package com.vibeout.talaa.feature.vibes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.vibeout.talaa.R;
import com.vibeout.talaa.core.model.*;
import com.vibeout.talaa.core.network.dto.CreateVibeRequest;
import com.vibeout.talaa.data.AppRepository;
import com.vibeout.talaa.ui.common.UiState;
import com.vibeout.talaa.ui.components.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a4\u0010\t\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\fH\u0007\u001a,\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001aN\u0010\u0012\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00152\b\b\u0002\u0010\u0007\u001a\u00020\u0016H\u0007\u001a:\u0010\u0017\u001a\u00020\u00012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0018\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00152\b\b\u0002\u0010\u0007\u001a\u00020\u0018H\u0007\u001a\u0010\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004H\u0002\u00a8\u0006\u001a"}, d2 = {"VibesScreen", "", "onOpen", "Lkotlin/Function1;", "", "onCreate", "Lkotlin/Function0;", "viewModel", "Lcom/vibeout/talaa/feature/vibes/VibesViewModel;", "CreateVibeScreen", "onBack", "onCreated", "Lcom/vibeout/talaa/feature/vibes/CreateVibeViewModel;", "DateTimeField", "label", "value", "Ljava/time/LocalDateTime;", "onValue", "VibeDetailsScreen", "onChat", "onReport", "Lkotlin/Function2;", "Lcom/vibeout/talaa/feature/vibes/VibeDetailsViewModel;", "ChatScreen", "Lcom/vibeout/talaa/feature/vibes/ChatViewModel;", "formatServerDate", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
public final class VibesFeatureKt {
    
    @androidx.compose.runtime.Composable()
    public static final void VibesScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpen, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCreate, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.vibes.VibesViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CreateVibeScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCreated, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.vibes.CreateVibeViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DateTimeField(java.lang.String label, java.time.LocalDateTime value, kotlin.jvm.functions.Function1<? super java.time.LocalDateTime, kotlin.Unit> onValue) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void VibeDetailsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onChat, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onReport, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.vibes.VibeDetailsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChatScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onReport, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.vibes.ChatViewModel viewModel) {
    }
    
    private static final java.lang.String formatServerDate(java.lang.String value) {
        return null;
    }
}