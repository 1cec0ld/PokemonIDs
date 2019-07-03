package com.gmail.ak1cec0ld.plugins.pokemonserver.autohouse;

import com.gmail.ak1cec0ld.plugins.pokemonserver.CustomYMLStorage;
import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

class AutoHouseConfigManager {
    private static CustomYMLStorage yml;
    private static YamlConfiguration storage;
    

    
    AutoHouseConfigManager(){
        yml = new CustomYMLStorage(PokemonServer.instance(), "PokemonServer" +File.separator+"HouseConfig.yml");
        storage = yml.getYamlConfiguration();
    }
    
    static ArrayList<Integer> getParabolaCoords(){
        int x1 = 0;
        int y1 = 0;
        int x2 = 24;
        int y2 = x2*75;
        int x3 = 43;
        int y3 = x3*150;
        return new ArrayList<>(Arrays.asList(storage.getInt("x1",x1),storage.getInt("y1",y1),
                                                    storage.getInt("x2",x2),storage.getInt("y2",y2),
                                                    storage.getInt("x3",x3),storage.getInt("y3",y3)));
    }
    static double getBonusMultiplier(){
        return storage.getDouble("bonusMultiplier",0.01);
    }
    static void setBonus(){
        clean();
        storage.set("bonusEntries."+System.currentTimeMillis(),System.currentTimeMillis());
        yml.save();
    }
    static int getBonus(){
        clean();
        ConfigurationSection cfg = storage.getConfigurationSection("bonusEntries");
        if(cfg == null)return 0;
        return Math.min(50, cfg.getKeys(false).size());
    }
    private static void clean(){
        ConfigurationSection cfg = storage.getConfigurationSection("bonusEntries");
        if(cfg == null) return;
        long current = System.currentTimeMillis();
        for(String key : cfg.getKeys(false)){
            long entry = cfg.getLong(key);
            if(current-entry > 1000*60*60*24*28L){
                cfg.set(key,null);
            }
        }
        yml.save();
    }
}