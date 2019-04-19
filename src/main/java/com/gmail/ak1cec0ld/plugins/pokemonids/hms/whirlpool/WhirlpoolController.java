package com.gmail.ak1cec0ld.plugins.pokemonids.hms.whirlpool;

import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class WhirlpoolController {
    private PokemonIDs plugin;
    
    public WhirlpoolController(PokemonIDs pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new WhirlpoolInteractListener(this), pl);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }

    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
