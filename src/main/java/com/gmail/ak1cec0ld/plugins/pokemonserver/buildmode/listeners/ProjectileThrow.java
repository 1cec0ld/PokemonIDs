package com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import com.gmail.ak1cec0ld.plugins.pokemonserver.buildmode.BuildMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileThrow implements Listener {

    public ProjectileThrow(){
        PokemonServer.instance().getServer().getPluginManager().registerEvents(this, PokemonServer.instance());
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        if(!(event.getEntity().getShooter() instanceof Player))return;
        Player player = (Player)event.getEntity().getShooter();
        if(player.isOp())return;
        if(BuildMode.isNotBuilding(player))return;
        event.setCancelled(true);
    }

}
