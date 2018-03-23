package de.photon.effects;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor
{
    private static final String PREFIX = ChatColor.DARK_RED + "[Effects] ";

    @Getter
    private static final MainCommand instance = new MainCommand();

    private MainCommand() {}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        for (InternalEffect registeredEffect : InternalEffect.REGISTERED_EFFECTS)
        {
            // Search for possible commands.
            if (command.getName().equalsIgnoreCase(registeredEffect.getName()))
            {
                // Command is handled by Effects
                // Player-only commands in this plugin.
                if (sender instanceof Player)
                {
                    // Send the player a message.
                    sender.sendMessage(PREFIX + ChatColor.GOLD + registeredEffect.getName() + " has been " + (
                            registeredEffect.toggleEffects((Player) sender) ?
                            (ChatColor.GREEN + "enabled") :
                            (ChatColor.RED + "disabled")));
                }
                else
                {
                    sender.sendMessage(PREFIX + ChatColor.RED + "Only a player can use this command.");
                }

                // Return here so you do not iterate through the whole loop if a command has been found.
                return true;
            }
        }
        return true;
    }
}
