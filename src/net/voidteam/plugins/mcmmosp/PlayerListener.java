package net.voidteam.plugins.mcmmosp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
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
		String skill = e.getSkill().toString().toLowerCase();
		int skill_level = e.getSkillLevel();
		FileConfiguration file = plugin.getConfig();

		//if (skill_level % 50 == 0) // For efficiency purposes, comment out for testing.
		//{
			for(String group : file.getConfigurationSection("groups").getKeys(false))
			{
				for(String configSkill : file.getConfigurationSection("groups." + group + ".skills").getKeys(false))
				{
					int configSkillLevel = file.getInt("groups." + group + ".skills." + configSkill);
					if (configSkill.equals(skill) && skill_level >= configSkillLevel)
					{
						player.sendMessage(ChatColor.AQUA + "You are eligible for the group " + group + " with " + skill + " level " + configSkillLevel + "!");
					}
				}
			}
		//}
	}
	
	// On mcMMO Player Experience Gain
	//@EventHandler
	//public void onMcMMOPlayerXpGainEvent(McMMOPlayerXpGainEvent e)
	//{
		// May come in handy.
	//}
}