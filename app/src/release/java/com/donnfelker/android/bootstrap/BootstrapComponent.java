package com.laurinka.android.golf.puttingzones;

import com.laurinka.android.golf.puttingzones.authenticator.BootstrapAuthenticatorActivity;
import com.laurinka.android.golf.puttingzones.core.TimerService;
import com.laurinka.android.golf.puttingzones.ui.BootstrapActivity;
import com.laurinka.android.golf.puttingzones.ui.BootstrapFragmentActivity;
import com.laurinka.android.golf.puttingzones.ui.BootstrapTimerActivity;
import com.laurinka.android.golf.puttingzones.ui.CheckInsListFragment;
import com.laurinka.android.golf.puttingzones.ui.MainActivity;
import com.laurinka.android.golf.puttingzones.ui.NavigationDrawerFragment;
import com.laurinka.android.golf.puttingzones.ui.NewsActivity;
import com.laurinka.android.golf.puttingzones.ui.NewsListFragment;
import com.laurinka.android.golf.puttingzones.ui.UserActivity;
import com.laurinka.android.golf.puttingzones.ui.UserListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AndroidModule.class,
                BootstrapModule.class
        }
)
public interface BootstrapComponent {

    void inject(BootstrapApplication target);

    void inject(BootstrapAuthenticatorActivity target);

    void inject(BootstrapTimerActivity target);

    void inject(MainActivity target);

    void inject(CheckInsListFragment target);

    void inject(NavigationDrawerFragment target);

    void inject(NewsActivity target);

    void inject(NewsListFragment target);

    void inject(UserActivity target);

    void inject(UserListFragment target);

    void inject(TimerService target);

    void inject(BootstrapFragmentActivity target);
    void inject(BootstrapActivity target);


}
