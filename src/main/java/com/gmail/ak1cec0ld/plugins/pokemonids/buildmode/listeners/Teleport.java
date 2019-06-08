package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport implements Listener {

    public Teleport(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        if(!event.getTo().getWorld().equals(event.getFrom().getWorld())) {
            BuildMode.executeBuildOff(event.getPlayer());
        } else if(!BuildMode.inBuildZone(event.getTo()) && BuildMode.isBuildGym(event.getPlayer())){
            BuildMode.executeBuildOff(event.getPlayer());
        }
    }




}
