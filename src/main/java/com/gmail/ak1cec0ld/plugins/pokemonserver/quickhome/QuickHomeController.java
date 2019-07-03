package com.gmail.ak1cec0ld.plugins.pokemonserver.quickhome;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;

public class QuickHomeController {
    private PokemonServer plugin;
    
    public QuickHomeController(PokemonServer plugin){
        plugin.getCommand("houses").setExecutor(new QuickHomeCommand(this));
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }
}
