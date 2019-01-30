package com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.PokemonIDs.CustomYMLStorage;

public class AutoHouseStorageManager {
    private CustomYMLStorage yml;
    private static YamlConfiguration storage;
    
    public AutoHouseStorageManager(AutoHouseController controller){
        yml = new CustomYMLStorage(controller.getPlugin(),"PokemonIDs"+File.separator+"HouseStorage.yml");
        storage = yml.getYamlConfiguration();
        yml.save();
    }
    
    public void createHouse(String housename, int tilesize){
        storage.set(housename+".size", tilesize);
        yml.save();
    }
    
    public static List<String> getHouseOwners(String housename){
        return storage.getStringList(housename+".owners");
    }
    
    public static int getHouseSize(String housename){
        return storage.getInt(housename+".size",1000000);
    }
    
    public void addHouseOwner(String housename, String uuid){
        final List<String> current = storage.getStringList(housename+".owners");
        current.add(uuid);
        storage.set(housename+".owners", current);
        yml.save();
    }
    
    public void removeHouseOwner(String housename, String uuid){
        List<String> current = storage.getStringList(housename+".owners");
        current.remove(uuid);
        storage.set(housename+".owners", (current.size()>0?current:null));
        yml.save();
    }
    
    public static Set<String> getAllHouses(){
        return storage.getKeys(false);
    }
}
