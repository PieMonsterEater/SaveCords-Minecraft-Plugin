package PieMonsterEater.SaveCords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main inst;
	
	Map<String, Map<String, String>> players = new HashMap<>();

	public Main()
    {
        inst = this;
    }
    public static Main getInst()
    {
        return inst;
    }
    
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new playerJoin(), this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equals("setCords")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(args.length > 0) {
				
				Map<String, String> m = players.get(p.getDisplayName());
				m.put(args[0], "x: " + p.getLocation().getBlockX() + " y: " + p.getLocation().getBlockY() + " z: " + p.getLocation().getBlockZ());
				
				File f = new File(this.getDataFolder(), p.getDisplayName() + "-Cords.yml");
				FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
				
				ConfigurationSection keys = playerData.getConfigurationSection("keys");
				
				keys.set(args[0], "x " + p.getLocation().getBlockX() + " y " + p.getLocation().getBlockY() + " z " + p.getLocation().getBlockZ());
				try {
					playerData.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				p.sendMessage("Saved location " + args[0] + " to x: " + p.getLocation().getBlockX() + " y: " + p.getLocation().getBlockY() + " z: " + p.getLocation().getBlockZ());
				return true;
			} else p.sendMessage("§4 You must give your coords a name!");
			}
			} else if(command.getName().equals("listCords")) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					
					Map<String, String> m =  players.get(p.getDisplayName());
					
					if(m.keySet().size() != 0) {
					for(String key : m.keySet()) {
						p.sendMessage(key);
					}
					return true;
					} else p.sendMessage("§4You have not noted down any cords!");
				}
			} else if(command.getName().equals("seeCords")) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(args.length > 0) {
					
					Map<String, String> m =  players.get(p.getDisplayName());
					
					if(m.keySet().size() != 0 && m.get(args[0]) != null) {
					p.sendMessage(args[0] + ": " + m.get(args[0]));
					} else {
						p.sendMessage("§4No cords were found with name: " + args[0]);
					}
				} else p.sendMessage("§4You have to specify the name of the coords!");
				}
				return true;
			} else if(command.getName().equals("removeCords")) {
				if(sender instanceof Player) {
					Player p = (Player) sender;
					if(args.length > 0) {
					
					Map<String, String> m = players.get(p.getDisplayName());
					
					if(m.keySet().size() != 0 && m.get(args[0]) != null) {
						File f = new File(this.getDataFolder(), p.getDisplayName() + "-Cords.yml");
						YamlConfiguration playerData = YamlConfiguration.loadConfiguration(f);
						
						ConfigurationSection keys = playerData.getConfigurationSection("keys");
						
						String key = args[0];
						String cords = m.get(args[0]);
						
						keys.set(args[0], null);
						m.remove(args[0]);

						try {
							playerData.save(f);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						p.sendMessage("§aSuccessfully removed " + key + " with value " + cords);
					} else p.sendMessage("No cords with name " + args[0] + " were found!");
				} else p.sendMessage("§4You have to specify the name of the coords!");
				}
			}
		return false;
	}
	
	public void setMap(Player p) {
		Map<String, String> cordList = new HashMap<>();
		
		YamlConfiguration playerData = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), p.getDisplayName() + "-Cords.yml"));
		
		ConfigurationSection keys = playerData.getConfigurationSection("keys");
		
		for(String key : keys.getKeys(true)) {
			cordList.put(key, playerData.getString("keys." + key));
			Log.info("key: " + key + "cords: " + playerData.getString("keys." + key));
		}
		
		players.put(p.getDisplayName(), cordList);
	}
	
}
