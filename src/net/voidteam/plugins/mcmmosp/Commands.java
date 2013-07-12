package net.voidteam.plugins.mcmmosp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.player.PlayerProfile;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.util.player.UserManager;

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
				sender.sendMessage(ChatColor.GREEN + "[mcMMOSkillPromote] Version: " + pdfFile.getVersion());

				return true;
			}
			// /mcmmosp reload
			else if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("mcmmosp.reload"))
			{
				plugin.reloadConfiguration();
				sender.sendMessage(ChatColor.GREEN + "[mcMMOSkillPromote] Configuration reloaded.");

				return true;
			}
			// /mcmmosp refresh [USERNAME]
			else if (args[0].equalsIgnoreCase("refresh") && sender.hasPermission("mcmmosp.refresh"))
			{
				Player player = null;
				
				// Make sure the username was specified.
				if (args.length > 1)
				{
					// If the last argument is not empty, use it on the specified player.
					if (!args[1].isEmpty())
					{
						// Get the player specified.
						player = plugin.getServer().getPlayer(args[1]);
					}
					else
					{
						// Get the sender!
						player = plugin.getServer().getPlayer(sender.getName());
					}
				}
				// Otherwise get the sender!
				else
				{
					// Get the sender!
					player = plugin.getServer().getPlayer(sender.getName());
				}
				
				PermissionManager pex = PermissionsEx.getPermissionManager();
				PermissionUser peplayer = pex.getUser(player);
				McMMOPlayer mcmmoplayer = UserManager.getPlayer(player);
				PlayerProfile mcmmoprofile = mcmmoplayer.getProfile();
				FileConfiguration file = plugin.getConfig();
				
				String[] skillTypes = {"EXCAVATION", "FISHING", "HERBALISM", "MINING", "WOODCUTTING", "AXES", "ARCHERY", "SWORDS", "TAMING", "UNARMED", "ACROBATICS", "REPAIR"};
				
				// For each group in the config.
				for(String group : file.getConfigurationSection("groups").getKeys(false))
				{
					int total = 0;
					int win = 0;
					//  For each skill in this group in the config
					for(String configSkill : file.getConfigurationSection("groups." + group + ".skills").getKeys(false))
					{
						// For each individual skill defined above, does it match the skill we are on in the previous loop?
						for (String skillType : skillTypes)
						{
							// If skill matches the one we are on in the config in this loop...
							if (configSkill.toLowerCase().equals(skillType.toLowerCase()))
							{
								int skillLevel = mcmmoprofile.getSkillLevel(SkillType.valueOf(skillType));
								int configSkillLevel = file.getInt("groups." + group + ".skills." + configSkill);
								
								// player.sendMessage(ChatColor.AQUA + "Checking skill level " + skillType + ": " + skillLevel + "...");
								
								if (skillLevel >= configSkillLevel) // If the player's skill level is equal to or greater than the skill level defined in the config for this group's skill, then mark it as a winning skill for this group
								{
									win++;
									// player.sendMessage(ChatColor.AQUA + skillType + " WIN:  " + skillLevel + "...");
								}
							}
						}
						// Count the total number of skills that this group requires in order to win the group.
						total++;
					}
					
					// If the player has the number of wins that this group requires, then....
					if (win == total)
					{
						// For each of this player's groups in permissions, does it match the group that we are on?
						int exist = 0;
						for(PermissionGroup pexgroup : peplayer.getGroups())
						{
							if (pexgroup.getName().equals(group))
							{
								// This group exists already.
								exist++;
							}
						}
						
						// This group doesn't exist, so add them to it!
						if (exist == 0)
						{
							peplayer.addGroup(group);
							player.sendMessage(ChatColor.RED + "You've been added to the group " + group + "!");
						}
					}
				}
				
				sender.sendMessage(ChatColor.GREEN + "[mcMMOSkillPromote] Groups refreshed.");
				
				return true;
			}
		}
		
		return false;
	}
}