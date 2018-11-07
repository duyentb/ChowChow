package com.chowchow.os.chowchow.callback;

import com.chowchow.os.chowchow.model.Route;

import java.util.List;

public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
