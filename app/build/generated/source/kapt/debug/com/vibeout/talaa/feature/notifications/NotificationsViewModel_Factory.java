package com.vibeout.talaa.feature.notifications;

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
public final class NotificationsViewModel_Factory implements Factory<NotificationsViewModel> {
  private final Provider<AppRepository> repositoryProvider;

  public NotificationsViewModel_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public NotificationsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static NotificationsViewModel_Factory create(Provider<AppRepository> repositoryProvider) {
    return new NotificationsViewModel_Factory(repositoryProvider);
  }

  public static NotificationsViewModel newInstance(AppRepository repository) {
    return new NotificationsViewModel(repository);
  }
}
