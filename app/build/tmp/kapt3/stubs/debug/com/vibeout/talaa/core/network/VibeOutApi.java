package com.vibeout.talaa.core.network;

import com.vibeout.talaa.core.model.*;
import com.vibeout.talaa.core.network.dto.*;
import retrofit2.Response;
import retrofit2.http.*;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u00fc\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J \u0010\u0002\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ$\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J$\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J$\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020\u0015H\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001a\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ$\u0010\u001f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\"0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020$H\u00a7@\u00a2\u0006\u0002\u0010%Jp\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u00050\u00040\u00032\n\b\u0003\u0010(\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010)\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010*\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010+\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010,\u001a\u0004\u0018\u00010\n2\b\b\u0003\u0010-\u001a\u00020.2\b\b\u0003\u0010/\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u00100Jr\u00101\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u00050\u00040\u00032\b\b\u0001\u0010(\u001a\u00020\n2\n\b\u0003\u0010)\u001a\u0004\u0018\u00010\n2\n\b\u0003\u00102\u001a\u0004\u0018\u00010.2\n\b\u0003\u00103\u001a\u0004\u0018\u00010.2\n\b\u0003\u00104\u001a\u0004\u0018\u0001052\n\b\u0003\u00106\u001a\u0004\u0018\u0001052\n\b\u0003\u00107\u001a\u0004\u0018\u000105H\u00a7@\u00a2\u0006\u0002\u00108J$\u00109\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ$\u0010:\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ$\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ \u0010<\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\'0\u00050\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010=\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020?H\u00a7@\u00a2\u0006\u0002\u0010@J \u0010A\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0\u00050\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010B\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ.\u0010C\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020>0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020DH\u00a7@\u00a2\u0006\u0002\u0010EJd\u0010F\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020G0\u00050\u00040\u00032\n\b\u0003\u0010(\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010)\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010H\u001a\u0004\u0018\u00010\n2\n\b\u0003\u0010I\u001a\u0004\u0018\u00010\n2\b\b\u0003\u0010-\u001a\u00020.2\b\b\u0003\u0010/\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010JJ$\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020G0\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020LH\u00a7@\u00a2\u0006\u0002\u0010MJ$\u0010N\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020G0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ.\u0010O\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020QH\u00a7@\u00a2\u0006\u0002\u0010RJ$\u0010S\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ.\u0010T\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020UH\u00a7@\u00a2\u0006\u0002\u0010VJ.\u0010W\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020XH\u00a7@\u00a2\u0006\u0002\u0010YJ.\u0010Z\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020G0\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020[H\u00a7@\u00a2\u0006\u0002\u0010\\J>\u0010]\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020^0\u00050\u00040\u00032\b\b\u0001\u0010_\u001a\u00020\n2\b\b\u0003\u0010-\u001a\u00020.2\b\b\u0003\u0010/\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010`J.\u0010a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020^0\u00040\u00032\b\b\u0001\u0010_\u001a\u00020\n2\b\b\u0001\u0010\u000e\u001a\u00020bH\u00a7@\u00a2\u0006\u0002\u0010cJ$\u0010d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020eH\u00a7@\u00a2\u0006\u0002\u0010fJ,\u0010g\u001a \u0012\u001c\u0012\u001a\u0012\u0016\u0012\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010h0\u00050\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J$\u0010i\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\u000e\u001a\u00020jH\u00a7@\u00a2\u0006\u0002\u0010kJ$\u0010l\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010m\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ \u0010n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020 0\u00050\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007J4\u0010o\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020p0\u00050\u00040\u00032\b\b\u0003\u0010-\u001a\u00020.2\b\b\u0003\u0010/\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010qJ$\u0010r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u001a\u0010s\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006t"}, d2 = {"Lcom/vibeout/talaa/core/network/VibeOutApi;", "", "getCities", "Lretrofit2/Response;", "Lcom/vibeout/talaa/core/network/dto/ApiEnvelope;", "", "Lcom/vibeout/talaa/core/model/City;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCity", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/vibeout/talaa/core/network/dto/AuthDataDto;", "body", "Lcom/vibeout/talaa/core/network/dto/RegisterRequest;", "(Lcom/vibeout/talaa/core/network/dto/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "Lcom/vibeout/talaa/core/network/dto/LoginRequest;", "(Lcom/vibeout/talaa/core/network/dto/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "refresh", "Lcom/vibeout/talaa/core/network/dto/RefreshRequest;", "(Lcom/vibeout/talaa/core/network/dto/RefreshRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "authMe", "Lcom/vibeout/talaa/core/model/User;", "getMe", "updateMe", "Lcom/vibeout/talaa/core/network/dto/UpdateUserRequest;", "(Lcom/vibeout/talaa/core/network/dto/UpdateUserRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPublicUser", "Lcom/vibeout/talaa/core/model/PublicUser;", "getPreferences", "Lcom/vibeout/talaa/core/model/UserPreference;", "updatePreferences", "Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;", "(Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlaces", "Lcom/vibeout/talaa/core/model/Place;", "cityId", "mood", "placeType", "priceLevel", "search", "page", "", "limit", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecommendedPlaces", "budgetMin", "budgetMax", "distanceKm", "", "latitude", "longitude", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/lang/Double;Ljava/lang/Double;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlace", "savePlace", "unsavePlace", "getSavedPlaces", "generatePlan", "Lcom/vibeout/talaa/core/model/AiPlanResult;", "Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;", "(Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPlanHistory", "getPlan", "selectPlan", "Lcom/vibeout/talaa/core/network/dto/SelectPlanRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/SelectPlanRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVibes", "Lcom/vibeout/talaa/core/model/Vibe;", "status", "language", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createVibe", "Lcom/vibeout/talaa/core/network/dto/CreateVibeRequest;", "(Lcom/vibeout/talaa/core/network/dto/CreateVibeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVibe", "joinVibe", "Lcom/vibeout/talaa/core/model/VibeParticipant;", "Lcom/vibeout/talaa/core/network/dto/JoinVibeRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/JoinVibeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "leaveVibe", "approveParticipant", "Lcom/vibeout/talaa/core/network/dto/UserIdRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/UserIdRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "rejectParticipant", "Lcom/vibeout/talaa/core/network/dto/RejectParticipantRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/RejectParticipantRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelVibe", "Lcom/vibeout/talaa/core/network/dto/CancelVibeRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/CancelVibeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessages", "Lcom/vibeout/talaa/core/model/ChatMessage;", "vibeId", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendMessage", "Lcom/vibeout/talaa/core/network/dto/SendMessageRequest;", "(Ljava/lang/String;Lcom/vibeout/talaa/core/network/dto/SendMessageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createReport", "Lcom/vibeout/talaa/core/network/dto/CreateReportRequest;", "(Lcom/vibeout/talaa/core/network/dto/CreateReportRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMyReports", "", "blockUser", "Lcom/vibeout/talaa/core/network/dto/CreateBlockRequest;", "(Lcom/vibeout/talaa/core/network/dto/CreateBlockRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unblockUser", "blockedUserId", "getBlocks", "getNotifications", "Lcom/vibeout/talaa/core/model/NotificationItem;", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markNotificationRead", "markAllNotificationsRead", "app_debug"})
public abstract interface VibeOutApi {
    
    @retrofit2.http.GET(value = "cities")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCities(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.City>>>> $completion);
    
    @retrofit2.http.GET(value = "cities/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCity(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.City>>> $completion);
    
    @retrofit2.http.POST(value = "auth/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object register(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.RegisterRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.network.dto.AuthDataDto>>> $completion);
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.LoginRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.network.dto.AuthDataDto>>> $completion);
    
    @retrofit2.http.POST(value = "auth/refresh")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object refresh(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.RefreshRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.network.dto.AuthDataDto>>> $completion);
    
    @retrofit2.http.POST(value = "auth/logout")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "auth/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object authMe(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.User>>> $completion);
    
    @retrofit2.http.GET(value = "users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMe(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.User>>> $completion);
    
    @retrofit2.http.PUT(value = "users/me")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateMe(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.UpdateUserRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.User>>> $completion);
    
    @retrofit2.http.GET(value = "users/{id}/public")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPublicUser(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.PublicUser>>> $completion);
    
    @retrofit2.http.GET(value = "users/preferences")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPreferences(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.UserPreference>>> $completion);
    
    @retrofit2.http.PUT(value = "users/preferences")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updatePreferences(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.UpdatePreferencesRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.UserPreference>>> $completion);
    
    @retrofit2.http.GET(value = "places")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlaces(@retrofit2.http.Query(value = "cityId")
    @org.jetbrains.annotations.Nullable()
    java.lang.String cityId, @retrofit2.http.Query(value = "mood")
    @org.jetbrains.annotations.Nullable()
    java.lang.String mood, @retrofit2.http.Query(value = "placeType")
    @org.jetbrains.annotations.Nullable()
    java.lang.String placeType, @retrofit2.http.Query(value = "priceLevel")
    @org.jetbrains.annotations.Nullable()
    java.lang.String priceLevel, @retrofit2.http.Query(value = "search")
    @org.jetbrains.annotations.Nullable()
    java.lang.String search, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.Place>>>> $completion);
    
    @retrofit2.http.GET(value = "places/recommended")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getRecommendedPlaces(@retrofit2.http.Query(value = "cityId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String cityId, @retrofit2.http.Query(value = "mood")
    @org.jetbrains.annotations.Nullable()
    java.lang.String mood, @retrofit2.http.Query(value = "budgetMin")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMin, @retrofit2.http.Query(value = "budgetMax")
    @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMax, @retrofit2.http.Query(value = "distanceKm")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double distanceKm, @retrofit2.http.Query(value = "latitude")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double latitude, @retrofit2.http.Query(value = "longitude")
    @org.jetbrains.annotations.Nullable()
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.Place>>>> $completion);
    
    @retrofit2.http.GET(value = "places/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlace(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.Place>>> $completion);
    
    @retrofit2.http.POST(value = "places/{id}/save")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object savePlace(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.DELETE(value = "places/{id}/save")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object unsavePlace(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "users/me/saved-places")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSavedPlaces(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.Place>>>> $completion);
    
    @retrofit2.http.POST(value = "ai/plans/generate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object generatePlan(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.GeneratePlanRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.AiPlanResult>>> $completion);
    
    @retrofit2.http.GET(value = "ai/plans/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlanHistory(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.AiPlanResult>>>> $completion);
    
    @retrofit2.http.GET(value = "ai/plans/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPlan(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.AiPlanResult>>> $completion);
    
    @retrofit2.http.POST(value = "ai/plans/{id}/select")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object selectPlan(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.SelectPlanRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.AiPlanResult>>> $completion);
    
    @retrofit2.http.GET(value = "vibes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVibes(@retrofit2.http.Query(value = "cityId")
    @org.jetbrains.annotations.Nullable()
    java.lang.String cityId, @retrofit2.http.Query(value = "mood")
    @org.jetbrains.annotations.Nullable()
    java.lang.String mood, @retrofit2.http.Query(value = "status")
    @org.jetbrains.annotations.Nullable()
    java.lang.String status, @retrofit2.http.Query(value = "language")
    @org.jetbrains.annotations.Nullable()
    java.lang.String language, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.Vibe>>>> $completion);
    
    @retrofit2.http.POST(value = "vibes")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createVibe(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.CreateVibeRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.Vibe>>> $completion);
    
    @retrofit2.http.GET(value = "vibes/{id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVibe(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.Vibe>>> $completion);
    
    @retrofit2.http.POST(value = "vibes/{id}/join")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object joinVibe(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.JoinVibeRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.VibeParticipant>>> $completion);
    
    @retrofit2.http.POST(value = "vibes/{id}/leave")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object leaveVibe(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.POST(value = "vibes/{id}/approve")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object approveParticipant(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.UserIdRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.VibeParticipant>>> $completion);
    
    @retrofit2.http.POST(value = "vibes/{id}/reject")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object rejectParticipant(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.RejectParticipantRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.VibeParticipant>>> $completion);
    
    @retrofit2.http.POST(value = "vibes/{id}/cancel")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cancelVibe(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.CancelVibeRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.Vibe>>> $completion);
    
    @retrofit2.http.GET(value = "chats/{vibeId}/messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMessages(@retrofit2.http.Path(value = "vibeId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.ChatMessage>>>> $completion);
    
    @retrofit2.http.POST(value = "chats/{vibeId}/messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendMessage(@retrofit2.http.Path(value = "vibeId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String vibeId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.SendMessageRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<com.vibeout.talaa.core.model.ChatMessage>>> $completion);
    
    @retrofit2.http.POST(value = "reports")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createReport(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.CreateReportRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "reports/my")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMyReports(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<java.util.Map<java.lang.String, java.lang.Object>>>>> $completion);
    
    @retrofit2.http.POST(value = "blocks")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object blockUser(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.dto.CreateBlockRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.DELETE(value = "blocks/{blockedUserId}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object unblockUser(@retrofit2.http.Path(value = "blockedUserId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String blockedUserId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.GET(value = "blocks")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getBlocks(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.PublicUser>>>> $completion);
    
    @retrofit2.http.GET(value = "notifications")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNotifications(@retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<java.util.List<com.vibeout.talaa.core.model.NotificationItem>>>> $completion);
    
    @retrofit2.http.PUT(value = "notifications/{id}/read")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markNotificationRead(@retrofit2.http.Path(value = "id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @retrofit2.http.PUT(value = "notifications/read-all")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markAllNotificationsRead(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.vibeout.talaa.core.network.dto.ApiEnvelope<kotlin.Unit>>> $completion);
    
    @kotlin.Metadata(mv = {2, 1, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}