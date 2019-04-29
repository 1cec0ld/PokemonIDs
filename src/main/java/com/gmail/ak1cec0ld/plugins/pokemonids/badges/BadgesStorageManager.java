package com.gmail.ak1cec0ld.plugins.pokemonids.badges;

import com.gmail.ak1cec0ld.plugins.pokemonids.CustomYMLStorage;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public class BadgesStorageManager {
    private CustomYMLStorage yml;
    private YamlConfiguration storage;
    
    public BadgesStorageManager(BadgesController controller){
        yml = new CustomYMLStorage(controller.getPlugin(),"PokemonIDs"+File.separator+"Badges.yml");
        storage = yml.getYamlConfiguration();
    }
    
    public Set<String> getAllCityNames(){
        return storage.getKeys(false);
    }
    
    public int getCityValue(String cityname){
        return storage.getInt(cityname+".value", 0);
    }
    
    public String getCityBadgeName(String cityname){
        return ChatColor.translateAlternateColorCodes('&', storage.getString(cityname+".name", ""));
    }
    
    public String getCityRegion(String cityname){
        return storage.getString(cityname+".region", "");
        
    }
}
