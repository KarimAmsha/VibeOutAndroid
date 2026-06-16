package com.vibeout.talaa.data;

import com.vibeout.talaa.core.database.CityDao;
import com.vibeout.talaa.core.database.CityEntity;
import com.vibeout.talaa.core.database.SavedPlaceDao;
import com.vibeout.talaa.core.database.SavedPlaceEntity;
import com.vibeout.talaa.core.model.*;
import com.vibeout.talaa.core.network.ApiExecutor;
import com.vibeout.talaa.core.network.VibeOutApi;
import com.vibeout.talaa.core.network.dto.*;
import com.vibeout.talaa.core.storage.TokenStore;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u00c4\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\u001e\u0010\u0015\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dJ\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\b\b\u0002\u0010%\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010\'J\u000e\u0010(\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010)\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020*H\u0086@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020-2\u0006\u0010\u001b\u001a\u00020.H\u0086@\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u0002012\u0006\u0010\u001b\u001a\u000202H\u0086@\u00a2\u0006\u0002\u00103J\u0016\u00104\u001a\u0002012\u0006\u00105\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u001e\u00107\u001a\u0002012\u0006\u00105\u001a\u00020\u00172\u0006\u00108\u001a\u000209H\u0086@\u00a2\u0006\u0002\u0010:J6\u0010;\u001a\b\u0012\u0004\u0012\u00020<0#2\b\u0010=\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010>\u001a\u0004\u0018\u00010\u00172\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u0010@J\u0016\u0010A\u001a\u00020<2\u0006\u00105\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020<0#H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010C\u001a\u00020!2\u0006\u00105\u001a\u00020\u00172\u0006\u0010D\u001a\u00020&H\u0086@\u00a2\u0006\u0002\u0010EJ \u0010F\u001a\b\u0012\u0004\u0012\u00020G0#2\n\b\u0002\u0010=\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u0016\u0010H\u001a\u00020G2\u0006\u00105\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u0016\u0010I\u001a\u00020G2\u0006\u0010\u001b\u001a\u00020JH\u0086@\u00a2\u0006\u0002\u0010KJ \u0010L\u001a\u00020M2\u0006\u00105\u001a\u00020\u00172\b\u0010N\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010O\u001a\u00020!2\u0006\u00105\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u001e\u0010P\u001a\u00020M2\u0006\u0010Q\u001a\u00020\u00172\u0006\u0010R\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010S\u001a\u00020M2\u0006\u0010Q\u001a\u00020\u00172\u0006\u0010R\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J \u0010T\u001a\u00020G2\u0006\u00105\u001a\u00020\u00172\b\u0010U\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001c\u0010V\u001a\b\u0012\u0004\u0012\u00020W0#2\u0006\u0010Q\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u001e\u0010X\u001a\u00020W2\u0006\u0010Q\u001a\u00020\u00172\u0006\u0010N\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010Y\u001a\b\u0012\u0004\u0012\u00020Z0#H\u0086@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010[\u001a\u00020!2\u0006\u00105\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u00106J\u000e\u0010\\\u001a\u00020!H\u0086@\u00a2\u0006\u0002\u0010\u001fJ0\u0010]\u001a\u00020!2\u0006\u0010^\u001a\u00020\u00172\u0006\u0010_\u001a\u00020\u00172\u0006\u0010U\u001a\u00020\u00172\b\u0010`\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u0010aJ\"\u0010b\u001a\u00020!2\u0006\u0010R\u001a\u00020\u00172\n\b\u0002\u0010U\u001a\u0004\u0018\u00010\u0017H\u0086@\u00a2\u0006\u0002\u0010\u0019J\f\u0010c\u001a\u00020d*\u00020$H\u0002J\f\u0010e\u001a\u00020$*\u00020dH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006f"}, d2 = {"Lcom/vibeout/talaa/data/AppRepository;", "", "api", "Lcom/vibeout/talaa/core/network/VibeOutApi;", "executor", "Lcom/vibeout/talaa/core/network/ApiExecutor;", "tokenStore", "Lcom/vibeout/talaa/core/storage/TokenStore;", "cityDao", "Lcom/vibeout/talaa/core/database/CityDao;", "savedPlaceDao", "Lcom/vibeout/talaa/core/database/SavedPlaceDao;", "<init>", "(Lcom/vibeout/talaa/core/network/VibeOutApi;Lcom/vibeout/talaa/core/network/ApiExecutor;Lcom/vibeout/talaa/core/storage/TokenStore;Lcom/vibeout/talaa/core/database/CityDao;Lcom/vibeout/talaa/core/database/SavedPlaceDao;)V", "_currentUser", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/vibeout/talaa/core/model/User;", "currentUser", "Lkotlinx/coroutines/flow/StateFlow;", "getCurrentUser", "()Lkotlinx/coroutines/flow/StateFlow;", "login", "email", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "request", "Lcom/vibeout/talaa/core/network/dto/RegisterRequest;", "(Lcom/vibeout/talaa/core/network/dto/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "restoreSession", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "getCities", "", "Lcom/vibeout/talaa/core/model/City;", "force", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMe", "updateMe", "Lcom/vibeout/talaa/core/network/dto/UpdateUserRequest;", "(Lcom/vibeout/talaa/core/network/dto/UpdateUserRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePreferences", "Lcom/vibeout/talaa/core/model/UserPreference;", "Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;", "(Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generatePlan", "Lcom/vibeout/talaa/core/model/AiPlanResult;", "Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;", "(Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlan", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "selectPlan", "index", "", "(Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaces", "Lcom/vibeout/talaa/core/model/Place;", "cityId", "search", "mood", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlace", "getSavedPlaces", "setPlaceSaved", "saved", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVibes", "Lcom/vibeout/talaa/core/model/Vibe;", "getVibe", "createVibe", "Lcom/vibeout/talaa/core/network/dto/CreateVibeRequest;", "(Lcom/vibeout/talaa/core/network/dto/CreateVibeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinVibe", "Lcom/vibeout/talaa/core/model/VibeParticipant;", "message", "leaveVibe", "approveParticipant", "vibeId", "userId", "rejectParticipant", "cancelVibe", "reason", "getMessages", "Lcom/vibeout/talaa/core/model/ChatMessage;", "sendMessage", "getNotifications", "Lcom/vibeout/talaa/core/model/NotificationItem;", "markNotificationRead", "markAllNotificationsRead", "report", "targetType", "targetId", "description", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "blockUser", "toEntity", "Lcom/vibeout/talaa/core/database/CityEntity;", "toModel", "app_debug"})
public final class AppRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.network.VibeOutApi api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.network.ApiExecutor executor = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.storage.TokenStore tokenStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.database.CityDao cityDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.database.SavedPlaceDao savedPlaceDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.vibeout.talaa.core.model.User> _currentUser = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.core.model.User> currentUser = null;
    
    @javax.inject.Inject()
    public AppRepository(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.VibeOutApi api, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.ApiExecutor executor, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.storage.TokenStore tokenStore, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.database.CityDao cityDao, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.database.SavedPlaceDao savedPlaceDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.vibeout.talaa.core.model.User> getCurrentUser() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.RegisterRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object restoreSession(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getCities(boolean force, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.City>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMe(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateMe(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.UpdateUserRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.User> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePreferences(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.UpdatePreferencesRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.UserPreference> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object generatePlan(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.GeneratePlanRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.AiPlanResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPlan(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.AiPlanResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object selectPlan(@org.jetbrains.annotations.NotNull()
    java.lang.String id, int index, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.AiPlanResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPlaces(@org.jetbrains.annotations.Nullable()
    java.lang.String cityId, @org.jetbrains.annotations.Nullable()
    java.lang.String search, @org.jetbrains.annotations.Nullable()
    java.lang.String mood, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.Place>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getPlace(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.Place> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSavedPlaces(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.Place>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setPlaceSaved(@org.jetbrains.annotations.NotNull()
    java.lang.String id, boolean saved, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getVibes(@org.jetbrains.annotations.Nullable()
    java.lang.String cityId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.Vibe>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getVibe(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.Vibe> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createVibe(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.CreateVibeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.Vibe> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object joinVibe(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.VibeParticipant> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object leaveVibe(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object approveParticipant(@org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.VibeParticipant> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object rejectParticipant(@org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.VibeParticipant> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object cancelVibe(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.Vibe> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.ChatMessage>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vibeout.talaa.core.model.ChatMessage> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getNotifications(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vibeout.talaa.core.model.NotificationItem>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markNotificationRead(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markAllNotificationsRead(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object report(@org.jetbrains.annotations.NotNull()
    java.lang.String targetType, @org.jetbrains.annotations.NotNull()
    java.lang.String targetId, @org.jetbrains.annotations.NotNull()
    java.lang.String reason, @org.jetbrains.annotations.Nullable()
    java.lang.String description, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object blockUser(@org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.Nullable()
    java.lang.String reason, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.vibeout.talaa.core.database.CityEntity toEntity(com.vibeout.talaa.core.model.City $this$toEntity) {
        return null;
    }
    
    private final com.vibeout.talaa.core.model.City toModel(com.vibeout.talaa.core.database.CityEntity $this$toModel) {
        return null;
    }
}