package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.dive;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.entity.Player;

public class DiveController {
    private PokemonServer plugin;
    
    public DiveController(PokemonServer pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new DiveInteractListener(this), pl);
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }

    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
