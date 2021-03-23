package com.llat.tools;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class EventBus {
    private final ArrayList<Listener> listeners = new ArrayList<Listener>();

    public EventBus () {}

    public void throwEvent (Event _event) {
        this.listeners.forEach((listener) -> { try { listener.catchEvent(_event); } catch (Exception e) { Logger.getLogger(EventBus.class.getName()).log(Level.SEVERE, null, e); } });
    }

    public final void addListener (Listener _listener) { this.listeners.add(_listener); }

    public final void removeListener (Listener _listener) { this.listeners.remove(_listener); }

    public final ArrayList<Listener> getListeners () { return this.listeners; }
}
