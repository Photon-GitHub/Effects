package de.photon.effects;

import org.bukkit.plugin.java.JavaPlugin;

public class Effects extends JavaPlugin
{
    /**
     * This will get the object of the plugin registered on the server.
     *
     * @return the active instance of this plugin on the server.
     */
    public static Effects getInstance()
    {
        return Effects.getPlugin(Effects.class);
    }
}
