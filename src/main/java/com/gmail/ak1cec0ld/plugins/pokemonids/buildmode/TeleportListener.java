package com.gmail.ak1cec0ld.plugins.pokemonids.buildmode;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {

    TeleportListener(){
        PokemonIDs.instance().getServer().getPluginManager().registerEvents(this,PokemonIDs.instance());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.SURVIVAL))return;
        if(player.hasPermission("buildmode.teleport"))return;
        if(BuildMode.inKalos(event.getFrom()) && BuildMode.inKalos(event.getTo()))return;
        if(!(BuildMode.inKalos(event.getFrom()) || BuildMode.inKalos(event.getTo())))return;
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.RED+"[BuildMode] There was a problem letting you Teleport. Talk to 1ce for details.");
    }
}
