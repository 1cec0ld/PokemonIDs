package com.gmail.ak1cec0ld.plugins.pokemonids.Teleports;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class TeleportsController {
    private PokemonIDs plugin;
    public TeleportsController(PokemonIDs plugin){
        this.plugin = plugin;
        plugin.getCommand("wild").setExecutor(new WildsCommandManager());
        plugin.getCommand("spawn").setExecutor(new SpawnCommandManager(this));
        plugin.getCommand("pvp").setExecutor(new PvPCommandManager(this));
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
}
