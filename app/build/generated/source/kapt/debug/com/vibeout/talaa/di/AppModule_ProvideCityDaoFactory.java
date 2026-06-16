package com.vibeout.talaa.di;

import com.vibeout.talaa.core.database.AppDatabase;
import com.vibeout.talaa.core.database.CityDao;
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
public final class AppModule_ProvideCityDaoFactory implements Factory<CityDao> {
  private final Provider<AppDatabase> dbProvider;

  public AppModule_ProvideCityDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CityDao get() {
    return provideCityDao(dbProvider.get());
  }

  public static AppModule_ProvideCityDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new AppModule_ProvideCityDaoFactory(dbProvider);
  }

  public static CityDao provideCityDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideCityDao(db));
  }
}
