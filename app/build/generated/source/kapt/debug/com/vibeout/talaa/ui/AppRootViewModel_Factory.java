package com.vibeout.talaa.ui;

import com.vibeout.talaa.core.storage.AppPreferences;
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
public final class AppRootViewModel_Factory implements Factory<AppRootViewModel> {
  private final Provider<AppPreferences> preferencesProvider;

  public AppRootViewModel_Factory(Provider<AppPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AppRootViewModel get() {
    return newInstance(preferencesProvider.get());
  }

  public static AppRootViewModel_Factory create(Provider<AppPreferences> preferencesProvider) {
    return new AppRootViewModel_Factory(preferencesProvider);
  }

  public static AppRootViewModel newInstance(AppPreferences preferences) {
    return new AppRootViewModel(preferences);
  }
}
