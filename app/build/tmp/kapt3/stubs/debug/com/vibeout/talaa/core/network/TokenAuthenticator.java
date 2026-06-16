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
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\u0013H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/vibeout/talaa/core/network/TokenAuthenticator;", "Lokhttp3/Authenticator;", "tokenStore", "Lcom/vibeout/talaa/core/storage/TokenStore;", "gson", "Lcom/google/gson/Gson;", "<init>", "(Lcom/vibeout/talaa/core/storage/TokenStore;Lcom/google/gson/Gson;)V", "refreshClient", "Lokhttp3/OkHttpClient;", "getRefreshClient", "()Lokhttp3/OkHttpClient;", "refreshClient$delegate", "Lkotlin/Lazy;", "authenticate", "Lokhttp3/Request;", "route", "Lokhttp3/Route;", "response", "Lokhttp3/Response;", "responseCount", "", "app_debug"})
public final class TokenAuthenticator implements okhttp3.Authenticator {
    @org.jetbrains.annotations.NotNull()
    private final com.vibeout.talaa.core.storage.TokenStore tokenStore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy refreshClient$delegate = null;
    
    @javax.inject.Inject()
    public TokenAuthenticator(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.storage.TokenStore tokenStore, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        super();
    }
    
    private final okhttp3.OkHttpClient getRefreshClient() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public okhttp3.Request authenticate(@org.jetbrains.annotations.Nullable()
    okhttp3.Route route, @org.jetbrains.annotations.NotNull()
    okhttp3.Response response) {
        return null;
    }
    
    private final int responseCount(okhttp3.Response response) {
        return 0;
    }
}