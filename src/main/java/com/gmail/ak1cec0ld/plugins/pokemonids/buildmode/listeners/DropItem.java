package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropItem implements Listener {

    public DropItem(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        if(event.getPlayer().isOp())return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.COLOR_CHAR+ "cChange to non-building if you want to be able to drop items.");
    }
}
