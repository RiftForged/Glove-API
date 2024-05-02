package com.riftforged.glove.api.command;

import com.riftforged.glove.api.GlovePlugin;
import com.riftforged.glove.api.phase.PluginDevelopmentPhase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainCommand implements CommandExecutor, TabCompleter
{

    private final String label;
    private final GlovePlugin plugin;

    private static final TextColor PRIMARY_COLOR = TextColor.color(137, 207, 240);
    private static final TextColor SECONDARY_COLOR = TextColor.color(240, 193, 137);
    private static final TextColor TERTIARY_COLOR = TextColor.color(255, 255, 255);
    private static final TextColor WARNING_COLOR = TextColor.color(245, 214, 7);

    public MainCommand(String label, GlovePlugin plugin)
    {
        this.label = label;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments)
    {
        if (arguments.length != 1)
            return false;

        if (arguments[0].equalsIgnoreCase("help"))
        {
            sender.sendMessage(Component.text(plugin.getName() + " has the following commands: ", PRIMARY_COLOR));
            TextComponent help = Component.text("    /" + this.label + " help", SECONDARY_COLOR)
                    .append(Component.text(" - ", TERTIARY_COLOR))
                    .append(Component.text("Shows this help message.", PRIMARY_COLOR));
            sender.sendMessage(help);

            TextComponent version = Component.text("    /" + this.label + " version", SECONDARY_COLOR)
                    .append(Component.text(" - ", TERTIARY_COLOR))
                    .append(Component.text("Shows the plugin's version information", PRIMARY_COLOR));
            sender.sendMessage(version);
            return true;
        }

        if (arguments[0].equalsIgnoreCase("version"))
        {
            sender.sendMessage(Component.text("You are currently using " + plugin.getName() + " version " + plugin.getPluginMeta().getVersion(), PRIMARY_COLOR));
            sender.sendMessage(Component.text("The current Plugin Development Stage is ", PRIMARY_COLOR)
                    .append(Component.text(plugin.getDevelopmentPhase().toString(), SECONDARY_COLOR)));
            if (!plugin.getDevelopmentPhase().equals(PluginDevelopmentPhase.PRODUCTION))
            {
                TextComponent message = Component.text("This version of Glove is not meant for production use.", WARNING_COLOR);
                sender.sendMessage(message);
            }
            return true;
        }

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments)
    {
        return List.of("help", "version");
    }
}
