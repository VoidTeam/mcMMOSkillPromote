package net.voidteam.plugins.mcmmosp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.experience.McMMOPlayerLevelUpEvent;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerListener implements Listener
{
	public static FileConfiguration file;
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
		PermissionManager pex = PermissionsEx.getPermissionManager();
		Player player = e.getPlayer();
		PermissionUser peplayer = pex.getUser(player);
		String skill = e.getSkill().toString().toLowerCase();
		int skill_level = e.getSkillLevel();		

		for(String key : file.getConfigurationSection("groups").getKeys(false)){
			System.out.println("Config skill for " + key + ":" + file.getConfigurationSection("skills").getString(skill));
		}
	}
	
	// On mcMMO Player Experience Gain
	//@EventHandler
	//public void onMcMMOPlayerXpGainEvent(McMMOPlayerXpGainEvent e)
	//{
		// May come in handy.
	//}
}