package com.vibeout.talaa.feature.vibes;

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
public final class VibesViewModel_Factory implements Factory<VibesViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  public VibesViewModel_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public VibesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static VibesViewModel_Factory create(Provider<AppRepository> repositoryProvider) {
    return new VibesViewModel_Factory(repositoryProvider);
  }

  public static VibesViewModel newInstance(AppRepository repository) {
    return new VibesViewModel(repository);
  }
}
