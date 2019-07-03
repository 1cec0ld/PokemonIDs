package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.cut;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.entity.Player;

public class CutController {
    private PokemonServer plugin;
    
    public CutController(PokemonServer pl){
        this.plugin = pl;
        plugin.getServer().getPluginManager().registerEvents(new CutInteractListener(this), pl);
    }
    
    public PokemonServer getPlugin(){
        return this.plugin;
    }
    
    public boolean permissionToBreak(Player player){
        return (plugin.getPlayerStorageManager().getPokemonChoice(player.getUniqueId().toString()) != -1);
    }
}
