package de.photon.effects;

import org.bukkit.plugin.java.JavaPlugin;

public class Effects extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        InternalEffect.REGISTERED_EFFECTS.forEach(registeredEffect -> this.getCommand(registeredEffect.getName()).setExecutor(MainCommand.getInstance()));
    }

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
