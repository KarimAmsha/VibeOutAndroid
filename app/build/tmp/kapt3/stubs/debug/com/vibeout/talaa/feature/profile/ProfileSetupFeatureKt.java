package com.vibeout.talaa.feature.profile;

import androidx.compose.foundation.layout.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.ViewModel;
import com.vibeout.talaa.R;
import com.vibeout.talaa.core.model.PrivacyLevel;
import com.vibeout.talaa.core.model.SocialPreference;
import com.vibeout.talaa.core.network.dto.UpdatePreferencesRequest;
import com.vibeout.talaa.data.AppRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a,\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\fH\u0003\u00a8\u0006\r"}, d2 = {"ProfileSetupScreen", "", "onDone", "Lkotlin/Function0;", "viewModel", "Lcom/vibeout/talaa/feature/profile/ProfileSetupViewModel;", "SettingSwitch", "label", "", "checked", "", "onChecked", "Lkotlin/Function1;", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
public final class ProfileSetupFeatureKt {
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileSetupScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDone, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.profile.ProfileSetupViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SettingSwitch(java.lang.String label, boolean checked, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onChecked) {
    }
}