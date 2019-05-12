package com.gmail.ak1cec0ld.plugins.pokemonids.secretbases;

import com.gmail.ak1cec0ld.plugins.pokemonids.CustomYMLStorage;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;

class SBStorage {



    private static CustomYMLStorage yml;
    private static YamlConfiguration storage;

    SBStorage(){
        yml = new CustomYMLStorage(PokemonIDs.instance(),"PokemonIDs"+ File.separator+"SecretBases.yml");
        storage = yml.getYamlConfiguration();
    }

    static HashMap<String,Location> getAllBases(){
        HashMap<String, Location> map = new HashMap<>();
        for(String x : storage.getKeys(false)){
            for(String y : storage.getConfigurationSection(x).getKeys(false)){
                for(String z : storage.getConfigurationSection(x+"."+y).getKeys(false)){
                    String path = x+"."+y+"."+z;
                    String name = storage.getString(path+".owner","none");
                    String world = storage.getString(path+".world","Japan");
                    Location loc = new Location(Bukkit.getWorld(world),Integer.parseInt(x),Integer.parseInt(y),Integer.parseInt(z));
                    map.put(name,loc);
                }
            }
        }
        return map;
    }

    static boolean hasBase(Location loc){
        return storage.contains(loc.getBlockX()+"") &&
               storage.contains(loc.getBlockX()+"."+loc.getBlockY()) &&
               storage.contains(loc.getBlockX()+"."+loc.getBlockY()+"."+loc.getBlockZ());
    }
    static void removeBase(Location loc){
        storage.set(loc.getBlockX()+"."+loc.getBlockY()+"."+loc.getBlockZ(),null);
        yml.save();
    }
    static Location getBaseTarget(Location base){
        if(!hasBase(base))return null;
        String worldName;
        int x;
        int y;
        int z;
        worldName = storage.getString(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".world", "Japan");
        x = storage.getInt(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".x",0);
        y = storage.getInt(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".y",0);
        z = storage.getInt(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".z",0);
        return new Location(Bukkit.getWorld(worldName),x,y,z);
    }
    static void createBase(Location base, Location target, String ownerName){
        String locationPath = base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ();
        storage.set(locationPath+".world",target.getWorld().getName());
        storage.set(locationPath+".x",target.getBlockX());
        storage.set(locationPath+".y",target.getBlockY());
        storage.set(locationPath+".z",target.getBlockZ());
        storage.set(locationPath+".owner",ownerName);
        storage.set(locationPath+".locked",false);
        PokemonIDs.debug("Created new Secretbase from "+base.toString()+" to "+target.toString()+" for "+ownerName);
        yml.save();
    }
    static boolean isOwner(Location base, String name){
        return hasBase(base) && name.equals(storage.getString(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".owner"));
    }
    static void changeOwner(Location base, String newName){
        if(!hasBase(base))return;
        storage.set(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".owner",newName);
        yml.save();
    }
    static boolean isLocked(Location base){
        if(!hasBase(base))return true;
        return storage.getBoolean(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".locked",true);
    }
    static boolean toggleLock(Location base){
        if(!hasBase(base))return true;
        boolean state = storage.getBoolean(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".locked",true);
        storage.set(base.getBlockX()+"."+base.getBlockY()+"."+base.getBlockZ()+".locked",!state);
        yml.save();
        return !state;
    }
}
