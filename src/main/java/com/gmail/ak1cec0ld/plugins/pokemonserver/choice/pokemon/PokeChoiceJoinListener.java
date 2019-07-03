package com.gmail.ak1cec0ld.plugins.pokemonserver.choice.pokemon;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.ak1cec0ld.plugins.pokemonserver.choice.ChoiceController;

public class PokeChoiceJoinListener implements Listener{
    
    ChoiceController controller;
    
    public PokeChoiceJoinListener(ChoiceController controller){
        this.controller = controller;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        int pokeChoice = controller.getStorageManager().getPokemonChoice(player.getUniqueId().toString());
        if(pokeChoice == -1){
            int regionChoice = controller.getStorageManager().getRegionChoice(player.getUniqueId().toString());
            constrainPlayer(player, regionChoice);
        }
    }
    
    public void constrainPlayer(Player player, int regionChoice){
        switch(regionChoice){
        case 0:
            controller.getTaskManager().constrainPallet(player);
            break;
        case 1:
            controller.getTaskManager().constrainNewbark(player);
            break;
        case 2:
            controller.getTaskManager().constrainLittleroot(player);
            break;
        case 3:
            controller.getTaskManager().constrainSandgem(player);
            break;
        case 4:
            controller.getTaskManager().constrainNuvema(player);
            break;
        default:
            break;
        }
    }
}
