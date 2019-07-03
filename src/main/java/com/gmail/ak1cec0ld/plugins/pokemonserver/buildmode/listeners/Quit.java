package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.BuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Quit implements Listener {

    public Quit(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        BuildMode.executeBuildOff(event.getPlayer());
    }
}
