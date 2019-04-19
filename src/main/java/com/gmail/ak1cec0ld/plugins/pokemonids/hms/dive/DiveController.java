package com.gmail.ak1cec0ld.plugins.pokemonids.hms.dive;

import org.bukkit.entity.Player;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class DiveController {
    private PokemonIDs plugin;
    
    public DiveController(PokemonIDs pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new DiveInteractListener(this), pl);
    }
    
    public PokemonIDs getPlugin(){
        return this.plugin;
    }

    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
