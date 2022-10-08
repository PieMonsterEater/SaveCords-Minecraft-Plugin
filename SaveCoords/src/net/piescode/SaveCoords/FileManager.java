package net.piescode.SaveCoords;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileManager {
	private static YamlConfiguration msg;
	
	//Checks to make sure a coordinate save file exists for the user, and if not makes a new file in the form <playerName>-Cords.yml and formats it to have "keys" as the top comment
	public static void checkFiles(Player p){
        if(!Main.getInst().getDataFolder().exists()){
            Main.getInst().getDataFolder().mkdir();
        }
        if(!new File(Main.getInst().getDataFolder(), p.getUniqueId().toString() + "-Cords.yml").exists()){
        	File f = new File(Main.getInst().getDataFolder(), p.getUniqueId().toString() + "-Cords.yml");
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
