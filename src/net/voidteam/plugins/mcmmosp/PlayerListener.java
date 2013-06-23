package net.voidteam.plugins.mcmmosp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;

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
		String skill = e.getSkill().toString().toLowerCase();
		int skill_level = e.getSkillLevel();
		FileConfiguration file = plugin.getConfig();

		for(String group : file.getConfigurationSection("groups").getKeys(false))
		{
			for(String configSkill : file.getConfigurationSection("groups." + group + ".skills").getKeys(false))
			{
				if (configSkill.equals(skill))
				{
					player.sendMessage(ChatColor.YELLOW + "The " + group + " group contains " + skill + "!");
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