package net.piescode.SaveCords;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Main main = Main.getInst();
		
		FileManager.checkFiles(e.getPlayer());
		main.setMap(e.getPlayer());
	}
	
}
