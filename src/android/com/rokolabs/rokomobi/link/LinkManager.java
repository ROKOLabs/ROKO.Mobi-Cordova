package com.rokolabs.rokomobi.link;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.rokolabs.rokomobi.base.BasePlugin;
import com.rokolabs.sdk.links.ResponseCreateLink;
import com.rokolabs.sdk.links.ResponseVanityLink;
import com.rokolabs.sdk.links.RokoLinkType;
import com.rokolabs.sdk.links.RokoLinks;
import com.rokolabs.sdk.share.RokoShareChannelType;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LinkManager extends BasePlugin {
    private static final String CREATE_LINK = "createLink";
    private static final String HANDLE_DEEP_LINK = "handleDeepLink";
    public static final String ON_PAGE_STARTED = "onPageStarted";
    public static final String ON_PAGE_FINISHED = "onPageFinished";
    private static CordovaWebView gWebView;
    private static ResponseVanityLink gCachedExtras;
    private static boolean isReady;

    public static boolean isActive() {
        return gWebView != null;
    }

    public static void sendExtras(ResponseVanityLink extras) {
        if (extras != null) {
            if (gWebView != null) {
                Log.d("LinkManager", "Activity: "+ gWebView.getContext().getClass().getSimpleName());
                sendJavascript(extras);
            } else {
                Log.d("LinkManager", "gCachedExtras");
                gCachedExtras = extras;
            }
        }
    }


    public static void sendJavascript(ResponseVanityLink response) {
        if(!isReady){
            gCachedExtras = response;
            return;
        }
        final String script = "javascript:document.addEventListener('deviceready',function(){console.log(\"onHandleDeepLink\");onHandleDeepLink(" + gson.toJson(response.data) + ")})";
        if (gWebView != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    gWebView.sendJavascript(script);
                    Log.d("LinkManager", "sendJavascript: " + script);
                }
            });
        }
    }

    public static void init(CordovaWebView webView) {
    }

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        Log.d("LinkManager", "initialize");
        gWebView = webView;
        if(gCachedExtras != null && isReady) {
            sendJavascript(gCachedExtras);
            gCachedExtras = null;
        }
    }

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (CREATE_LINK.equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        CreateLink createLinkModel = gson.fromJson(args.getJSONObject(0).toString(), CreateLink.class);
                        RokoLinkType type;
                        switch (createLinkModel.type) {
                            case 1:
                                type = RokoLinkType.PROMO;
                                break;
                            case 2:
                                type = RokoLinkType.REFERRAL;
                                break;
                            default:
                                type = RokoLinkType.SHARE;

                        }
                        RokoLinks.createLinkWithName(createLinkModel.name,
                                type, createLinkModel.sourceURL,
                                RokoShareChannelType.getByString(createLinkModel.channelName),
                                createLinkModel.sharingCode,
                                createLinkModel.advancedSettings,
                                new RokoLinks.CallbackCreateLink() {
                                    @Override
                                    public void success(ResponseCreateLink responseCreateLink) {
                                        ResultCreateLink res = new ResultCreateLink();
                                        res.linkId = responseCreateLink.data.objectId;
                                        res.linkURL = responseCreateLink.data.link;
                                        callbackContext.success(gson.toJson(res));
                                    }

                                    @Override
                                    public void failure(String s) {
                                        callbackContext.error(s);
                                    }
                                }
                        );
                    } catch (JSONException ex) {
                        callbackContext.error("Error parse json");
                    }
                }
            });
            return true;
        }
        if (HANDLE_DEEP_LINK.equals(action)) {
            final String link = args.getString(0);
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setData(Uri.parse(link));
                    RokoLinks.getByVanityLinkCmd(cordova.getActivity(), intent, new RokoLinks.CallbackVanityLink() {
                        @Override
                        public void success(ResponseVanityLink responseVanityLink) {
                            ResponseVanityLink.Data data = responseVanityLink.data;
                            Map<String, String> result = new HashMap<String, String>();
                            result.put("name", data.name);
                            result.put("createDate", data.createDate);
                            result.put("updateDate", data.updateDate);
                            result.put("shareChannel", data.channel.name);
                            result.put("vanityLink", data.vanityLink);
                            result.put("type", data.linkType);
                            result.put("promo", data.promoCode);
                            callbackContext.success(new JSONObject(result));
                        }

                        @Override
                        public void failure(String s) {
                            callbackContext.error(s);
                        }
                    });
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public Object onMessage(String id, Object data) {
        Log.d("LinkManager", "message: "+id);
        if(ON_PAGE_STARTED.equals(id)){
            isReady = false;
        } else if(ON_PAGE_FINISHED.equals(id)){
            isReady = true;
            if(gCachedExtras != null) {
                sendJavascript(gCachedExtras);
                gCachedExtras = null;
            }
        }
        return super.onMessage(id, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LinkManager", "onDestroy");
        gWebView = null;
    }
}
