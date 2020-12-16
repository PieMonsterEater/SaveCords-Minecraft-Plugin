package PieMonsterEater.SaveCords;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {
	private static YamlConfiguration msg;
	
	public static void checkFiles(Player p){
        if(!Main.getInst().getDataFolder().exists()){
            Main.getInst().getDataFolder().mkdir();
        }
        if(!new File(Main.getInst().getDataFolder(), p.getDisplayName() + "-Cords.yml").exists()){
        	File f = new File(Main.getInst().getDataFolder(), p.getDisplayName() + "-Cords.yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(f);
            
            playerData.createSection("keys");
            
            try {
				playerData.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
    }
    
    public static YamlConfiguration getMsg(){
        return msg;
    }
}
