package com.gmail.ak1cec0ld.plugins.pokemonids.Teleports;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static java.lang.Math.floor;

public class WildsCommandManager implements CommandExecutor {
    private Random r = new Random();
    
    private final double STARTING_WILD_RADIUS = 2500;
    private final double WILD_OFFSET = 150000;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            if(sender instanceof Player){
                Player p = (Player)sender;
                String uuid = p.getUniqueId().toString().replace("-", "");
                String uuid1 = uuid.substring(0, 16);
                String uuid2 = uuid.substring(16);
                //p.sendMessage("You are " + uuid1 + " and " + uuid2);
                BigDecimal uid1 = new BigDecimal(new BigInteger(uuid1,16));
                BigDecimal uid2 = new BigDecimal(new BigInteger(uuid2,16));
                BigDecimal max = new BigDecimal(new BigInteger("FFFFFFFFFFFFFFFF",16));
                double x = uid1.divide(max, 5, RoundingMode.HALF_UP).doubleValue();
                double z = (uid2.divide(max, 5, RoundingMode.HALF_UP).doubleValue()-.5)*4;
                
                Location dest = getWildLoc((x*STARTING_WILD_RADIUS)-(STARTING_WILD_RADIUS/2),(z*STARTING_WILD_RADIUS)-(STARTING_WILD_RADIUS/2));
                p.teleport(dest);
            } else {
                sender.sendMessage("The console can't go to the wild ya fool.");
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cJust type /wild"));
        }
        return false;
    }

    private Location getWildLoc(double x, double z) {
        int attempts = 0;
        double x2 = WILD_OFFSET;
        double z2 = WILD_OFFSET;
        Location l;
        Location l2 = new Location(Bukkit.getWorld("Wild"), x2, 256, z2);
        while (attempts < 10) {
            attempts++;
            x2 = WILD_OFFSET+floor(x+r.nextInt(11)-5);
            z2 = WILD_OFFSET+floor(z+r.nextInt(11)-5);
            l = new Location(Bukkit.getWorld("Wild"), x2, 256, z2);
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
                 type.equals(Material.SIGN) || type.equals(Material.WALL_SIGN) || 
                 type.equals(Material.TORCH) || type.equals(Material.WALL_TORCH) || 
                 type.equals(Material.REDSTONE_TORCH) || type.equals(Material.REDSTONE_WALL_TORCH) ||
                 type.equals(Material.LAVA) || type.equals(Material.END_PORTAL));
    }
}
