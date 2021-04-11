package com.llat.tools;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EventBus {

    /**
     *
     */
    private static final ArrayList<Listener> listeners = new ArrayList<Listener>();

    /**
     *
     */
    public static void throwEvent(Event _event) {
        EventBus.listeners.forEach((listener) -> {
            try {
                listener.catchEvent(_event);
            } catch (Exception e) {
                Logger.getLogger(EventBus.class.getName()).log(Level.SEVERE, null, e);
            }
        });
    }

    public static void addListener(Listener _listener) {
        EventBus.listeners.add(_listener);
    }

    public static void removeListener(Listener _listener) {
        EventBus.listeners.remove(_listener);
    }

    public static ArrayList<Listener> getListeners() {
        return EventBus.listeners;
    }
}
