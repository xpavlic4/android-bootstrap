package com.laurinka.android.golf.puttingzones;

import android.accounts.AccountsException;
import android.app.Activity;

import com.laurinka.android.golf.puttingzones.core.BootstrapService;

import java.io.IOException;

public interface BootstrapServiceProvider {
    BootstrapService getService(Activity activity) throws IOException, AccountsException;
}
