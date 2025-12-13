package de.photon.effects;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainCommand implements CommandExecutor, TabExecutor {
    private static final String PREFIX = ChatColor.DARK_RED + "[Effects] ";

    private static final String COMMAND_LIST = Arrays.stream(InternalEffect.values())
                                                     .map(InternalEffect::name)
                                                     .map(String::toLowerCase)
                                                     .sorted()
                                                     .collect(Collectors.joining(ChatColor.WHITE + ", \n" + ChatColor.GOLD));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if (args.length == 0) {
            sender.sendMessage(PREFIX + ChatColor.RED + "This plugin can give you permanent effects.");
            sender.sendMessage(PREFIX + ChatColor.GOLD + "Usage: /effects <effect>");
            sender.sendMessage(PREFIX + ChatColor.RED + "Possible effects: \n" + ChatColor.GOLD + COMMAND_LIST);
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only a player can use this command.");
            return true;
        }

        final InternalEffect effect = InternalEffect.byName(args[0]);

        if (effect == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Effect not found.");
            sender.sendMessage(PREFIX + ChatColor.RED + "Possible effects: \n" + ChatColor.GOLD + COMMAND_LIST);
            return true;
        }

        if (!effect.hasPermission(sender)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You don't have permission to do that.");
            return true;
        }

        // Send the player a message.
        sender.sendMessage(PREFIX + ChatColor.GOLD + effect.name() + " has been " + (
                effect.toggleEffects(player) ?
                (ChatColor.GREEN + "enabled") :
                (ChatColor.RED + "disabled")));
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        // Command only has one argument.
        if (args.length != 1) return List.of();

        final String arg = args[0].toLowerCase(Locale.ENGLISH);
        return Arrays.stream(InternalEffect.values())
                     .filter(effect -> effect.hasPermission(sender))
                     .map(InternalEffect::name)
                     .map(String::toLowerCase)
                     .filter(effectName -> effectName.startsWith(arg))
                     .sorted()
                     .toList();
    }
}
