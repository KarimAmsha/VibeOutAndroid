package com.vibeout.talaa.feature.auth;

import com.vibeout.talaa.core.storage.AppPreferences;
import com.vibeout.talaa.data.AppRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<AppPreferences> appPreferencesProvider;

  private final Provider<AppRepository> repositoryProvider;

  public SplashViewModel_Factory(Provider<AppPreferences> appPreferencesProvider,
      Provider<AppRepository> repositoryProvider) {
    this.appPreferencesProvider = appPreferencesProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(appPreferencesProvider.get(), repositoryProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<AppPreferences> appPreferencesProvider,
      Provider<AppRepository> repositoryProvider) {
    return new SplashViewModel_Factory(appPreferencesProvider, repositoryProvider);
  }

  public static SplashViewModel newInstance(AppPreferences appPreferences,
      AppRepository repository) {
    return new SplashViewModel(appPreferences, repository);
  }
}
