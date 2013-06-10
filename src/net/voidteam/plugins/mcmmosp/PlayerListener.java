package net.voidteam.plugins.mcmmosp;

import org.bukkit.event.Listener;

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
	//@EventHandler
	//public void onMcMMOPlayerLevelUp(McMMOPlayerLevelUpEvent e)
	//{
		// TODO: Handle Shit.
	//}
	
	// On mcMMO Player Experience Gain
	//@EventHandler
	//public void onMcMMOPlayerXpGainEvent(McMMOPlayerXpGainEvent e)
	//{
		// May come in handy.
	//}
}