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

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\b\u00c2\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0005X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/vibeout/talaa/ui/Routes;", "", "<init>", "()V", "Splash", "", "Onboarding", "Login", "Register", "ProfileSetup", "Home", "Places", "Map", "Place", "Vibes", "Vibe", "CreateVibe", "Chat", "Notifications", "Profile", "Settings", "Safety", "Plan", "Report", "app_debug"})
final class Routes {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Splash = "splash";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Onboarding = "onboarding";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Login = "login";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Register = "register";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ProfileSetup = "profile_setup";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Home = "home";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Places = "places";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Map = "map";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Place = "place/{placeId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Vibes = "vibes";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Vibe = "vibe/{vibeId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CreateVibe = "create_vibe?placeId={placeId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Chat = "chat/{vibeId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Notifications = "notifications";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Profile = "profile";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Settings = "settings";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Safety = "safety";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Plan = "plan/{planId}";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String Report = "report/{targetType}/{targetId}";
    @org.jetbrains.annotations.NotNull()
    public static final com.vibeout.talaa.ui.Routes INSTANCE = null;
    
    private Routes() {
        super();
    }
}