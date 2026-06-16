package com.vibeout.talaa.data;

import com.vibeout.talaa.core.database.CityDao;
import com.vibeout.talaa.core.database.SavedPlaceDao;
import com.vibeout.talaa.core.network.ApiExecutor;
import com.vibeout.talaa.core.network.VibeOutApi;
import com.vibeout.talaa.core.storage.TokenStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppRepository_Factory implements Factory<AppRepository> {
  private final Provider<VibeOutApi> apiProvider;

  private final Provider<ApiExecutor> executorProvider;

  private final Provider<TokenStore> tokenStoreProvider;

  private final Provider<CityDao> cityDaoProvider;

  private final Provider<SavedPlaceDao> savedPlaceDaoProvider;

  public AppRepository_Factory(Provider<VibeOutApi> apiProvider,
      Provider<ApiExecutor> executorProvider, Provider<TokenStore> tokenStoreProvider,
      Provider<CityDao> cityDaoProvider, Provider<SavedPlaceDao> savedPlaceDaoProvider) {
    this.apiProvider = apiProvider;
    this.executorProvider = executorProvider;
    this.tokenStoreProvider = tokenStoreProvider;
    this.cityDaoProvider = cityDaoProvider;
    this.savedPlaceDaoProvider = savedPlaceDaoProvider;
  }

  @Override
  public AppRepository get() {
    return newInstance(apiProvider.get(), executorProvider.get(), tokenStoreProvider.get(), cityDaoProvider.get(), savedPlaceDaoProvider.get());
  }

  public static AppRepository_Factory create(Provider<VibeOutApi> apiProvider,
      Provider<ApiExecutor> executorProvider, Provider<TokenStore> tokenStoreProvider,
      Provider<CityDao> cityDaoProvider, Provider<SavedPlaceDao> savedPlaceDaoProvider) {
    return new AppRepository_Factory(apiProvider, executorProvider, tokenStoreProvider, cityDaoProvider, savedPlaceDaoProvider);
  }

  public static AppRepository newInstance(VibeOutApi api, ApiExecutor executor,
      TokenStore tokenStore, CityDao cityDao, SavedPlaceDao savedPlaceDao) {
    return new AppRepository(api, executor, tokenStore, cityDao, savedPlaceDao);
  }
}
