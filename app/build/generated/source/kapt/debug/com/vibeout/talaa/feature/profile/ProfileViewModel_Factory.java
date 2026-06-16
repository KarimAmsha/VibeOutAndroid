package com.vibeout.talaa.feature.profile;

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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  private final Provider<AppPreferences> appPreferencesProvider;

  public ProfileViewModel_Factory(Provider<AppRepository> repositoryProvider,
      Provider<AppPreferences> appPreferencesProvider) {
    this.repositoryProvider = repositoryProvider;
    this.appPreferencesProvider = appPreferencesProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(repositoryProvider.get(), appPreferencesProvider.get());
  }

  public static ProfileViewModel_Factory create(Provider<AppRepository> repositoryProvider,
      Provider<AppPreferences> appPreferencesProvider) {
    return new ProfileViewModel_Factory(repositoryProvider, appPreferencesProvider);
  }

  public static ProfileViewModel newInstance(AppRepository repository,
      AppPreferences appPreferences) {
    return new ProfileViewModel(repository, appPreferences);
  }
}
