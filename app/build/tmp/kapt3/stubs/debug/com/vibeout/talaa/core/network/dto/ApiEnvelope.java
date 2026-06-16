package com.vibeout.talaa.core.network.dto;

import com.google.gson.JsonElement;
import com.vibeout.talaa.core.model.*;

@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B?\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\t\u0010\u0019\u001a\u00020\u0004H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\u0010\u0010\u001b\u001a\u0004\u0018\u00018\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0013J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003JL\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00018\u00002\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020\u00042\b\u0010!\u001a\u0004\u0018\u00010\u0002H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0015\u0010\u0007\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006%"}, d2 = {"Lcom/vibeout/talaa/core/network/dto/ApiEnvelope;", "T", "", "success", "", "message", "", "data", "pagination", "Lcom/vibeout/talaa/core/network/dto/PaginationDto;", "error", "Lcom/vibeout/talaa/core/network/dto/ApiErrorDto;", "<init>", "(ZLjava/lang/String;Ljava/lang/Object;Lcom/vibeout/talaa/core/network/dto/PaginationDto;Lcom/vibeout/talaa/core/network/dto/ApiErrorDto;)V", "getSuccess", "()Z", "getMessage", "()Ljava/lang/String;", "getData", "()Ljava/lang/Object;", "Ljava/lang/Object;", "getPagination", "()Lcom/vibeout/talaa/core/network/dto/PaginationDto;", "getError", "()Lcom/vibeout/talaa/core/network/dto/ApiErrorDto;", "component1", "component2", "component3", "component4", "component5", "copy", "(ZLjava/lang/String;Ljava/lang/Object;Lcom/vibeout/talaa/core/network/dto/PaginationDto;Lcom/vibeout/talaa/core/network/dto/ApiErrorDto;)Lcom/vibeout/talaa/core/network/dto/ApiEnvelope;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ApiEnvelope<T extends java.lang.Object> {
    private final boolean success = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String message = null;
    @org.jetbrains.annotations.Nullable()
    private final T data = null;
    @org.jetbrains.annotations.Nullable()
    private final com.vibeout.talaa.core.network.dto.PaginationDto pagination = null;
    @org.jetbrains.annotations.Nullable()
    private final com.vibeout.talaa.core.network.dto.ApiErrorDto error = null;
    
    public ApiEnvelope(boolean success, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    T data, @org.jetbrains.annotations.Nullable()
    com.vibeout.talaa.core.network.dto.PaginationDto pagination, @org.jetbrains.annotations.Nullable()
    com.vibeout.talaa.core.network.dto.ApiErrorDto error) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final T getData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.vibeout.talaa.core.network.dto.PaginationDto getPagination() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.vibeout.talaa.core.network.dto.ApiErrorDto getError() {
        return null;
    }
    
    public ApiEnvelope() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final T component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.vibeout.talaa.core.network.dto.PaginationDto component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.vibeout.talaa.core.network.dto.ApiErrorDto component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.network.dto.ApiEnvelope<T> copy(boolean success, @org.jetbrains.annotations.NotNull()
    java.lang.String message, @org.jetbrains.annotations.Nullable()
    T data, @org.jetbrains.annotations.Nullable()
    com.vibeout.talaa.core.network.dto.PaginationDto pagination, @org.jetbrains.annotations.Nullable()
    com.vibeout.talaa.core.network.dto.ApiErrorDto error) {
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