package com.vibeout.talaa.core.network.dto;

import com.google.gson.JsonElement;
import com.vibeout.talaa.core.model.*;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b)\b\u0086\b\u0018\u00002\u00020\u0001B\u0097\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010)\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u0011\u0010*\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH\u00c6\u0003J\u0011\u0010+\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bH\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010.\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\u0010\u00100\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\u0010\u00101\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010#J\u009e\u0001\u00102\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0010\b\u0002\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000fH\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u00020\u000f2\b\u00105\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00106\u001a\u00020\u0003H\u00d6\u0001J\t\u00107\u001a\u00020\tH\u00d6\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0017\u0010\u0015R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0019\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0019\u0010\n\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0013\u0010\r\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b\"\u0010#R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b%\u0010#R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010$\u001a\u0004\b&\u0010#\u00a8\u00068"}, d2 = {"Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;", "", "preferredBudgetMin", "", "preferredBudgetMax", "preferredDistanceKm", "", "preferredMoods", "", "", "preferredPlaceTypes", "socialPreference", "noiseLevel", "privacyLevel", "allowNewPeople", "", "allowNotifications", "allowLocationBasedSuggestions", "<init>", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;)V", "getPreferredBudgetMin", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPreferredBudgetMax", "getPreferredDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getPreferredMoods", "()Ljava/util/List;", "getPreferredPlaceTypes", "getSocialPreference", "()Ljava/lang/String;", "getNoiseLevel", "getPrivacyLevel", "getAllowNewPeople", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getAllowNotifications", "getAllowLocationBasedSuggestions", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;Ljava/lang/Boolean;Ljava/lang/Boolean;)Lcom/vibeout/talaa/core/network/dto/UpdatePreferencesRequest;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class UpdatePreferencesRequest {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer preferredBudgetMin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer preferredBudgetMax = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double preferredDistanceKm = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.String> preferredMoods = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.String> preferredPlaceTypes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String socialPreference = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String noiseLevel = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String privacyLevel = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean allowNewPeople = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean allowNotifications = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Boolean allowLocationBasedSuggestions = null;
    
    public UpdatePreferencesRequest(@org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMax, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> preferredMoods, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> preferredPlaceTypes, @org.jetbrains.annotations.Nullable()
    java.lang.String socialPreference, @org.jetbrains.annotations.Nullable()
    java.lang.String noiseLevel, @org.jetbrains.annotations.Nullable()
    java.lang.String privacyLevel, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowNewPeople, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowNotifications, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowLocationBasedSuggestions) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPreferredBudgetMin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPreferredBudgetMax() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPreferredDistanceKm() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> getPreferredMoods() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> getPreferredPlaceTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSocialPreference() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNoiseLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrivacyLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getAllowNewPeople() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getAllowNotifications() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean getAllowLocationBasedSuggestions() {
        return null;
    }
    
    public UpdatePreferencesRequest() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.network.dto.UpdatePreferencesRequest copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMax, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> preferredMoods, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> preferredPlaceTypes, @org.jetbrains.annotations.Nullable()
    java.lang.String socialPreference, @org.jetbrains.annotations.Nullable()
    java.lang.String noiseLevel, @org.jetbrains.annotations.Nullable()
    java.lang.String privacyLevel, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowNewPeople, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowNotifications, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean allowLocationBasedSuggestions) {
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