package de.photon.effects;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MainCommand implements CommandExecutor, TabExecutor
{
    private static final String EFFECTS_PERMISSION_PREFIX = "effects.";
    private static final String PREFIX = ChatColor.DARK_RED + "[Effects] ";
    @Getter
    private static final MainCommand instance = new MainCommand();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args)
    {
        if (args.length == 0) {
            sender.sendMessage(PREFIX + ChatColor.RED + "This plugin can give you permanent effects.");
            sender.sendMessage(PREFIX + ChatColor.GOLD + "Usage: /effects <effect>");
            sender.sendMessage(PREFIX + ChatColor.RED + "Possible effects: \n" + ChatColor.GOLD + String.join(ChatColor.WHITE + ", \n" + ChatColor.GOLD, InternalEffect.REGISTERED_EFFECTS.keySet()));
            return true;
        }

        // Command is handled by Effects
        // Player-only commands in this plugin.
        if (!(sender instanceof Player)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Only a player can use this command.");
            return true;
        }

        val effect = InternalEffect.REGISTERED_EFFECTS.get(args[0].toLowerCase(Locale.ROOT));

        // Should never happen as we registered the command.
        if (effect == null) {
            sender.sendMessage(PREFIX + ChatColor.RED + "Effect not found.");
            sender.sendMessage(PREFIX + ChatColor.RED + "Possible effects: \n" + String.join(", \n", InternalEffect.REGISTERED_EFFECTS.keySet()));
            return true;
        }

        if (!sender.hasPermission(EFFECTS_PERMISSION_PREFIX + effect.getName())) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You don't have permission to do that.");
            return true;
        }


        // Send the player a message.
        sender.sendMessage(PREFIX + ChatColor.GOLD + StringUtils.capitalize(effect.getName()) + " has been " + (
                effect.toggleEffects((Player) sender) ?
                (ChatColor.GREEN + "enabled") :
                (ChatColor.RED + "disabled")));

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args)
    {
        val arg = args.length > 0 ? args[0].toLowerCase(Locale.ROOT) : "";
        return InternalEffect.REGISTERED_EFFECTS.keySet().stream()
                                                .filter(name -> sender.hasPermission(EFFECTS_PERMISSION_PREFIX + name))
                                                // The empty string will always return true.
                                                .filter(key -> key.startsWith(arg))
                                                .sorted()
                                                .collect(Collectors.toUnmodifiableList());
    }
}
