package de.photon.effects;

import org.bukkit.plugin.java.JavaPlugin;

public class Effects extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        this.getCommand("effects").setExecutor(MainCommand.getInstance());
        this.getCommand("effects").setTabCompleter(MainCommand.getInstance());
    }
}
