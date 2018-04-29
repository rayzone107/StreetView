package com.osahub.rachit.streetview.server;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Rachit Goyal on 28/04/18
 */

public interface VolleyResponse {

    void onData(JSONObject response);

    void onError(VolleyError e);
}
