package com.gmail.ak1cec0ld.plugins.pokemonids.toggleeffects;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.gmail.ak1cec0ld.plugins.pokemonids.CustomYMLStorage;
import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;

public class EffectFile {
    private static CustomYMLStorage yml;
    private static YamlConfiguration storage;
    
    EffectFile(){
        yml = new CustomYMLStorage(PokemonIDs.instance(),"PokemonIDs"+File.separator+"Effects.yml");
        storage = yml.getYamlConfiguration();
    }
    
    static Set<String> getEffects(){
        return storage.getKeys(false);
    }
    static Particle getParticle(String key){
        try{
            return Particle.valueOf(storage.getString(key + ".particle").toUpperCase());
        }catch(Exception e){
            return null;
        }
    }
    static Long timesPerSecondAsTicks(String key){
        return storage.getLong(key + ".interval", 20L);
    }
    static double[] getOffset(String key){
        double x = storage.getDouble(key + ".offset.x", 0.0);
        double y = storage.getDouble(key + ".offset.y", 0.0);
        double z = storage.getDouble(key + ".offset.z", 0.0);
        double[] arr = {x,y,z};
        return arr;
    }
    static int getCount(String key){
        return storage.getInt(key+".count",1);
    }
    static int getExtra(String key){
        return storage.getInt(key+".extra",0);
    }
}
