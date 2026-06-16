package com.vibeout.talaa.feature.profile;

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
public final class ProfileSetupViewModel_Factory implements Factory<ProfileSetupViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  public ProfileSetupViewModel_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ProfileSetupViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ProfileSetupViewModel_Factory create(Provider<AppRepository> repositoryProvider) {
    return new ProfileSetupViewModel_Factory(repositoryProvider);
  }

  public static ProfileSetupViewModel newInstance(AppRepository repository) {
    return new ProfileSetupViewModel(repository);
  }
}
