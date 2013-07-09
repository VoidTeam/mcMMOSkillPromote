package net.voidteam.plugins.mcmmosp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.player.PlayerProfile;
import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;
import com.gmail.nossr50.util.player.UserManager;

// Player Listener Class.
public class PlayerListener implements Listener
{
	private final McMMOSkillPromote plugin;		// Plugin instance.
	
	// Constructor.
	public PlayerListener(McMMOSkillPromote instance)
	{
		plugin = instance;
	}
	
	// On Player Join. For Debugging.
	//@EventHandler
	//public void onPlayerJoin(PlayerJoinEvent e)
	//{
		// May come in handy.
	//}
	
	// On mcMMO Player Level Up.
	// This will handle the skill caps for each specified group.
	@EventHandler
	public void onMcMMOPlayerLevelUp(McMMOPlayerLevelUpEvent e)
	{
		Player player = e.getPlayer();
		
		PermissionManager pex = PermissionsEx.getPermissionManager();
		PermissionUser peplayer = pex.getUser(player);
		McMMOPlayer mcmmoplayer = UserManager.getPlayer(player);
		PlayerProfile mcmmoprofile = mcmmoplayer.getProfile();
		FileConfiguration file = plugin.getConfig();
		int skill_level = e.getSkillLevel();
		
		String[] skillTypes = {"EXCAVATION", "FISHING", "HERBALISM", "MINING", "WOODCUTTING", "AXES", "ARCHERY", "SWORDS", "TAMING", "UNARMED", "ACROBATICS", "REPAIR"};
		
		if (skill_level % 50 == 0) // For efficiency purposes, comment out for testing.
		{
			
			// Get the list of groups.
			for(String group : file.getConfigurationSection("groups").getKeys(false))
			{
				int total = 0;
				int win = 0;
				// Get the list of required skills for this group.
				for(String configSkill : file.getConfigurationSection("groups." + group + ".skills").getKeys(false))
				{
					// For each individual skill...
					for (String skillType : skillTypes)
					{
						if (configSkill.toLowerCase().equals(skillType.toLowerCase()))
						{
							int skillLevel = mcmmoprofile.getSkillLevel(SkillType.valueOf(skillType));
							int configSkillLevel = file.getInt("groups." + group + ".skills." + configSkill);
							
							//player.sendMessage(ChatColor.AQUA + "Checking skill level " + skillType + ": " + skillLevel + "...");
							
							if (skillLevel >= configSkillLevel)
							{
								win++;
								//player.sendMessage(ChatColor.AQUA + skillType + " WIN:  " + skillLevel + "...");
							}
						}
					}
					total++;
				}
				
				if (win == total)
				{
					//player.sendMessage(ChatColor.AQUA + "You are eligible for the " + group + " group!");
					
					int exist = 0;
					for(PermissionGroup pexgroup : peplayer.getGroups())
					{
						if (pexgroup.getName().equals(group))
						{
							exist++;
						}
					}
					
					if (exist == 0)
					{
						peplayer.addGroup(group);
						player.sendMessage(ChatColor.RED + "You've been added to the group " + group + "!");
					}
				}
			}
		}
	}
	
	// On mcMMO Player Experience Gain
	//@EventHandler
	//public void onMcMMOPlayerXpGainEvent(McMMOPlayerXpGainEvent e)
	//{
		// May come in handy.
	//}
}