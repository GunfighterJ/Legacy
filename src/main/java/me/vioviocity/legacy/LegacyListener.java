package me.vioviocity.legacy;

import java.util.Date;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LegacyListener implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
	Date now = new Date();
	Legacy.playerTime.put(event.getPlayer(), now.getTime());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
	// initialize variables
	Player player = event.getPlayer();
	Date now = new Date();
	long playerSession = (now.getTime() - Legacy.playerTime.get(player)) / 1000;
	
	// display to console
	Legacy.log.info("[Legacy] " + player.getName() + "'s session was " + playerSession + " seconds.");

	// save in config
	if (Legacy.config.contains(player.getName()))
	    Legacy.config.set(player.getName(), Legacy.config.getLong(player.getName()) + playerSession);
	else
	    Legacy.config.set(player.getName(), playerSession);
	Legacy.saveLegacyConfig();
	
	// remove from map
	Legacy.playerTime.remove(player);
    }
}