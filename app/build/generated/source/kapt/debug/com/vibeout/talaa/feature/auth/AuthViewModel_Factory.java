package com.vibeout.talaa.feature.auth;

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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  public AuthViewModel_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AuthViewModel_Factory create(Provider<AppRepository> repositoryProvider) {
    return new AuthViewModel_Factory(repositoryProvider);
  }

  public static AuthViewModel newInstance(AppRepository repository) {
    return new AuthViewModel(repository);
  }
}
