package com.gmail.ak1cec0ld.plugins.pokemonserver.ssparadox;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BoatController {
    static final Location olivine   = new Location(Bukkit.getWorld("Japan"),-2072.5,68.1,343.5,0.0F,0.0F);
    static final Location slateport = new Location(Bukkit.getWorld("Japan"),-4203.5,36.1,1972.5,90.0F,0.0F);
    static final Location lilycove  = new Location(Bukkit.getWorld("Japan"),-3503.5,36.1,2881.5,90.0F,0.0F);
    static final Location canalave  = new Location(Bukkit.getWorld("Japan"),455.5,67.1,-3380.5,90.0F,0.0F);
    static final Location snowpoint = new Location(Bukkit.getWorld("Japan"),1081.5,67.1,-4389.5,180.0F,0.0F);
    static final Location vermilion = new Location(Bukkit.getWorld("Japan"),-12.5,70.1,473.5,0.0F,0.0F);
    static final Location ssparadox = new Location(Bukkit.getWorld("Japan"),-616.5,71.1,-1418.5,0.0F,0.0F);
    
    public BoatController(){
        new BoatTaskManager();
        PokemonServer.instance().getServer().getPluginManager().registerEvents(new SSTicketListener(), PokemonServer.instance());
    }
}
