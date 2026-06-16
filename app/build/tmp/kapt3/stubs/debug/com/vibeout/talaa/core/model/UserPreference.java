package com.vibeout.talaa.core.model;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b(\b\u0086\b\u0018\u00002\u00020\u0001B\u0089\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010(\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0019J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010-\u001a\u00020\tH\u00c6\u0003J\t\u0010.\u001a\u00020\u000fH\u00c6\u0003J\t\u0010/\u001a\u00020\u000fH\u00c6\u0003J\t\u00100\u001a\u00020\u000fH\u00c6\u0003J\u0090\u0001\u00101\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b2\b\b\u0002\u0010\u000b\u001a\u00020\t2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\r\u001a\u00020\t2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u000fH\u00c6\u0001\u00a2\u0006\u0002\u00102J\u0013\u00103\u001a\u00020\u000f2\b\u00104\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00105\u001a\u00020\u0003H\u00d6\u0001J\t\u00106\u001a\u00020\tH\u00d6\u0001R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0014\u0010\u0015R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0016\u001a\u0004\b\u0017\u0010\u0015R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u001a\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001fR\u0011\u0010\r\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010#R\u0011\u0010\u0011\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010#\u00a8\u00067"}, d2 = {"Lcom/vibeout/talaa/core/model/UserPreference;", "", "preferredBudgetMin", "", "preferredBudgetMax", "preferredDistanceKm", "", "preferredMoods", "", "", "preferredPlaceTypes", "socialPreference", "noiseLevel", "privacyLevel", "allowNewPeople", "", "allowNotifications", "allowLocationBasedSuggestions", "<init>", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZ)V", "getPreferredBudgetMin", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPreferredBudgetMax", "getPreferredDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getPreferredMoods", "()Ljava/util/List;", "getPreferredPlaceTypes", "getSocialPreference", "()Ljava/lang/String;", "getNoiseLevel", "getPrivacyLevel", "getAllowNewPeople", "()Z", "getAllowNotifications", "getAllowLocationBasedSuggestions", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "component11", "copy", "(Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Double;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZ)Lcom/vibeout/talaa/core/model/UserPreference;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class UserPreference {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer preferredBudgetMin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer preferredBudgetMax = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double preferredDistanceKm = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> preferredMoods = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> preferredPlaceTypes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String socialPreference = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String noiseLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String privacyLevel = null;
    private final boolean allowNewPeople = false;
    private final boolean allowNotifications = false;
    private final boolean allowLocationBasedSuggestions = false;
    
    public UserPreference(@org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMax, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> preferredMoods, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> preferredPlaceTypes, @org.jetbrains.annotations.NotNull()
    java.lang.String socialPreference, @org.jetbrains.annotations.Nullable()
    java.lang.String noiseLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String privacyLevel, boolean allowNewPeople, boolean allowNotifications, boolean allowLocationBasedSuggestions) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPreferredMoods() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPreferredPlaceTypes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSocialPreference() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNoiseLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrivacyLevel() {
        return null;
    }
    
    public final boolean getAllowNewPeople() {
        return false;
    }
    
    public final boolean getAllowNotifications() {
        return false;
    }
    
    public final boolean getAllowLocationBasedSuggestions() {
        return false;
    }
    
    public UserPreference() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.model.UserPreference copy(@org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer preferredBudgetMax, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> preferredMoods, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> preferredPlaceTypes, @org.jetbrains.annotations.NotNull()
    java.lang.String socialPreference, @org.jetbrains.annotations.Nullable()
    java.lang.String noiseLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String privacyLevel, boolean allowNewPeople, boolean allowNotifications, boolean allowLocationBasedSuggestions) {
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