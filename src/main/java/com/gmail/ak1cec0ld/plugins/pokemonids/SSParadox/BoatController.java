package com.gmail.ak1cec0ld.plugins.pokemonids.SSParadox;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class BoatController {
    
    private PokemonIDs plugin;

    public static Location olivine   = new Location(Bukkit.getWorld("Japan"),-2072.5,68.1,343.5,0.0F,0.0F);
    public static Location slateport = new Location(Bukkit.getWorld("Japan"),-4203.5,36.1,1972.5,90.0F,0.0F);
    public static Location lilycove  = new Location(Bukkit.getWorld("Japan"),-3503.5,36.1,2881.5,90.0F,0.0F);
    public static Location canalave  = new Location(Bukkit.getWorld("Japan"),455.5,67.1,-3380.5,90.0F,0.0F);
    public static Location snowpoint = new Location(Bukkit.getWorld("Japan"),1081.5,67.1,-4389.5,180.0F,0.0F);
    public static Location vermilion = new Location(Bukkit.getWorld("Japan"),-12.5,70.1,473.5,0.0F,0.0F);
    public static Location ssparadox = new Location(Bukkit.getWorld("Japan"),-616.5,71.1,-1418.5,0.0F,0.0F);
    
    public BoatController(PokemonIDs plugin){
        this.plugin = plugin;
        new BoatTaskManager(this);
        plugin.getServer().getPluginManager().registerEvents(new BoatInteractListener(), plugin);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
}
