package com.vibeout.talaa.core.network;

import com.google.gson.Gson;
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
public final class TokenAuthenticator_Factory implements Factory<TokenAuthenticator> {
  private final Provider<TokenStore> tokenStoreProvider;

  private final Provider<Gson> gsonProvider;

  public TokenAuthenticator_Factory(Provider<TokenStore> tokenStoreProvider,
      Provider<Gson> gsonProvider) {
    this.tokenStoreProvider = tokenStoreProvider;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public TokenAuthenticator get() {
    return newInstance(tokenStoreProvider.get(), gsonProvider.get());
  }

  public static TokenAuthenticator_Factory create(Provider<TokenStore> tokenStoreProvider,
      Provider<Gson> gsonProvider) {
    return new TokenAuthenticator_Factory(tokenStoreProvider, gsonProvider);
  }

  public static TokenAuthenticator newInstance(TokenStore tokenStore, Gson gson) {
    return new TokenAuthenticator(tokenStore, gson);
  }
}
