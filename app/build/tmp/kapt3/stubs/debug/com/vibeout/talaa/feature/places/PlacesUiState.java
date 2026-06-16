package com.vibeout.talaa.feature.places;

import android.content.Intent;
import android.net.Uri;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.compose.*;
import com.vibeout.talaa.R;
import com.vibeout.talaa.core.model.Place;
import com.vibeout.talaa.data.AppRepository;
import com.vibeout.talaa.ui.common.UiState;
import com.vibeout.talaa.ui.components.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\tH\u00c6\u0003J3\u0010\u0015\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\t2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0007H\u00d6\u0001R\u001d\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001b"}, d2 = {"Lcom/vibeout/talaa/feature/places/PlacesUiState;", "", "state", "Lcom/vibeout/talaa/ui/common/UiState;", "", "Lcom/vibeout/talaa/core/model/Place;", "search", "", "savedOnly", "", "<init>", "(Lcom/vibeout/talaa/ui/common/UiState;Ljava/lang/String;Z)V", "getState", "()Lcom/vibeout/talaa/ui/common/UiState;", "getSearch", "()Ljava/lang/String;", "getSavedOnly", "()Z", "component1", "component2", "component3", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class PlacesUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.ui.common.UiState<java.util.List<com.vibeout.talaa.core.model.Place>> state = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String search = null;
    private final boolean savedOnly = false;
    
    public PlacesUiState(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.ui.common.UiState<? extends java.util.List<com.vibeout.talaa.core.model.Place>> state, @org.jetbrains.annotations.NotNull()
    java.lang.String search, boolean savedOnly) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.ui.common.UiState<java.util.List<com.vibeout.talaa.core.model.Place>> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearch() {
        return null;
    }
    
    public final boolean getSavedOnly() {
        return false;
    }
    
    public PlacesUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.ui.common.UiState<java.util.List<com.vibeout.talaa.core.model.Place>> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.feature.places.PlacesUiState copy(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.ui.common.UiState<? extends java.util.List<com.vibeout.talaa.core.model.Place>> state, @org.jetbrains.annotations.NotNull()
    java.lang.String search, boolean savedOnly) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}