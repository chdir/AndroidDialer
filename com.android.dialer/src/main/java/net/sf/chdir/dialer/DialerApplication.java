/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package net.sf.chdir.dialer;

import android.app.Application;
import android.content.Context;
import android.os.Trace;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import com.android.contacts.common.extensions.ExtensionsFactory;
import com.android.contacts.common.testing.NeededForTesting;
import net.sf.chdir.dialer.database.FilteredNumberAsyncQueryHandler;
import net.sf.chdir.dialer.filterednumber.BlockedNumbersAutoMigrator;

public class DialerApplication extends Application {

    private static final String TAG = "DialerApplication";

    private static Context sContext;

    @Override
    public void onCreate() {
        sContext = this;
        Trace.beginSection(TAG + " onCreate");
        super.onCreate();
        Trace.beginSection(TAG + " ExtensionsFactory initialization");
        ExtensionsFactory.init(getApplicationContext());
        Trace.endSection();
        new BlockedNumbersAutoMigrator(PreferenceManager.getDefaultSharedPreferences(this),
                new FilteredNumberAsyncQueryHandler(getContentResolver())).autoMigrate();
        Trace.endSection();
    }

    @Nullable
    public static Context getContext() {
        return sContext;
    }

    @NeededForTesting
    public static void setContextForTest(Context context) {
        sContext = context;
    }
}