package com.gmail.ak1cec0ld.plugins.pokemonserver.teleports;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;

public class TeleportsController {
    private PokemonServer plugin;
    public TeleportsController(PokemonServer plugin){
        this.plugin = plugin;
        plugin.getCommand("wild").setExecutor(new WildsCommandManager());
        plugin.getCommand("spawn").setExecutor(new SpawnCommandManager(this));
        plugin.getCommand("pvp").setExecutor(new PvPCommandManager(this));
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }
}
