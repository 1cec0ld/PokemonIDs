package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.BuildMode;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChange implements Listener {

    public GameModeChange(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event){
        if(!event.getNewGameMode().equals(GameMode.SURVIVAL))return;
        if(event.getPlayer().isOp())return;
        if(BuildMode.isNotBuilding(event.getPlayer()))return;
        BuildMode.executeBuildOff(event.getPlayer());
    }
}
