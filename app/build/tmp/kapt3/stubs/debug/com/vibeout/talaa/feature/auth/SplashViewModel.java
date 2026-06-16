package com.vibeout.talaa.feature.auth;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.input.PasswordVisualTransformation;
import androidx.compose.ui.text.input.VisualTransformation;
import androidx.lifecycle.ViewModel;
import com.vibeout.talaa.R;
import com.vibeout.talaa.core.model.City;
import com.vibeout.talaa.core.network.dto.RegisterRequest;
import com.vibeout.talaa.core.storage.AppPreferences;
import com.vibeout.talaa.data.AppRepository;
import com.vibeout.talaa.ui.common.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Locale;
import javax.inject.Inject;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/vibeout/talaa/feature/auth/SplashViewModel;", "Landroidx/lifecycle/ViewModel;", "appPreferences", "Lcom/vibeout/talaa/core/storage/AppPreferences;", "repository", "Lcom/vibeout/talaa/data/AppRepository;", "<init>", "(Lcom/vibeout/talaa/core/storage/AppPreferences;Lcom/vibeout/talaa/data/AppRepository;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/vibeout/talaa/feature/auth/SplashUiState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SplashViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.storage.AppPreferences appPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.data.AppRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.vibeout.talaa.feature.auth.SplashUiState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.feature.auth.SplashUiState> state = null;
    
    @javax.inject.Inject()
    public SplashViewModel(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.storage.AppPreferences appPreferences, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.data.AppRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.feature.auth.SplashUiState> getState() {
        return null;
    }
}