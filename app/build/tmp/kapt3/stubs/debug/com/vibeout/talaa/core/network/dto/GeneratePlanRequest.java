package com.vibeout.talaa.core.network.dto;

import com.google.gson.JsonElement;
import com.vibeout.talaa.core.model.*;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0002\b!\b\u0086\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010\"\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010#\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010$\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001aJ\t\u0010%\u001a\u00020\fH\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003Jf\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010(J\u0013\u0010)\u001a\u00020\f2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\u0006H\u00d6\u0001J\t\u0010,\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0016\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u001b\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0011\u00a8\u0006-"}, d2 = {"Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;", "", "cityId", "", "mood", "budgetMin", "", "budgetMax", "durationMinutes", "preferredDistanceKm", "", "wantsNewPeople", "", "prompt", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/Double;ZLjava/lang/String;)V", "getCityId", "()Ljava/lang/String;", "getMood", "getBudgetMin", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBudgetMax", "getDurationMinutes", "()I", "getPreferredDistanceKm", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getWantsNewPeople", "()Z", "getPrompt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;ILjava/lang/Double;ZLjava/lang/String;)Lcom/vibeout/talaa/core/network/dto/GeneratePlanRequest;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class GeneratePlanRequest {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String cityId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mood = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer budgetMin = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer budgetMax = null;
    private final int durationMinutes = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double preferredDistanceKm = null;
    private final boolean wantsNewPeople = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String prompt = null;
    
    public GeneratePlanRequest(@org.jetbrains.annotations.NotNull()
    java.lang.String cityId, @org.jetbrains.annotations.NotNull()
    java.lang.String mood, @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMax, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, boolean wantsNewPeople, @org.jetbrains.annotations.Nullable()
    java.lang.String prompt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCityId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMood() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBudgetMin() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBudgetMax() {
        return null;
    }
    
    public final int getDurationMinutes() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getPreferredDistanceKm() {
        return null;
    }
    
    public final boolean getWantsNewPeople() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPrompt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component6() {
        return null;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.network.dto.GeneratePlanRequest copy(@org.jetbrains.annotations.NotNull()
    java.lang.String cityId, @org.jetbrains.annotations.NotNull()
    java.lang.String mood, @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMin, @org.jetbrains.annotations.Nullable()
    java.lang.Integer budgetMax, int durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Double preferredDistanceKm, boolean wantsNewPeople, @org.jetbrains.annotations.Nullable()
    java.lang.String prompt) {
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