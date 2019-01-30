package com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AutoHouseTaskManager {
    private AutoHouseController controller;
    private HashMap<String,Integer> modes;
    
    public AutoHouseTaskManager(AutoHouseController controller){
        this.controller = controller;
        modes = new HashMap<String,Integer>();
    }
    
    public void addPlayerMode(Player player, int mode, Long delay){
        String uuid = player.getUniqueId().toString();
        modes.put(uuid, mode);
        controller.getPlugin().getServer().getScheduler().runTaskLater(controller.getPlugin(), new Runnable(){
            @Override
            public void run() {
                if(modes.containsKey(uuid)){
                    modes.remove(uuid);
                    if (mode == 1){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bSell Mode ended."));
                    }
                    if (mode == 2){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bCo-owner Mode ended."));
                    }
                    if (mode == 3){
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&bEviction Mode ended."));
                    }
                }
            }}, delay);
    }
    
    public int getMode(String uuid){
        if (modes.containsKey(uuid)){
            return modes.get(uuid);
        }
        return -1;
    }

    public void removePlayerMode(String uuid){
        if (modes.containsKey(uuid)){
            modes.remove(uuid);
        }
    }
}
