package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractEntity implements Listener {

    public InteractEntity(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onInteractWithSomething(PlayerInteractEntityEvent event){
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        event.setCancelled(true);
    }

}
