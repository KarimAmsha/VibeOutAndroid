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

@kotlin.Metadata(mv = {2, 1, 0}, k = 2, xi = 48, d1 = {"\u00000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a4\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a,\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H\u0003\u001a4\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0011H\u0007\u001a4\u0010\u0012\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00062\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\u0013"}, d2 = {"PlacesScreen", "", "onOpenPlace", "Lkotlin/Function1;", "", "onOpenMap", "Lkotlin/Function0;", "viewModel", "Lcom/vibeout/talaa/feature/places/PlacesViewModel;", "PlaceCard", "place", "Lcom/vibeout/talaa/core/model/Place;", "onClick", "onSave", "PlaceDetailsScreen", "onBack", "onCreateVibe", "Lcom/vibeout/talaa/feature/places/PlaceDetailsViewModel;", "PlacesMapScreen", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
public final class PlacesFeatureKt {
    
    @androidx.compose.runtime.Composable()
    public static final void PlacesScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenPlace, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onOpenMap, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.places.PlacesViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PlaceCard(com.vibeout.talaa.core.model.Place place, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onSave) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlaceDetailsScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onCreateVibe, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.places.PlaceDetailsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PlacesMapScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenPlace, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.feature.places.PlacesViewModel viewModel) {
    }
}