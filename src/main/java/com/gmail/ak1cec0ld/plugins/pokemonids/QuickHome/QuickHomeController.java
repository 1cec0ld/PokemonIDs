package com.gmail.ak1cec0ld.plugins.pokemonids.QuickHome;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class QuickHomeController {
    private PokemonIDs plugin;
    
    public QuickHomeController(PokemonIDs plugin){
        plugin.getCommand("houses").setExecutor(new QuickHomeCommand(this));
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
}
