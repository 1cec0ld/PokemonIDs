package com.gmail.ak1cec0ld.plugins.pokemonserver.choice.region;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.ChoiceController;
import com.gmail.ak1cec0ld.plugins.pokemonserver.teleports.SpawnCommandManager;

public class RegionChoiceInteractListener implements Listener{
    ChoiceController controller;

    public RegionChoiceInteractListener(ChoiceController controller){
        this.controller = controller;
    }
    

        
    @EventHandler
    public void onPlayerInteractWithMap(PlayerInteractEntityEvent event){
        if (inRegion(event.getPlayer().getLocation(),"tutorial")){
            if (event.getRightClicked().getType().equals(EntityType.ITEM_FRAME)){
                Player player = event.getPlayer();
                String uuid = player.getUniqueId().toString();
                if(PokemonServer.getPlayerStorageManager().getRegionChoice(uuid) < 0){
                    if(inRegion(event.getRightClicked().getLocation(),"kantochoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 0);
                        controller.getTaskManager().constrainPallet(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"johtochoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 1);
                        controller.getTaskManager().constrainNewbark(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"hoennchoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 2);
                        controller.getTaskManager().constrainLittleroot(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"sinnohchoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 3);
                        controller.getTaskManager().constrainSandgem(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"unovachoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 4);
                        controller.getTaskManager().constrainNuvema(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"kaloschoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 5);
                        controller.getTaskManager().constrainPallet(player);
                    } else if(inRegion(event.getRightClicked().getLocation(),"alolachoice")){
                        event.setCancelled(true);
                        PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 6);
                        controller.getTaskManager().constrainPallet(player);
                    }
                } else {
                    SpawnCommandManager.spawn(event.getPlayer());
                }
            }
        }
    }



    private boolean inRegion(Location location, String string) {
        return(PokemonServer.inRegionChild(location, string));
    }
}