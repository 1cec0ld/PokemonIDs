package com.gmail.ak1cec0ld.plugins.pokemonids.teleports;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class PvPCommandManager implements CommandExecutor {

    private static Location pvpzone = new Location(Bukkit.getWorld("Wild2"),0.5,0.1,0.5,0.0F,0.0F);
    private Random r = new Random();
    private int WILD_OFFSET = 150000;
    
    PvPCommandManager(TeleportsController controller){
        controller.getPlugin().getCommand("pvp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if(sender instanceof Player){
                Player p = (Player)sender;
                p.teleport(getPvpLoc(pvpzone));
            } else {
                sender.sendMessage("The console can't pvp ya fool.");
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNope. Just type /pvp"));
        }
        return false;
    }
    
    private Location getPvpLoc(Location loc) {
        int attempts = 0;
        double x2 = WILD_OFFSET;
        double z2 = WILD_OFFSET;
        Location l;
        Location l2 = new Location(Bukkit.getWorld("Wild"), x2, 256, z2);
        while (attempts < 10) {
            attempts++;
            x2 = WILD_OFFSET+Math.floor(loc.getX()+r.nextInt(41)-20);
            z2 = WILD_OFFSET+Math.floor(loc.getZ()+r.nextInt(41)-20);
            l = new Location(Bukkit.getWorld("Wild2"), x2, 256, z2);
            l2 = l.getWorld().getHighestBlockAt(l).getLocation();
            if(isSafe(l2.getBlock().getRelative(BlockFace.DOWN).getType())){
                return l2;
            }
        } //we've tried 10 times, time to replace the block
        l2.getBlock().getRelative(BlockFace.DOWN).setType(Material.DIRT);
        return l2;
    }
    
    private boolean isSafe(Material type) {
        return !(type.equals(Material.AIR) || type.equals(Material.WATER) ||
                 type.toString().endsWith("SIGN") ||
                 type.equals(Material.TORCH) || type.equals(Material.WALL_TORCH) || 
                 type.equals(Material.REDSTONE_TORCH) || type.equals(Material.REDSTONE_WALL_TORCH) ||
                 type.equals(Material.LAVA) || type.equals(Material.END_PORTAL));
    }
}
