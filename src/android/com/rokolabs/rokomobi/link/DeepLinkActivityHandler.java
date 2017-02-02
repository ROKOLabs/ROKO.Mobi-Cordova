package com.rokolabs.rokomobi.link;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.rokolabs.sdk.RokoMobi;
import com.rokolabs.sdk.links.ResponseVanityLink;
import com.rokolabs.sdk.links.RokoLinks;

public class DeepLinkActivityHandler extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processLink();
        finish();
        forceMainActivityReload();
    }

    private void forceMainActivityReload() {
        PackageManager pm = getPackageManager();
        Intent launchIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        startActivity(launchIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void processLink() {
        final Application application = this.getApplication();
        RokoMobi.start(application, new RokoMobi.CallbackStart() {
            @Override
            public void start() {
                RokoLinks.getByVanityLinkCmd(application, getIntent(), new RokoLinks.CallbackVanityLink() {
                    @Override
                    public void success(ResponseVanityLink responseVanityLink) {
                        LinkManager.sendExtras(responseVanityLink);
                    }

                    @Override
                    public void failure(String s) {

                    }
                });
            }
        });
    }
}

