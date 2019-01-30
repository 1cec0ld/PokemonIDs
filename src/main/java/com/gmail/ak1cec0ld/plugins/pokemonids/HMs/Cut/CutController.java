package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Cut;

import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class CutController {
    private PokemonIDs plugin;
    
    public CutController(PokemonIDs pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new CutInteractListener(this), pl);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }
    
    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
