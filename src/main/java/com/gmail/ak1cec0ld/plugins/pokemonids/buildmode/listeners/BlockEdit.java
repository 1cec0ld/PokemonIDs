package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockEdit implements Listener {

    public BlockEdit(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        if(BuildMode.isBuildGym(event.getPlayer()) && !BuildMode.inBuildZone(event.getPlayer().getLocation())) {
            event.setCancelled(true);
            BuildMode.executeBuildOff(event.getPlayer());
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        if(BuildMode.isBuildGym(event.getPlayer()) && !BuildMode.inBuildZone(event.getPlayer().getLocation())) {
            event.setCancelled(true);
            BuildMode.executeBuildOff(event.getPlayer());
        }
    }
}
