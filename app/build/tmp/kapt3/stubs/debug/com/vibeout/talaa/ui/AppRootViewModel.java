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

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0014\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0010"}, d2 = {"Lcom/vibeout/talaa/ui/AppRootViewModel;", "Landroidx/lifecycle/ViewModel;", "preferences", "Lcom/vibeout/talaa/core/storage/AppPreferences;", "<init>", "(Lcom/vibeout/talaa/core/storage/AppPreferences;)V", "themeMode", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/vibeout/talaa/core/model/ThemeMode;", "getThemeMode", "()Lkotlinx/coroutines/flow/StateFlow;", "completeOnboarding", "Lkotlinx/coroutines/Job;", "onDone", "Lkotlin/Function0;", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AppRootViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.storage.AppPreferences preferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.core.model.ThemeMode> themeMode = null;
    
    @javax.inject.Inject()
    public AppRootViewModel(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.storage.AppPreferences preferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.core.model.ThemeMode> getThemeMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.Job completeOnboarding(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone) {
        return null;
    }
}