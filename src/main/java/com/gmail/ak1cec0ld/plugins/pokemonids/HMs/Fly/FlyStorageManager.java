package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Fly;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.pokemonids.CustomYMLStorage;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class FlyStorageManager {
    private CustomYMLStorage yml;
    private YamlConfiguration storage;
    
    public FlyStorageManager(PokemonIDs plugin){
        yml = new CustomYMLStorage(plugin,"PokemonIDs"+File.separator+"FlyPointStorage.yml");
        storage = yml.getYamlConfiguration();
    }
    
    public boolean regionContainsPoint(String region,String point){
        for(String input:storage.getStringList(region.toLowerCase())){
            if(input.equalsIgnoreCase(point)){
                return true;
            }
        }
        return false;
    }
    
    public boolean pointExists(String point){
        for(String region : storage.getKeys(false)){
            for(String names : storage.getStringList(region)){
                if(names.equalsIgnoreCase(point)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<String> getPoints(String region){
        if(storage.getKeys(false).contains(region.toLowerCase())){
            return storage.getStringList(region.toLowerCase());
        }
        return new ArrayList<String>();
    }
    
    public Set<String> getRegions(){
        return storage.getKeys(false);
    }
    
    public String getParentOfFlyPoint(String flypoint){
        for(String parent : storage.getKeys(false)){
            if(storage.getStringList(parent).contains(flypoint)){
                return parent;
            }
        }
        return "";
    }
}
