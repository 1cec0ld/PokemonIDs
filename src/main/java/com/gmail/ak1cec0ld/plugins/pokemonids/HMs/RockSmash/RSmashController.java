package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.RockSmash;

import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class RSmashController {
    private PokemonIDs plugin;
    
    public RSmashController(PokemonIDs pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new RSmashInteractListener(this), pl);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
