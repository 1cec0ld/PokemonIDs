package com.gmail.ak1cec0ld.plugins.pokemonserver.autohouse;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class AutoHouseTaskManager {
    private static final String METADATA_KEY = "autohouse_mode";

    static void addPlayerMode(Player player, int mode, Long delay){
        player.setMetadata(METADATA_KEY, new FixedMetadataValue(PokemonServer.instance(), mode));
        PokemonServer.instance().getServer().getScheduler().runTaskLater(PokemonServer.instance(), () -> {
            if(getMode(player) >= 0){
                removePlayerMode(player,getMode(player));
            }
        }, delay);
    }
    
    static int getMode(Player player){
        if (player.hasMetadata(METADATA_KEY)){
            return player.getMetadata(METADATA_KEY).get(0).asInt();
        }
        return -1;
    }

    static void removePlayerMode(Player player, int mode){
        if (player.hasMetadata(METADATA_KEY)){
            player.removeMetadata(METADATA_KEY, PokemonServer.instance());
            switch(mode){
                case 0:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cBuy Mode ended."));
                    break;
                case 1:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cSell Mode ended."));
                    break;
                case 2:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cCo-owner Mode ended."));
                    break;
                case 3:
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cEviction Mode ended."));
                    break;
            }
        }
    }
}
