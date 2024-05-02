package com.riftforged.glove.api;

import com.riftforged.glove.api.command.MainCommand;
import com.riftforged.glove.api.phase.PluginDevelopmentPhase;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

/**
 * Represents the main class of the Glove Plugin.
 */
public abstract class GlovePlugin extends JavaPlugin
{

    private final PluginDevelopmentPhase phase;

    /**
     * Default constructor.
     * Defaults the plugin development phase to PRODUCTION for convenience.
     */
    public GlovePlugin()
    {
        this(PluginDevelopmentPhase.PRODUCTION);
    }

    /**
     * Constructor that allows the plugin development phase to be set.
     *
     * @param phase The plugin development phase.
     */
    public GlovePlugin(PluginDevelopmentPhase phase)
    {
        this.phase = phase;
    }

    /**
     * Returns the Plugin's development phase.
     * Defaults to PRODUCTION for convenience.
     *
     * @return The Plugin's development phase.
     */
    public PluginDevelopmentPhase getDevelopmentPhase()
    {
        return this.phase;
    }

    /**
     * The method to be inherited by the Glove Plugin when it is enabled.
     * This method is called when the plugin is enabled.
     * Replaces the default {@link #onEnable()} method.
     */
    public abstract void enabled();

    /**
     * The method to be inherited by the Glove Plugin when it is disabled.
     * This method is called when the plugin is disabled.
     * Replaces the default {@link #onDisable()} method.
     */
    public abstract void disabled();

    /**
     * The default onEnable method for the plugin.
     * This method should not be overridden and instead
     * the {@link #enabled()} method should be overridden.
     * @see #enabled()
     */
    @Override
    public final void onEnable()
    {
        this.hello();
        this.enabled();
        this.getSLF4JLogger().info("{} successfully enabled.", this.getName());
    }

    /**
     * The default onDisable method for the plugin.
     * This method should not be overridden and instead
     * the {@link #disabled()} method should be overridden.
     * @see #disabled()
     */
    @Override
    public final void onDisable()
    {
        this.goodbye();
        this.disabled();
        this.getSLF4JLogger().warn("{} successfully disabled.", this.getName());
    }

    /**
     * Prints a message to the console with information about the plugin.
     * This method should be called when the plugin is enabled.
     */
    private void hello()
    {
        final Logger logger = this.getSLF4JLogger();

        logger.info("enabling {}.", this.getName());

        if (!PluginDevelopmentPhase.PRODUCTION.equals(this.getDevelopmentPhase()))
        {
            logger.warn("This plugin is in {} phase.", this.getDevelopmentPhase());
            logger.warn("This plugin is not intended for production use.");
        }
    }

    /**
     * This method should be called when the plugin is disabled.
     */
    private void goodbye()
    {
        final Logger logger = this.getSLF4JLogger();

        logger.info("Disabling {}.", this.getName());
    }

    /**
     * Registers a new command with the server.
     *
     * @param label The label of the command.
     * @param executor The executor of the command.
     */
    public final void registerCommand(String label, CommandExecutor executor)
    {
        this.getSLF4JLogger().info("Registering command: {}", label);

        PluginCommand command = this.getCommand(label);

        if (command == null)
        {
            this.getSLF4JLogger().warn("Failed to register command: {}. Did you include it in the plugin.yml?", label);
            return;
        }

        command.setExecutor(executor);
        this.getSLF4JLogger().info("Successfully registered command: {}", label);
    }

    /**
     * Registers a default main command for the plugin.
     * This is completely optional, but is provided for convenience.
     * The label still needs to be provided in the plugin.yml.
     *
     * @see MainCommand
     *
     * @param label The label of the main command.
     */
    public void registerMainCommand(String label)
    {
        this.registerCommand(label, new MainCommand(label,this));
    }
}