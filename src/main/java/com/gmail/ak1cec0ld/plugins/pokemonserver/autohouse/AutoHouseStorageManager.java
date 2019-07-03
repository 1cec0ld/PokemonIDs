package com.gmail.ak1cec0ld.plugins.pokemonserver.autohouse;

import java.io.File;
import java.util.List;
import java.util.Set;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.pokemonserver.CustomYMLStorage;

public class AutoHouseStorageManager {
    private static CustomYMLStorage yml;
    private static YamlConfiguration storage;
    
    AutoHouseStorageManager(){
        yml = new CustomYMLStorage(PokemonServer.instance(),"PokemonServer"+File.separator+"HouseStorage.yml");
        storage = yml.getYamlConfiguration();
        yml.save();
    }
    
    static void createHouse(String housename, int tilesize){
        storage.set(housename+".size", tilesize);
        yml.save();
    }
    
    public static List<String> getHouseOwners(String housename){
        return storage.getStringList(housename+".owners");
    }
    
    public static int getHouseSize(String housename){
        return storage.getInt(housename+".size",1000000);
    }
    
    static void addHouseOwner(String housename, String uuid){
        final List<String> current = storage.getStringList(housename+".owners");
        current.add(uuid);
        storage.set(housename+".owners", current);
        yml.save();
    }
    
    static void removeHouseOwner(String housename, String uuid){
        List<String> current = storage.getStringList(housename+".owners");
        current.remove(uuid);
        storage.set(housename+".owners", (current.size()>0?current:null));
        yml.save();
    }
    
    public static Set<String> getAllHouses(){
        return storage.getKeys(false);
    }
}
