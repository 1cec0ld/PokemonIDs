package com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Region;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.ChoiceController;

public class RegionChoiceJoinListener implements Listener{
    
    ChoiceController controller;
    
    public RegionChoiceJoinListener(ChoiceController controller){
        this.controller = controller;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(controller.getStorageManager().containsPlayer(player.getUniqueId().toString())){
            return;
        }
        int totalEver = controller.getStorageManager().getTotalPlayers();
        for(Player each : controller.getPlugin().getServer().getOnlinePlayers()){
            if(!controller.getStorageManager().containsPlayer(each.getUniqueId().toString())){
                totalEver++;
            }
        }
        controller.getPlugin().getServer().broadcastMessage(ChatColor.GREEN+"Welcome "+ChatColor.YELLOW+player.getName()+ChatColor.GREEN+", player #"+ChatColor.LIGHT_PURPLE+totalEver+ChatColor.GREEN+" to the Pokemon Server!");
        int regionChoice = controller.getStorageManager().getRegionChoice(player.getUniqueId().toString());
        if(regionChoice == -1){
            controller.getTaskManager().constrainTutorial(player);
        }
    }
}
