package net.voidteam.plugins.mcmmosp;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class McMMOSkillPromote extends JavaPlugin implements Listener
{
	// On Plugin Enable.
		@Override
		public void onEnable()
		{
			getLogger().info("Initializing...");
			
			// Create default config if it doesn't exist yet.
			if (!new File(getDataFolder(), "config.yml").exists())
			{
				saveDefaultConfig();
			}
			
			// Load configuration.
			reloadConfiguration();
			
			// Register events.
			getServer().getPluginManager().registerEvents(this, this);
			//getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
			
			// Register command executor.
			Commands cmdExecutor = new Commands(this);
			getCommand("mcmmosp").setExecutor(cmdExecutor);
			
			PlayerListener.file = getConfig();
			
			getLogger().info("Loaded!");
		}
		
		// On Plugin Disable.
		@Override
		public void onDisable()
		{
			getLogger().info("Disabling...");
			// TODO: Place any custom disable code here.
			getLogger().info("Disabled.");
		}
		
		// Reload the config.yml and associate variables/objects.
		public void reloadConfiguration()
		{
			// Standard reload.
			reloadConfig();
			// TODO: Reload other variables/objects.
		}
}
