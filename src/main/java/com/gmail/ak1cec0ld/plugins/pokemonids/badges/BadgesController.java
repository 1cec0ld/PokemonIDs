package com.gmail.ak1cec0ld.plugins.pokemonids.badges;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class BadgesController {
    private PokemonIDs plugin;
    private BadgesStorageManager badgeStorageManager;
    
    public BadgesController(PokemonIDs plugin){
        this.plugin = plugin;
        
        plugin.getServer().getPluginCommand("badges").setExecutor(new BadgesCommandManager(this));
        badgeStorageManager = new BadgesStorageManager(this);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    public BadgesStorageManager getStorageManager(){
        return this.badgeStorageManager;
    }
}
