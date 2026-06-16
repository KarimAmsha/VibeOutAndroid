package com.vibeout.talaa;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.vibeout.talaa.core.database.AppDatabase;
import com.vibeout.talaa.core.database.CityDao;
import com.vibeout.talaa.core.database.SavedPlaceDao;
import com.vibeout.talaa.core.network.ApiExecutor;
import com.vibeout.talaa.core.network.AuthInterceptor;
import com.vibeout.talaa.core.network.TokenAuthenticator;
import com.vibeout.talaa.core.network.VibeOutApi;
import com.vibeout.talaa.core.storage.AppPreferences;
import com.vibeout.talaa.core.storage.TokenStore;
import com.vibeout.talaa.data.AppRepository;
import com.vibeout.talaa.di.AppModule_ProvideApiFactory;
import com.vibeout.talaa.di.AppModule_ProvideCityDaoFactory;
import com.vibeout.talaa.di.AppModule_ProvideDatabaseFactory;
import com.vibeout.talaa.di.AppModule_ProvideExecutorFactory;
import com.vibeout.talaa.di.AppModule_ProvideGsonFactory;
import com.vibeout.talaa.di.AppModule_ProvideOkHttpFactory;
import com.vibeout.talaa.di.AppModule_ProvideRetrofitFactory;
import com.vibeout.talaa.di.AppModule_ProvideSavedPlaceDaoFactory;
import com.vibeout.talaa.feature.auth.AuthViewModel;
import com.vibeout.talaa.feature.auth.AuthViewModel_HiltModules;
import com.vibeout.talaa.feature.auth.AuthViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.auth.AuthViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.auth.SplashViewModel;
import com.vibeout.talaa.feature.auth.SplashViewModel_HiltModules;
import com.vibeout.talaa.feature.auth.SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.auth.SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.home.HomeViewModel;
import com.vibeout.talaa.feature.home.HomeViewModel_HiltModules;
import com.vibeout.talaa.feature.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.home.PlanResultViewModel;
import com.vibeout.talaa.feature.home.PlanResultViewModel_HiltModules;
import com.vibeout.talaa.feature.home.PlanResultViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.home.PlanResultViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.notifications.NotificationsViewModel;
import com.vibeout.talaa.feature.notifications.NotificationsViewModel_HiltModules;
import com.vibeout.talaa.feature.notifications.NotificationsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.notifications.NotificationsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.places.PlaceDetailsViewModel;
import com.vibeout.talaa.feature.places.PlaceDetailsViewModel_HiltModules;
import com.vibeout.talaa.feature.places.PlaceDetailsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.places.PlaceDetailsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.places.PlacesViewModel;
import com.vibeout.talaa.feature.places.PlacesViewModel_HiltModules;
import com.vibeout.talaa.feature.places.PlacesViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.places.PlacesViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.profile.ProfileSetupViewModel;
import com.vibeout.talaa.feature.profile.ProfileSetupViewModel_HiltModules;
import com.vibeout.talaa.feature.profile.ProfileSetupViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.profile.ProfileSetupViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.profile.ProfileViewModel;
import com.vibeout.talaa.feature.profile.ProfileViewModel_HiltModules;
import com.vibeout.talaa.feature.profile.ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.profile.ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.safety.ReportViewModel;
import com.vibeout.talaa.feature.safety.ReportViewModel_HiltModules;
import com.vibeout.talaa.feature.safety.ReportViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.safety.ReportViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.vibes.ChatViewModel;
import com.vibeout.talaa.feature.vibes.ChatViewModel_HiltModules;
import com.vibeout.talaa.feature.vibes.ChatViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.vibes.ChatViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.vibes.CreateVibeViewModel;
import com.vibeout.talaa.feature.vibes.CreateVibeViewModel_HiltModules;
import com.vibeout.talaa.feature.vibes.CreateVibeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.vibes.CreateVibeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.vibes.VibeDetailsViewModel;
import com.vibeout.talaa.feature.vibes.VibeDetailsViewModel_HiltModules;
import com.vibeout.talaa.feature.vibes.VibeDetailsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.vibes.VibeDetailsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.feature.vibes.VibesViewModel;
import com.vibeout.talaa.feature.vibes.VibesViewModel_HiltModules;
import com.vibeout.talaa.feature.vibes.VibesViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.feature.vibes.VibesViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.vibeout.talaa.ui.AppRootViewModel;
import com.vibeout.talaa.ui.AppRootViewModel_HiltModules;
import com.vibeout.talaa.ui.AppRootViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import com.vibeout.talaa.ui.AppRootViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerVibeOutApplication_HiltComponents_SingletonC {
  private DaggerVibeOutApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public VibeOutApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements VibeOutApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements VibeOutApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements VibeOutApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements VibeOutApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements VibeOutApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements VibeOutApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements VibeOutApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public VibeOutApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends VibeOutApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends VibeOutApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    FragmentCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends VibeOutApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends VibeOutApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    ActivityCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(15).put(AppRootViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AppRootViewModel_HiltModules.KeyModule.provide()).put(AuthViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AuthViewModel_HiltModules.KeyModule.provide()).put(ChatViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ChatViewModel_HiltModules.KeyModule.provide()).put(CreateVibeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, CreateVibeViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(NotificationsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, NotificationsViewModel_HiltModules.KeyModule.provide()).put(PlaceDetailsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlaceDetailsViewModel_HiltModules.KeyModule.provide()).put(PlacesViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlacesViewModel_HiltModules.KeyModule.provide()).put(PlanResultViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PlanResultViewModel_HiltModules.KeyModule.provide()).put(ProfileSetupViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileSetupViewModel_HiltModules.KeyModule.provide()).put(ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileViewModel_HiltModules.KeyModule.provide()).put(ReportViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReportViewModel_HiltModules.KeyModule.provide()).put(SplashViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SplashViewModel_HiltModules.KeyModule.provide()).put(VibeDetailsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, VibeDetailsViewModel_HiltModules.KeyModule.provide()).put(VibesViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, VibesViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }
  }

  private static final class ViewModelCImpl extends VibeOutApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    Provider<AppRootViewModel> appRootViewModelProvider;

    Provider<AuthViewModel> authViewModelProvider;

    Provider<ChatViewModel> chatViewModelProvider;

    Provider<CreateVibeViewModel> createVibeViewModelProvider;

    Provider<HomeViewModel> homeViewModelProvider;

    Provider<NotificationsViewModel> notificationsViewModelProvider;

    Provider<PlaceDetailsViewModel> placeDetailsViewModelProvider;

    Provider<PlacesViewModel> placesViewModelProvider;

    Provider<PlanResultViewModel> planResultViewModelProvider;

    Provider<ProfileSetupViewModel> profileSetupViewModelProvider;

    Provider<ProfileViewModel> profileViewModelProvider;

    Provider<ReportViewModel> reportViewModelProvider;

    Provider<SplashViewModel> splashViewModelProvider;

    Provider<VibeDetailsViewModel> vibeDetailsViewModelProvider;

    Provider<VibesViewModel> vibesViewModelProvider;

    ViewModelCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        SavedStateHandle savedStateHandleParam, ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.appRootViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.chatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.createVibeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.notificationsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.placeDetailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.placesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.planResultViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.profileSetupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.reportViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.vibeDetailsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.vibesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(15).put(AppRootViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (appRootViewModelProvider))).put(AuthViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (authViewModelProvider))).put(ChatViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (chatViewModelProvider))).put(CreateVibeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (createVibeViewModelProvider))).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (homeViewModelProvider))).put(NotificationsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (notificationsViewModelProvider))).put(PlaceDetailsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (placeDetailsViewModelProvider))).put(PlacesViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (placesViewModelProvider))).put(PlanResultViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (planResultViewModelProvider))).put(ProfileSetupViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (profileSetupViewModelProvider))).put(ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (profileViewModelProvider))).put(ReportViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (reportViewModelProvider))).put(SplashViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (splashViewModelProvider))).put(VibeDetailsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (vibeDetailsViewModelProvider))).put(VibesViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) (vibesViewModelProvider))).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.vibeout.talaa.ui.AppRootViewModel
          return (T) new AppRootViewModel(singletonCImpl.appPreferencesProvider.get());

          case 1: // com.vibeout.talaa.feature.auth.AuthViewModel
          return (T) new AuthViewModel(singletonCImpl.appRepositoryProvider.get());

          case 2: // com.vibeout.talaa.feature.vibes.ChatViewModel
          return (T) new ChatViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 3: // com.vibeout.talaa.feature.vibes.CreateVibeViewModel
          return (T) new CreateVibeViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 4: // com.vibeout.talaa.feature.home.HomeViewModel
          return (T) new HomeViewModel(singletonCImpl.appRepositoryProvider.get(), singletonCImpl.appPreferencesProvider.get());

          case 5: // com.vibeout.talaa.feature.notifications.NotificationsViewModel
          return (T) new NotificationsViewModel(singletonCImpl.appRepositoryProvider.get());

          case 6: // com.vibeout.talaa.feature.places.PlaceDetailsViewModel
          return (T) new PlaceDetailsViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 7: // com.vibeout.talaa.feature.places.PlacesViewModel
          return (T) new PlacesViewModel(singletonCImpl.appRepositoryProvider.get());

          case 8: // com.vibeout.talaa.feature.home.PlanResultViewModel
          return (T) new PlanResultViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 9: // com.vibeout.talaa.feature.profile.ProfileSetupViewModel
          return (T) new ProfileSetupViewModel(singletonCImpl.appRepositoryProvider.get());

          case 10: // com.vibeout.talaa.feature.profile.ProfileViewModel
          return (T) new ProfileViewModel(singletonCImpl.appRepositoryProvider.get(), singletonCImpl.appPreferencesProvider.get());

          case 11: // com.vibeout.talaa.feature.safety.ReportViewModel
          return (T) new ReportViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 12: // com.vibeout.talaa.feature.auth.SplashViewModel
          return (T) new SplashViewModel(singletonCImpl.appPreferencesProvider.get(), singletonCImpl.appRepositoryProvider.get());

          case 13: // com.vibeout.talaa.feature.vibes.VibeDetailsViewModel
          return (T) new VibeDetailsViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.appRepositoryProvider.get());

          case 14: // com.vibeout.talaa.feature.vibes.VibesViewModel
          return (T) new VibesViewModel(singletonCImpl.appRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends VibeOutApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends VibeOutApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends VibeOutApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    Provider<AppPreferences> appPreferencesProvider;

    Provider<TokenStore> tokenStoreProvider;

    Provider<AuthInterceptor> authInterceptorProvider;

    Provider<Gson> provideGsonProvider;

    Provider<TokenAuthenticator> tokenAuthenticatorProvider;

    Provider<OkHttpClient> provideOkHttpProvider;

    Provider<Retrofit> provideRetrofitProvider;

    Provider<VibeOutApi> provideApiProvider;

    Provider<ApiExecutor> provideExecutorProvider;

    Provider<AppDatabase> provideDatabaseProvider;

    Provider<AppRepository> appRepositoryProvider;

    SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    CityDao cityDao() {
      return AppModule_ProvideCityDaoFactory.provideCityDao(provideDatabaseProvider.get());
    }

    SavedPlaceDao savedPlaceDao() {
      return AppModule_ProvideSavedPlaceDaoFactory.provideSavedPlaceDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.appPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<AppPreferences>(singletonCImpl, 0));
      this.tokenStoreProvider = DoubleCheck.provider(new SwitchingProvider<TokenStore>(singletonCImpl, 6));
      this.authInterceptorProvider = DoubleCheck.provider(new SwitchingProvider<AuthInterceptor>(singletonCImpl, 5));
      this.provideGsonProvider = DoubleCheck.provider(new SwitchingProvider<Gson>(singletonCImpl, 8));
      this.tokenAuthenticatorProvider = DoubleCheck.provider(new SwitchingProvider<TokenAuthenticator>(singletonCImpl, 7));
      this.provideOkHttpProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 4));
      this.provideRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 3));
      this.provideApiProvider = DoubleCheck.provider(new SwitchingProvider<VibeOutApi>(singletonCImpl, 2));
      this.provideExecutorProvider = DoubleCheck.provider(new SwitchingProvider<ApiExecutor>(singletonCImpl, 9));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 10));
      this.appRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AppRepository>(singletonCImpl, 1));
    }

    @Override
    public void injectVibeOutApplication(VibeOutApplication vibeOutApplication) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.vibeout.talaa.core.storage.AppPreferences
          return (T) new AppPreferences(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 1: // com.vibeout.talaa.data.AppRepository
          return (T) new AppRepository(singletonCImpl.provideApiProvider.get(), singletonCImpl.provideExecutorProvider.get(), singletonCImpl.tokenStoreProvider.get(), singletonCImpl.cityDao(), singletonCImpl.savedPlaceDao());

          case 2: // com.vibeout.talaa.core.network.VibeOutApi
          return (T) AppModule_ProvideApiFactory.provideApi(singletonCImpl.provideRetrofitProvider.get());

          case 3: // retrofit2.Retrofit
          return (T) AppModule_ProvideRetrofitFactory.provideRetrofit(singletonCImpl.provideOkHttpProvider.get(), singletonCImpl.provideGsonProvider.get());

          case 4: // okhttp3.OkHttpClient
          return (T) AppModule_ProvideOkHttpFactory.provideOkHttp(singletonCImpl.authInterceptorProvider.get(), singletonCImpl.tokenAuthenticatorProvider.get());

          case 5: // com.vibeout.talaa.core.network.AuthInterceptor
          return (T) new AuthInterceptor(singletonCImpl.tokenStoreProvider.get());

          case 6: // com.vibeout.talaa.core.storage.TokenStore
          return (T) new TokenStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.vibeout.talaa.core.network.TokenAuthenticator
          return (T) new TokenAuthenticator(singletonCImpl.tokenStoreProvider.get(), singletonCImpl.provideGsonProvider.get());

          case 8: // com.google.gson.Gson
          return (T) AppModule_ProvideGsonFactory.provideGson();

          case 9: // com.vibeout.talaa.core.network.ApiExecutor
          return (T) AppModule_ProvideExecutorFactory.provideExecutor(singletonCImpl.provideGsonProvider.get());

          case 10: // com.vibeout.talaa.core.database.AppDatabase
          return (T) AppModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
