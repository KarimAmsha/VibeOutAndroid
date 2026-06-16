package com.vibeout.talaa.di;

import android.content.Context;
import androidx.room.Room;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vibeout.talaa.BuildConfig;
import com.vibeout.talaa.core.database.AppDatabase;
import com.vibeout.talaa.core.database.CityDao;
import com.vibeout.talaa.core.database.SavedPlaceDao;
import com.vibeout.talaa.core.network.*;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {2, 1, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0007J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\rH\u0007J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000f\u001a\u00020\u0005H\u0007J\u0012\u0010\u0015\u001a\u00020\u00162\b\b\u0001\u0010\u0017\u001a\u00020\u0018H\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u0016H\u0007J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001b\u001a\u00020\u0016H\u0007\u00a8\u0006\u001e"}, d2 = {"Lcom/vibeout/talaa/di/AppModule;", "", "<init>", "()V", "provideGson", "Lcom/google/gson/Gson;", "provideOkHttp", "Lokhttp3/OkHttpClient;", "authInterceptor", "Lcom/vibeout/talaa/core/network/AuthInterceptor;", "tokenAuthenticator", "Lcom/vibeout/talaa/core/network/TokenAuthenticator;", "provideRetrofit", "Lretrofit2/Retrofit;", "client", "gson", "provideApi", "Lcom/vibeout/talaa/core/network/VibeOutApi;", "retrofit", "provideExecutor", "Lcom/vibeout/talaa/core/network/ApiExecutor;", "provideDatabase", "Lcom/vibeout/talaa/core/database/AppDatabase;", "context", "Landroid/content/Context;", "provideCityDao", "Lcom/vibeout/talaa/core/database/CityDao;", "db", "provideSavedPlaceDao", "Lcom/vibeout/talaa/core/database/SavedPlaceDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class AppModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.vibeout.talaa.di.AppModule INSTANCE = null;
    
    private AppModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.google.gson.Gson provideGson() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final okhttp3.OkHttpClient provideOkHttp(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.AuthInterceptor authInterceptor, @org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.network.TokenAuthenticator tokenAuthenticator) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final retrofit2.Retrofit provideRetrofit(@org.jetbrains.annotations.NotNull()
    okhttp3.OkHttpClient client, @org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.network.VibeOutApi provideApi(@org.jetbrains.annotations.NotNull()
    retrofit2.Retrofit retrofit) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.network.ApiExecutor provideExecutor(@org.jetbrains.annotations.NotNull()
    com.google.gson.Gson gson) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.database.AppDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.database.CityDao provideCityDao(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.database.AppDatabase db) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.vibeout.talaa.core.database.SavedPlaceDao provideSavedPlaceDao(@org.jetbrains.annotations.NotNull()
    com.vibeout.talaa.core.database.AppDatabase db) {
        return null;
    }
}