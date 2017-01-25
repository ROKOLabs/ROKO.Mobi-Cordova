package com.rokolabs.rokomobi.instabot;

import com.rokolabs.rokomobi.base.BasePlugin;
import com.rokolabs.sdk.instabot.Instabot;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by ilya.uglov on 25.01.17.
 */

public class InstabotManager extends BasePlugin {
    private static final String LOAD_CONVERSATION = "loadConversation";

    @Override
    public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if (LOAD_CONVERSATION.equals(action)) {
            cordova.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        String conversationId = args.getString(0);
                        Instabot instabot = new Instabot();
                        instabot.show(conversationId);
                    } catch (JSONException ex) {
                        callbackContext.error("JSON not parse error");
                    }

                }
            });
            return true;
        }
        callbackContext.error("Error");
        return false;
    }
}
