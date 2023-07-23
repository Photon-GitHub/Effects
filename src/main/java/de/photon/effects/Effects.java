package de.photon.effects;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Effects extends JavaPlugin {
    @Override
    public void onEnable() {
        final PluginCommand effectCommand = this.getCommand("effects");

        if (effectCommand == null) {
            this.getLogger().severe("Could not register /effects command. Disabling plugin.");
            return;
        }

        effectCommand.setExecutor(MainCommand.getInstance());
        effectCommand.setTabCompleter(MainCommand.getInstance());
    }
}
