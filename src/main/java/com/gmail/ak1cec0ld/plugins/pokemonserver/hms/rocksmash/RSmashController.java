package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.rocksmash;

import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;

public class RSmashController {
    private PokemonServer plugin;
    
    public RSmashController(PokemonServer pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new RSmashInteractListener(this), pl);
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }
    
    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
