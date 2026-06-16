package com.vibeout.talaa.di;

import com.vibeout.talaa.core.network.VibeOutApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import retrofit2.Retrofit;

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
public final class AppModule_ProvideApiFactory implements Factory<VibeOutApi> {
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public VibeOutApi get() {
    return provideApi(retrofitProvider.get());
  }

  public static AppModule_ProvideApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new AppModule_ProvideApiFactory(retrofitProvider);
  }

  public static VibeOutApi provideApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideApi(retrofit));
  }
}
