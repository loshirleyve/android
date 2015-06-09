package com.yun9.jupiter.navigation;

import java.util.List;

/**
 * Created by Leon on 15/6/6.
 */
public class NavigationConfig {

    private List<NavigationEnterConfig> enters;

    private List<NavigationHandlerConfig> handlers;

    public List<NavigationEnterConfig> getEnters() {
        return enters;
    }

    public void setEnters(List<NavigationEnterConfig> enters) {
        this.enters = enters;
    }

    public List<NavigationHandlerConfig> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<NavigationHandlerConfig> handlers) {
        this.handlers = handlers;
    }
}
