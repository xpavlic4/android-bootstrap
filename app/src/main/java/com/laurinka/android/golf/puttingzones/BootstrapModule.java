package com.laurinka.android.golf.puttingzones;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.telephony.TelephonyManager;

import com.laurinka.android.golf.puttingzones.authenticator.ApiKeyProvider;
import com.laurinka.android.golf.puttingzones.authenticator.LogoutService;
import com.laurinka.android.golf.puttingzones.authenticator.LogoutServiceImpl;
import com.laurinka.android.golf.puttingzones.core.BootstrapService;
import com.laurinka.android.golf.puttingzones.core.Constants;
import com.laurinka.android.golf.puttingzones.core.PostFromAnyThreadBus;
import com.laurinka.android.golf.puttingzones.core.RestAdapterRequestInterceptor;
import com.laurinka.android.golf.puttingzones.core.RestErrorHandler;
import com.laurinka.android.golf.puttingzones.core.UserAgentProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module
public class BootstrapModule {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new PostFromAnyThreadBus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutServiceImpl(context, accountManager);
    }

    @Provides
    BootstrapService provideBootstrapService(RestAdapter restAdapter) {
        return new BootstrapService(restAdapter);
    }

    @Provides
    BootstrapServiceProvider provideBootstrapServiceProvider(RestAdapter restAdapter, ApiKeyProvider apiKeyProvider) {
        return new BootstrapServiceProviderImpl(restAdapter, apiKeyProvider);
    }

    @Provides
    ApiKeyProvider provideApiKeyProvider(AccountManager accountManager) {
        return new ApiKeyProvider(accountManager);
    }

    @Provides
    Gson provideGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }

    @Provides
    RestErrorHandler provideRestErrorHandler(Bus bus) {
        return new RestErrorHandler(bus);
    }

    @Provides
    UserAgentProvider providesUserAgentProvider(ApplicationInfo appInfo, PackageInfo packageInfo, TelephonyManager telephonyManager, ClassLoader classLoader) {
        return new UserAgentProvider(appInfo, packageInfo, telephonyManager, classLoader);
    }

    @Provides
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider) {
        return new RestAdapterRequestInterceptor(userAgentProvider);
    }

    @Provides
    RestAdapter provideRestAdapter(RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint(Constants.Http.URL_BASE)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

}
