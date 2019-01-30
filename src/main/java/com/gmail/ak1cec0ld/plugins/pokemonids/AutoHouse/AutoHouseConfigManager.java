package com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.configuration.file.YamlConfiguration;

import com.gmail.ak1cec0ld.plugins.PokemonIDs.CustomYMLStorage;

public class AutoHouseConfigManager {
    private CustomYMLStorage yml;
    private static YamlConfiguration storage;
    
    private static int x1 = 0;
    private static int y1 = 0;
    private static int x2 = 24;
    private static int y2 = x2*75;
    private static int x3 = 43;
    private static int y3 = x3*150;
    
    public AutoHouseConfigManager(AutoHouseController controller){
        yml = new CustomYMLStorage(controller.getPlugin(), "PokemonIDs" +File.separator+"HouseConfig.yml");
        storage = yml.getYamlConfiguration();
    }
    
    public static ArrayList<Integer> getCoords(){
        return new ArrayList<Integer>(Arrays.asList(storage.getInt("x1",x1),storage.getInt("y1",y1),
                                                    storage.getInt("x2",x2),storage.getInt("y2",y2),
                                                    storage.getInt("x3",x3),storage.getInt("y3",y3)));
    }
}