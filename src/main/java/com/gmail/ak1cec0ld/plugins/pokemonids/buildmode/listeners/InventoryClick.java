package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick implements Listener {

    public InventoryClick(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if(!(event.getWhoClicked() instanceof Player))return;
        if(event.getWhoClicked().isOp())return;
        if(BuildMode.isNotBuilding((Player) event.getWhoClicked()))return;
        if(!event.getView().getType().equals(InventoryType.CREATIVE)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage(ChatColor.COLOR_CHAR + "cNo moving items outside of your own Inventory.");
        }
    }

}
