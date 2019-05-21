package com.gmail.ak1cec0ld.plugins.pokemonids.teleports;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommandManager implements CommandExecutor {
    private static TeleportsController controller;
    private static Location kanto = new Location(Bukkit.getWorld("Japan"),-586.0,67.0,495.0,0.0F,0.0F);
    private static Location johto = new Location(Bukkit.getWorld("Japan"),-1262.0,67.0,561.0,0.0F,0.0F);
    private static Location hoenn = new Location(Bukkit.getWorld("Japan"),-4077.0,69.0,1546.0,90.0F,0.0F);
    private static Location sinnoh = new Location(Bukkit.getWorld("Japan"),581.0,67.0,-3127.0,0.0F,0.0F);
    private static Location unova = new Location(Bukkit.getWorld("Japan"),-743.0,51.0,-504.0,90.0F,0.0F);
    private static Location kalos = new Location(Bukkit.getWorld("Japan"),-743.0,51.0,-504.0,90.0F,0.0F);
    private static Location alola = new Location(Bukkit.getWorld("Japan"),-743.0,51.0,-504.0,90.0F,0.0F);
    private static Location tutorial = new Location(Bukkit.getWorld("Japan"),-565.5,31.0,907.0,180.0F,0.0F);
    
    public SpawnCommandManager(TeleportsController pokessentialsController){
        SpawnCommandManager.controller = pokessentialsController;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            //get their regionchoice
            if(sender instanceof Player){
                Player player = (Player)sender;
                if(player.hasPermission("pokemonids.teleports.spawn.self")){
                    spawn(player);
                }
            } else {
                sender.sendMessage("Not applicable from console! Choose a target!");
            }
        } else if(args.length == 1 && (!(sender instanceof Player) || ((Player)sender).hasPermission("pokemonids.teleports.spawn.others"))){
        //if they are trying to spawn someone else, and (they are console, or they have permission to do it)
            Player p = controller.getPlugin().getPlayerFromString(args[0]);
            if(p == null){
                sender.sendMessage("No online player name found starting with " + args[0] + "!");
            } else {
                spawn(p);
            }
        }
        return false;
    }
    
    public static void spawn(Player player){
        int choice = controller.getPlugin().getPlayerStorageManager().getRegionChoice(player.getUniqueId().toString());
        Location destination = tutorial;
        switch(choice){
            case 0:
                destination =(kanto);
                break;
            case 1:
                destination =(johto);
                break;
            case 2:
                destination =(hoenn);
                break;
            case 3:
                destination =(sinnoh);
                break;
            case 4:
                destination =(unova);
                break;
            case 5:
                destination =(kalos);
                break;
            case 6:
                destination =(alola);
                break;
            default:
                destination =(tutorial);
                break;
        }
        destination.getWorld().getChunkAt(destination).load(false);
        player.teleport(destination);
    }
}
