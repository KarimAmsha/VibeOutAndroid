package com.vibeout.talaa.di;

import com.google.gson.Gson;
import com.vibeout.talaa.core.network.ApiExecutor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class AppModule_ProvideExecutorFactory implements Factory<ApiExecutor> {
  private final Provider<Gson> gsonProvider;

  public AppModule_ProvideExecutorFactory(Provider<Gson> gsonProvider) {
    this.gsonProvider = gsonProvider;
  }

  @Override
  public ApiExecutor get() {
    return provideExecutor(gsonProvider.get());
  }

  public static AppModule_ProvideExecutorFactory create(Provider<Gson> gsonProvider) {
    return new AppModule_ProvideExecutorFactory(gsonProvider);
  }

  public static ApiExecutor provideExecutor(Gson gson) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideExecutor(gson));
  }
}
