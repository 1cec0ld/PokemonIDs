package com.gmail.ak1cec0ld.plugins.pokemonids.Choice.Region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.ak1cec0ld.plugins.pokemonids.Choice.ChoiceController;
import com.gmail.ak1cec0ld.plugins.PokemonIDs.Teleports.SpawnCommandManager;

public class RegionChoiceInteractListener implements Listener{
    ChoiceController controller;

    public RegionChoiceInteractListener(ChoiceController controller){
        this.controller = controller;
    }
    

        
    @EventHandler
    public void onPlayerInteractWithMap(PlayerInteractEntityEvent event){
        if (inRegion(event.getPlayer().getLocation(),"tutorial")){
            if (event.getRightClicked()!=null && event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)){
                Player player = event.getPlayer();
                String uuid = player.getUniqueId().toString();
                if(controller.getPlugin().getPlayerStorageManager().getRegionChoice(uuid) < 0){
                    if(inRegion(event.getRightClicked().getLocation(),"kantochoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 1");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 0);
                        controller.getTaskManager().constrainPallet(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"johtochoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 2");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 1);
                        controller.getTaskManager().constrainNewbark(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"hoennchoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 3");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 2);
                        controller.getTaskManager().constrainLittleroot(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"sinnohchoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 4");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 3);
                        controller.getTaskManager().constrainSandgem(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"unovachoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 5");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 4);
                        controller.getTaskManager().constrainNuvema(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"kaloschoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 6");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 5);
                        controller.getTaskManager().constrainPallet(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"alolachoice")){
                        Bukkit.getLogger().info("Cancelled PIEE: 7");
                        event.setCancelled(true);
                        controller.getPlugin().getPlayerStorageManager().setRegionChoice(uuid, 6);
                        controller.getTaskManager().constrainPallet(player);
                    }
                } else {
                    SpawnCommandManager.spawn(event.getPlayer());
                }
            }
        }
    }



    private boolean inRegion(Location location, String string) {
        return(controller.getPlugin().inRegionChild(location, string));
    }
}