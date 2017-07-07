package com.rokolabs.rokomobi.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import com.rokolabs.rokomobi.base.BasePlugin;
import com.rokolabs.rokomobi.link.CreateLink;
import com.rokolabs.rokomobi.link.ResultCreateLink;
import com.rokolabs.sdk.RokoMobi;
import com.rokolabs.sdk.json.Json;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sobolev on 7/7/17.
 */

public class SettingsManager extends BasePlugin {
    private CordovaWebView gWebView;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.d("LinkManager", "initialize");
        gWebView = webView;
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("getSDKInfo".equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final RokoMobi rm = RokoMobi.getInstance(gWebView.getContext());
                        callbackContext.success(new JSONObject(new Json(){
                            String apiVersion = rm.settings.getString("rokosdkversion", (String)null);
                            String applicationName = "Test";
                            String mobileAppVersion = rm.settings.getString("applicationversion", (String)null);
                            String apiURL = RokoMobi.getSettings().apiUrl;
                            String apiToken = RokoMobi.getInstance(gWebView.getContext()).getSettings().apiToken;
                        }.toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        } else if ("changeEnvironment".equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    callbackContext.success();
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LinkManager", "onDestroy");
        gWebView = null;
    }
}

