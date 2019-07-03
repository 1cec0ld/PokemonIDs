package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.BuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Teleport implements Listener {

    public Teleport(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
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
