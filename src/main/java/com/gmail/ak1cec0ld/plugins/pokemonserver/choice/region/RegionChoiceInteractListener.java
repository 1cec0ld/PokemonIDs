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
        if(event.getPlayer().isSneaking())return;
        if (!inRegion(event.getPlayer().getLocation(),"tutorial"))return;
        if (!event.getRightClicked().getType().equals(EntityType.ITEM_FRAME))return;
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        event.setCancelled(true);
        if(PokemonServer.getPlayerStorageManager().getRegionChoice(uuid) < 0){
            if(inRegion(event.getRightClicked().getLocation(),"kantochoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 0);
                controller.getTaskManager().constrainPallet(player);
            } else if(inRegion(event.getRightClicked().getLocation(),"johtochoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 1);
                controller.getTaskManager().constrainNewbark(player);
            } else if(inRegion(event.getRightClicked().getLocation(),"hoennchoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 2);
                controller.getTaskManager().constrainLittleroot(player);
            } else if(inRegion(event.getRightClicked().getLocation(),"sinnohchoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 3);
                controller.getTaskManager().constrainSandgem(player);
            }/* else if(inRegion(event.getRightClicked().getLocation(),"unovachoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 4);
                controller.getTaskManager().constrainNuvema(player);
            } else if(inRegion(event.getRightClicked().getLocation(),"kaloschoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 5);
                controller.getTaskManager().constrainPallet(player);
            } else if(inRegion(event.getRightClicked().getLocation(),"alolachoice")){
                PokemonServer.getPlayerStorageManager().setRegionChoice(uuid, 6);
                controller.getTaskManager().constrainPallet(player);
            }*/
        } else {
            SpawnCommandManager.spawn(event.getPlayer());
        }
    }



    private boolean inRegion(Location location, String string) {
        return(PokemonServer.inRegionChild(location, string));
    }
}