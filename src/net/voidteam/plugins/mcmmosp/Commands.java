package net.voidteam.plugins.mcmmosp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

//Command Executor Class.
public class Commands implements CommandExecutor
{
	private final McMMOSkillPromote plugin;		// Plugin instance.

	// Constructor.
	Commands(McMMOSkillPromote instance)
	{
		plugin = instance;
	}
	
	// On Command. Handles all commands.
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		// /mcmmosp
		if (cmd.getName().equalsIgnoreCase("mcmmosp"))
		{
			// /mcmmosp, /mcmmosp version, /mcmmosp info
			if (args.length == 0 || args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("info"))
			{
				PluginDescriptionFile pdfFile = plugin.getDescription();
				sender.sendMessage( ChatColor.GREEN + pdfFile.getName() + " " + pdfFile.getVersion() + "" );

				return true;
			}
			// /mcmmosp reload
			else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("mcmmosp.reload"))
			{
				plugin.reloadConfiguration();
				sender.sendMessage(ChatColor.GREEN + "[mcMMOSkillPromote] Configuration reloaded.");

				return true;
			}
		}
		
		return false;
	}
}