package com.github.pk7r.simplexevents.listeners;

import com.github.pk7r.simplexevents.Main;
import org.bukkit.plugin.PluginManager;

public class ListenerRegister {

    public ListenerRegister(Main plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new ChatEventListener(), plugin);
        pm.registerEvents(new GenericEventListener(), plugin);
    }
}
