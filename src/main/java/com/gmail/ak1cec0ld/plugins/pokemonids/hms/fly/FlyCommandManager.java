package com.gmail.ak1cec0ld.plugins.pokemonids.hms.fly;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashSet;

public class FlyCommandManager implements CommandExecutor{
    FlyController manager;
    
    public FlyCommandManager(FlyController manager){
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("fly")){
            if(args.length == 0){
                sender.sendMessage(ChatColor.COLOR_CHAR+"bFly Plugin, subsection of PokemonIDs, version 1.0");
                sender.sendMessage(ChatColor.COLOR_CHAR+"aVisit cities and pokemon Centers!");
                sender.sendMessage(ChatColor.COLOR_CHAR+"aIf you're still in that pokemon region, you can fly back later!");
                sender.sendMessage(ChatColor.COLOR_CHAR+"6Just make sure there's nothing above your head, or you might smack it!");
                sender.sendMessage(ChatColor.COLOR_CHAR+"6  /fly {location}");
            } else if(sender instanceof Player && args.length == 1){
                Player player = (Player)sender;
                if(exposedToSky(player)){
                    if(manager.playerHasFlyPoint(player, args[0])){
                        if(playerInFlypointRegion(player,args[0]) || playerHasAllFlyPoints(player)){
                            fly(player,args[0]);
                        } else {
                            player.sendMessage(ChatColor.COLOR_CHAR+"cYou don't appear to be in "+manager.getStorageManager().getParentOfFlyPoint(args[0]));
                        }
                    }else if(manager.getStorageManager().getRegions().contains(manager.getParentRegion(player.getLocation()))){
                        player.sendMessage(ChatColor.COLOR_CHAR+"cYou haven't been to " + args[0]);
                        manager.getPlugin().getServer().getScheduler().runTaskLater(manager.getPlugin(),(Runnable) () -> messageAllFlyPoints(player),20L );

                    } else {
                        sender.sendMessage(ChatColor.COLOR_CHAR+"cYou haven't been to "+args[0]+" yet! Visit there first!");
                    }
                } else {
                    Location loc = player.getLocation();
                    player.setVelocity(new Vector(0,200,0));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(manager.getPlugin(), () -> player.teleport(loc),10L);
                }
            }
            return true;
        }
        return false;
    }

    private boolean playerHasAllFlyPoints(Player player) {
        for(String regionname : manager.getStorageManager().getRegions()){
            for(String cityname : manager.getStorageManager().getPoints(regionname)){
                if(!manager.playerHasFlyPoint(player, cityname)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean playerInFlypointRegion(Player player, String flypoint) {
        String regionName = manager.getParentRegion(player.getLocation());
        return manager.getStorageManager().regionContainsPoint(regionName, flypoint);
    }

    private void fly(Player player, String flypoint){
        player.teleport(player);
        manager.getPlugin().getServer().dispatchCommand(manager.getPlugin().getServer().getConsoleSender(), "warp "+flypoint.toLowerCase()+" "+player.getName());
    }

    private boolean exposedToSky(Player player) {
        HashSet<Material> goThroughThese = new HashSet<Material>(Arrays.asList(
                Material.AIR,
                Material.TORCH,
                Material.WALL_TORCH,
                Material.REDSTONE_TORCH,
                Material.REDSTONE_WALL_TORCH,
                Material.TRIPWIRE,
                Material.TRIPWIRE_HOOK,
                Material.LEVER,
                Material.STONE_BUTTON,
                Material.OAK_BUTTON,
                Material.ACACIA_BUTTON,
                Material.BIRCH_BUTTON,
                Material.DARK_OAK_BUTTON,
                Material.JUNGLE_BUTTON,
                Material.SPRUCE_BUTTON,
                Material.OAK_WALL_SIGN,
                Material.ACACIA_WALL_SIGN,
                Material.BIRCH_WALL_SIGN,
                Material.DARK_OAK_WALL_SIGN,
                Material.JUNGLE_WALL_SIGN,
                Material.SPRUCE_WALL_SIGN,
                Material.OAK_SIGN,
                Material.ACACIA_SIGN,
                Material.BIRCH_SIGN,
                Material.DARK_OAK_SIGN,
                Material.JUNGLE_SIGN,
                Material.SPRUCE_SIGN,
                Material.OAK_FENCE,
                Material.ACACIA_FENCE,
                Material.BIRCH_FENCE,
                Material.DARK_OAK_FENCE,
                Material.JUNGLE_FENCE,
                Material.SPRUCE_FENCE,
                Material.OAK_FENCE_GATE,
                Material.ACACIA_FENCE_GATE,
                Material.BIRCH_FENCE_GATE,
                Material.DARK_OAK_FENCE_GATE,
                Material.JUNGLE_FENCE_GATE,
                Material.SPRUCE_FENCE_GATE,
                Material.IRON_BARS,
                Material.FIRE,
                Material.VINE,
                Material.LADDER));
        int i = player.getEyeLocation().getBlockX();
        int k = player.getEyeLocation().getBlockZ();
        for(int j = player.getEyeLocation().getBlockY(); j < player.getWorld().getMaxHeight(); j++){
            if(!goThroughThese.contains(player.getWorld().getBlockAt(i, j, k).getType())){
                return false;
            }
        }
        return true;
    }

    private void messageAllFlyPoints(Player target){
        for(String city : manager.getStorageManager().getPoints(manager.getParentRegion(target.getLocation()))){

            String first = "{\"text\":\""+ChatColor.COLOR_CHAR+"6- "+(manager.playerHasFlyPoint(target, city)?ChatColor.COLOR_CHAR+"a":ChatColor.COLOR_CHAR+"c")+city+"\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":";
            String hoverEvent = (manager.playerHasFlyPoint(target, city) ? "\""+ChatColor.COLOR_CHAR+"aClick to fly to "+city+"\"}":"\""+ChatColor.COLOR_CHAR+"cVisit "+city+" to enable flying here\"}");
            String clickEvent = (manager.playerHasFlyPoint(target, city) ? ",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/fly "+city+"\"}}":"}");

            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + target.getName() + " " + first + hoverEvent + clickEvent );

        }
    }
}
