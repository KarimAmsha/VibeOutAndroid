package com.vibeout.talaa.di;

import com.vibeout.talaa.core.database.AppDatabase;
import com.vibeout.talaa.core.database.SavedPlaceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideSavedPlaceDaoFactory implements Factory<SavedPlaceDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideSavedPlaceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SavedPlaceDao get() {
    return provideSavedPlaceDao(dbProvider.get());
  }

  public static AppModule_ProvideSavedPlaceDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideSavedPlaceDaoFactory(dbProvider);
  }

  public static SavedPlaceDao provideSavedPlaceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSavedPlaceDao(db));
  }
}
