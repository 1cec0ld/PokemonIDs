package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Attack implements Listener {

    public Attack(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }


    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player))return;
        Player player = (Player)event.getDamager();
        if(player.isOp())return;
        if(BuildMode.isNotBuilding(player))return;
        event.setCancelled(true);
    }

}
