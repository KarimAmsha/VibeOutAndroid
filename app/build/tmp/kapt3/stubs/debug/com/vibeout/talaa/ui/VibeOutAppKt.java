package com.vibeout.talaa.ui;

import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavType;
import androidx.navigation.compose.*;
import com.vibeout.talaa.R;
import com.vibeout.talaa.core.model.ThemeMode;
import com.vibeout.talaa.core.storage.AppPreferences;
import com.vibeout.talaa.feature.auth.*;
import com.vibeout.talaa.feature.home.*;
import com.vibeout.talaa.feature.places.*;
import com.vibeout.talaa.feature.profile.*;
import com.vibeout.talaa.feature.safety.*;
import com.vibeout.talaa.feature.vibes.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u0007\u00a8\u0006\u0004"}, d2 = {"VibeOutApp", "", "rootViewModel", "Lcom/vibeout/talaa/ui/AppRootViewModel;", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
public final class VibeOutAppKt {
    
    @androidx.compose.runtime.Composable()
    public static final void VibeOutApp(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.ui.AppRootViewModel rootViewModel) {
    }
}