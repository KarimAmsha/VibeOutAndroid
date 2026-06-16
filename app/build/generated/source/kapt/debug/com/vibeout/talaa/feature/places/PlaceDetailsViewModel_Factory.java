package com.vibeout.talaa.feature.places;

import androidx.lifecycle.SavedStateHandle;
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
public final class PlaceDetailsViewModel_Factory implements Factory<PlaceDetailsViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<AppRepository> repositoryProvider;

  public PlaceDetailsViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AppRepository> repositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PlaceDetailsViewModel get() {
    return newInstance(savedStateHandleProvider.get(), repositoryProvider.get());
  }

  public static PlaceDetailsViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<AppRepository> repositoryProvider) {
    return new PlaceDetailsViewModel_Factory(savedStateHandleProvider, repositoryProvider);
  }

  public static PlaceDetailsViewModel newInstance(SavedStateHandle savedStateHandle,
      AppRepository repository) {
    return new PlaceDetailsViewModel(savedStateHandle, repository);
  }
}
