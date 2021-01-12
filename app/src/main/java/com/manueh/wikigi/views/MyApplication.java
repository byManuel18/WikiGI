package com.manueh.wikigi.views;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application{
        private static Context sContext;

        @Override
        public void onCreate() {
            super.onCreate();
            sContext =   getApplicationContext();
            Realm.init(sContext);
            RealmConfiguration config = new RealmConfiguration
                    .Builder()
                    .name("WIKIGIDATES.realm")
                    .directory(getExternalFilesDirs(null)[1])
                    .build();
            Realm.setDefaultConfiguration(config);

            Realm realm = Realm.getDefaultInstance();
            Log.d("Realm", "path: " + realm.getPath());
        }

        public static Context getContext() {
            return sContext;
        }

}
