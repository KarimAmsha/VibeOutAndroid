package com.vibeout.talaa.core.network;

import com.google.gson.Gson;
import com.vibeout.talaa.BuildConfig;
import com.vibeout.talaa.core.network.dto.ApiEnvelope;
import com.vibeout.talaa.core.network.dto.AuthDataDto;
import com.vibeout.talaa.core.network.dto.RefreshRequest;
import com.vibeout.talaa.core.storage.TokenStore;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/vibeout/talaa/core/network/AuthInterceptor;", "Lokhttp3/Interceptor;", "tokenStore", "Lcom/vibeout/talaa/core/storage/TokenStore;", "<init>", "(Lcom/vibeout/talaa/core/storage/TokenStore;)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "app_debug"})
public final class AuthInterceptor implements okhttp3.Interceptor {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.storage.TokenStore tokenStore = null;
    
    @javax.inject.Inject()
    public AuthInterceptor(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.storage.TokenStore tokenStore) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull()
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
}