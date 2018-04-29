package com.osahub.rachit.streetview.server;

/**
 * Created by Rachit Goyal on 29/04/18
 */

public interface NetworkResponse<T> {

    void onData(T object);

    void onError();
}
