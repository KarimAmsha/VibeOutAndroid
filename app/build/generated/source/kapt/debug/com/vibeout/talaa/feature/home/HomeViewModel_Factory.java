package com.vibeout.talaa.feature.home;

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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  private final Provider<AppPreferences> appPreferencesProvider;

  public HomeViewModel_Factory(Provider<AppRepository> repositoryProvider,
      Provider<AppPreferences> appPreferencesProvider) {
    this.repositoryProvider = repositoryProvider;
    this.appPreferencesProvider = appPreferencesProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), appPreferencesProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<AppRepository> repositoryProvider,
      Provider<AppPreferences> appPreferencesProvider) {
    return new HomeViewModel_Factory(repositoryProvider, appPreferencesProvider);
  }

  public static HomeViewModel newInstance(AppRepository repository, AppPreferences appPreferences) {
    return new HomeViewModel(repository, appPreferences);
  }
}
