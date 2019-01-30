package com.gmail.ak1cec0ld.plugins.pokemonids.Choice;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class ChoiceTaskManager {
    private ChoiceController controller;
    private HashMap<String,Integer> playerTasks;
    private Location tutorial = new Location(Bukkit.getWorld("Japan"),-566.5,31.0,907.0,180.0F,0.0F);
    private Location kanto = new Location(Bukkit.getWorld("Japan"),-579.5,67.1,504.5,180.0F,0.0F);
    private Location johto = new Location(Bukkit.getWorld("Japan"),-1281.5,67.1,566.5,180.0F,0.0F);
    private Location hoenn = new Location(Bukkit.getWorld("Japan"),-4094.5,69.1,1570.5,270.0F,0.0F);
    private Location sinnoh = new Location(Bukkit.getWorld("Japan"),733.5,67.1,-3170.5,180.0F,0.0F);
    private Location unova = new Location(Bukkit.getWorld("Japan"),-7209.5,69.1,759.5,180.0F,0.0F);
    
    public ChoiceTaskManager(ChoiceController controller){
        this.controller = controller;
        playerTasks = new HashMap<String,Integer>();
    }
    
    public void constrainOff(Player player){
        if(playerTasks.containsKey(player.getUniqueId().toString())){
            Bukkit.getScheduler().cancelTask(playerTasks.get(player.getUniqueId().toString()));
            playerTasks.remove(player.getUniqueId().toString());
        }
    }
    
    public void constrainPallet(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inPallet(player.getLocation())){
                    player.teleport(kanto);
                    player.sendMessage(ChatColor.RED+"Visit Oak's Lab before you leave!");
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Pok�mon!");
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    public void constrainNewbark(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inNewbark(player.getLocation())){
                    player.teleport(johto);
                    player.sendMessage(ChatColor.RED+"Visit Elm's Lab before you leave!");
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Pok�mon!");
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    public void constrainLittleroot(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inLittleroot(player.getLocation())){
                    player.teleport(hoenn);
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Pok�mon!");
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    public void constrainSandgem(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inSandgem(player.getLocation())){
                    player.teleport(sinnoh);
                    player.sendMessage(ChatColor.RED+"Visit Rowan's Lab before you leave!");
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Pok�mon!");
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    public void constrainNuvema(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inNuvema(player.getLocation())){
                    player.teleport(unova);
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Pok�mon!");
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    public void constrainTutorial(Player player){
        String uuid = player.getUniqueId().toString();
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                if(!inTutorial(player.getLocation())){
                    //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn "+player.getName()+" default");
                    player.sendMessage(ChatColor.RED+"Go to the back of the room to leave!");
                    player.sendMessage(ChatColor.GOLD+"You need to choose a Region!");
                    player.teleport(tutorial);
                }
            }
        };
        if(!playerTasks.containsKey(uuid)){
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 10L, 20L);
            playerTasks.put(uuid, taskID);
        } else {
            Bukkit.getScheduler().cancelTask(playerTasks.get(uuid));
            int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(controller.getPlugin(), runnable, 0L, 20L);
            playerTasks.put(uuid, taskID);
        }
    }
    
    private boolean inPallet(Location loc){
        return controller.getPlugin().inRegionChild(loc, "pallet");
    }
    private boolean inNewbark(Location loc){
        return controller.getPlugin().inRegionChild(loc, "newbark");
    }
    private boolean inLittleroot(Location loc){
        return controller.getPlugin().inRegionChild(loc, "littleroot");
    }
    private boolean inSandgem(Location loc){
        return controller.getPlugin().inRegionChild(loc, "sandgem");
    }
    private boolean inNuvema(Location loc){
        return controller.getPlugin().inRegionChild(loc, "nuvema");
    }
    private boolean inTutorial(Location loc){
        return controller.getPlugin().inRegionChild(loc, "tutorial");
    }
}
