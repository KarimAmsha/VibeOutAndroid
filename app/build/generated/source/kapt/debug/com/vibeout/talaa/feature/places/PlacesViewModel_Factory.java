package com.vibeout.talaa.feature.places;

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
public final class PlacesViewModel_Factory implements Factory<PlacesViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  public PlacesViewModel_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PlacesViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static PlacesViewModel_Factory create(Provider<AppRepository> repositoryProvider) {
    return new PlacesViewModel_Factory(repositoryProvider);
  }

  public static PlacesViewModel newInstance(AppRepository repository) {
    return new PlacesViewModel(repository);
  }
}
