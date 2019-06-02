package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.listeners;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import com.gmail.ak1cec0ld.plugins.pokemonids.buildmode.BuildMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

public class PotionEffectChange implements Listener {

    public PotionEffectChange(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this, PokemonIDs.instance());
    }

    @EventHandler
    public void onPotionAdd(EntityPotionEffectEvent event){
        if(!event.getCause().equals(EntityPotionEffectEvent.Cause.POTION_DRINK) &&
                !event.getCause().equals(EntityPotionEffectEvent.Cause.POTION_SPLASH))return;
        if(!event.getAction().equals(EntityPotionEffectEvent.Action.ADDED))return;
        if(!(event.getEntity() instanceof Player))return;
        Player player = (Player)event.getEntity();
        if(player.isOp())return;
        if(BuildMode.isNotBuilding(player))return;
        event.setCancelled(true);
    }

}
