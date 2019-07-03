package com.gmail.ak1cec0ld.plugins.pokemonserver.badges;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;

public class BadgesController {
    private PokemonServer plugin;
    private BadgesStorageManager badgeStorageManager;
    
    public BadgesController(PokemonServer plugin){
        this.plugin = plugin;
        
        plugin.getServer().getPluginCommand("badges").setExecutor(new BadgesCommandManager(this));
        badgeStorageManager = new BadgesStorageManager(this);
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }
    
    public BadgesStorageManager getStorageManager(){
        return this.badgeStorageManager;
    }
}
