package com.gmail.ak1cec0ld.plugins.pokemonids.AutoHouse;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Directional;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;

public class AutoHouseInteractListener implements Listener{
    private AutoHouseController controller;
    private enum OwnerType {SOLO_OWNER, PRIMARY_OWNER, COOWNER};
    
    public AutoHouseInteractListener(AutoHouseController controller){
        this.controller = controller;
    }
    
    public void log(String message){
        controller.getPlugin().getLogger().log(Level.INFO, message);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getHand()==null || !event.getHand().equals(EquipmentSlot.HAND) || event.getAction()==null || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            return;
        }
        Player player = event.getPlayer();
        Block b = event.getClickedBlock();
        BlockState bs = b.getState();
        if (bs == null){
            return;
        }
        if(!bs.getType().equals(Material.WALL_SIGN)){
            return;
        }
        Sign interact_block = (Sign) bs;
        String interact_block_line_1 = interact_block.getLine(0);
        String interact_block_line_2 = interact_block.getLine(1);
        String interact_block_line_3 = interact_block.getLine(2);
        String interact_block_line_4 = interact_block.getLine(3);
        String housename = interact_block_line_3+interact_block_line_4;
        
        if (interact_block_line_1.equals("§a[For Sale]")){
            int line_2 = 1000000;
            try{
                line_2 = Integer.parseInt(interact_block_line_2);
            } catch(NumberFormatException e){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError! Contact a moderator! This house is broken!"));
                return;
            }
            if(line_2 != controller.getPrice(AutoHouseStorageManager.getHouseSize(housename))){
                if(line_2<=150){
                    controller.getStorageManager().createHouse(housename, line_2);
                }
                interact_block.setLine(1, Integer.toString(controller.getPrice(AutoHouseStorageManager.getHouseSize(housename))));
                interact_block.update();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis house was using an outdated pricing system! I'm sorry! Please try to buy again if you still want it!"));
            } else {
                attemptHousePurchase(interact_block,player,line_2);
            }
        } else if (interact_block_line_1.equals("§1OWNED BY:")){
            if(player.isSneaking() && (interact_block_line_2.equals(player.getName()) || player.hasPermission("worldguard.region"))){
                activateHouseRemoval(interact_block, player);
            } else if(interact_block_line_2.equals(player.getName())){
                activateCoownerAddition(interact_block,player);
            } else {
                getSeenTime(interact_block,player);
            }
        }
    }
    
    private void getSeenTime(Sign interact_block, Player player) {
        player.performCommand("seen "+interact_block.getLine(1));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Make sure they don't have an AWAY post in our forum, and aren't a GameFreak if their offline time is over 2 weeks!"));
    }

    private void activateCoownerAddition(Sign interact_block, Player player) {
        if (getOwnerType(interact_block) == OwnerType.SOLO_OWNER){
            if(controller.getTaskManager().getMode(player.getUniqueId().toString())==2){
                controller.getTaskManager().removePlayerMode(player.getUniqueId().toString());
                runCoownerAddition(interact_block);
            } else {
                controller.getTaskManager().addPlayerMode(player, 2, 200L);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've chosen to &badd a co-owner&a to your house!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRight-click &aagain to confirm this choice, or crouch-rightclick to change to &bSell Mode&a!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThis option will time out in 10 seconds..."));

            }
        } else {
            controller.getTaskManager().removePlayerMode(player.getUniqueId().toString());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou appear to already have a co-owner! Only 2 people per house please!"));
        }
    }

    private void runCoownerAddition(Sign interact_block) {
        Block nextSign = getHouseSignPositions(interact_block)[1].getBlock();
        nextSign.setType(Material.WALL_SIGN);
        nextSign.setBlockData(interact_block.getBlockData());
        Sign s = (Sign) nextSign.getState();
        s.setLine(0, "�a[For Sale]");
        s.setLine(1, Integer.toString(controller.getPrice(AutoHouseStorageManager.getHouseSize(interact_block.getLine(2)+interact_block.getLine(3)))));
        s.setLine(2, interact_block.getLine(3));
        s.setLine(3, interact_block.getLine(4));
        s.update();
    }

    private void activateHouseRemoval(Sign interact_block, Player player) {
        if(controller.getTaskManager().getMode(player.getUniqueId().toString())==1){
            controller.getTaskManager().removePlayerMode(player.getUniqueId().toString());
            if(interact_block.getLine(1).equalsIgnoreCase(player.getName())){
                controller.getPlugin().getEconomy().depositPlayer(player, controller.getPrice(AutoHouseStorageManager.getHouseSize(interact_block.getLine(2)+interact_block.getLine(3)))/3);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aHouse Sold! Check your balance!"));
            }
            runHouseEviction(interact_block);
        } else {
            controller.getTaskManager().addPlayerMode(player, 1, 200L);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've chosen to remove " + interact_block.getLine(1) + " from this house!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRight-click &aagain to confirm this choice, or rightclick without crouching to change to &bThe Other Mode&a!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThis option will time out in 10 seconds..."));
        }
    }

    private void runHouseEviction(Sign interact_block) {
        //do the paperwork to record that the person is homeless
        String housename = interact_block.getLine(2)+interact_block.getLine(3);
        String ownername = interact_block.getLine(1);
        controller.getPlugin().getPlayerStorageManager().removeHouse(controller.getPlugin().getOfflinePlayerFromString(ownername).getUniqueId().toString());
        controller.getStorageManager().removeHouseOwner(housename, controller.getPlugin().getOfflinePlayerFromString(ownername).getUniqueId().toString());
        controller.getPlugin().getServer().dispatchCommand(controller.getPlugin().getServer().getConsoleSender(), "rg removeowner -w "+interact_block.getWorld().getName()+" "+housename+" "+ownername);
        //figure out how to handle the house:
        //empty it or give it to a coowner, or give it to original owner
        //first find out if the sign has a sign right beside it (marking a coowner)
        OwnerType owner = getOwnerType(interact_block);
        Bukkit.getLogger().info(owner.toString());
        if (owner == OwnerType.SOLO_OWNER){
            interact_block.setLine(0, "�a[For Sale]");
            interact_block.setLine(1, Integer.toString(controller.getPrice(AutoHouseStorageManager.getHouseSize(housename))));
            interact_block.update(true);
        } else if(owner == OwnerType.PRIMARY_OWNER){
            Location secondarySign = getHouseSignPositions(interact_block)[1];
            interact_block.setLine(0, ((Sign)interact_block.getWorld().getBlockAt(secondarySign).getState()).getLine(0));
            interact_block.setLine(1, ((Sign)interact_block.getWorld().getBlockAt(secondarySign).getState()).getLine(1));
            interact_block.setLine(2, ((Sign)interact_block.getWorld().getBlockAt(secondarySign).getState()).getLine(2));
            interact_block.setLine(3, ((Sign)interact_block.getWorld().getBlockAt(secondarySign).getState()).getLine(3));
            interact_block.update();
            interact_block.getWorld().getBlockAt(secondarySign).setType(Material.AIR);
        } else { //they are the coowner
            interact_block.setType(Material.AIR);
        }
    }
    
    private OwnerType getOwnerType(Sign interact_block){
        Location primarySign = getHouseSignPositions(interact_block)[0];
        Location secondarySign = getHouseSignPositions(interact_block)[1];
        if(interact_block.getLocation().equals(primarySign)){
            if(secondarySign.getBlock().getType().equals(Material.WALL_SIGN)){
                return OwnerType.PRIMARY_OWNER;
            }
            return OwnerType.SOLO_OWNER;
        } 
        return OwnerType.COOWNER;
    }
    
    private Location[] getHouseSignPositions(Sign interact_block){
        BlockFace direction = ((Directional)interact_block.getData()).getFacing();
        if(direction.equals(BlockFace.NORTH)){ //northfacing, coowner sign is x-1
            if(interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getType().equals(Material.WALL_SIGN)){
                Location[] offsets = {interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getLocation(),interact_block.getLocation()};
                return offsets;
            } else {
                Location[] offsets = {interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getLocation()};
                return offsets;
            }
        } else if(direction.equals(BlockFace.SOUTH)){ //southfacing, coowner sign is x+1
            if(interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getType().equals(Material.WALL_SIGN)){
                Location[] offsets = {interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getLocation(),interact_block.getLocation()};
                return offsets;
            } else {
                Location[] offsets = {interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getLocation()};
                return offsets;
            }
        } else if(direction.equals(BlockFace.WEST)){ //westfacing, coowner sign is z+1
            if(interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getType().equals(Material.WALL_SIGN)){
                Location[] offsets = {interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getLocation(),interact_block.getLocation()};
                return offsets;
            } else {
                Location[] offsets = {interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getLocation()};
                return offsets;
            }
        } else if(direction.equals(BlockFace.EAST)){ //eastfacing, coowner sign is z-1
            if(interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getType().equals(Material.WALL_SIGN)){
                Location[] offsets = {interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getLocation(),interact_block.getLocation()};
                return offsets;
            } else {
                Location[] offsets = {interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getLocation()};
                return offsets;
            }
        }
        return null;
    }
    private void attemptHousePurchase(Sign interact_block,Player player,int cost){
        PokemonIDs plugin = controller.getPlugin();
        String uuid = player.getUniqueId().toString();
        String housename = interact_block.getLine(2)+""+interact_block.getLine(3);
        if(plugin.getPlayerStorageManager().getHouse(uuid).length() > 0){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou already own a house, it is "+plugin.getPlayerStorageManager().getHouse(uuid)));
        } else {
            if(plugin.getEconomy().getBalance(player)>=cost){
                plugin.getEconomy().withdrawPlayer(player, cost);
                controller.getStorageManager().addHouseOwner(housename, uuid);
                plugin.getPlayerStorageManager().setHouse(housename,uuid);
                interact_block.setLine(0, "�1OWNED BY:");
                interact_block.setLine(1, player.getName());
                interact_block.update();
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "rg addowner -w "+player.getWorld().getName()+" "+housename+" "+player.getName());
                player.sendMessage("�eHouse bought! New balance: �a"+plugin.getEconomy().getBalance(player));
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNot enough money! You have &4$"+plugin.getEconomy().getBalance(player)+"/&2$"+cost+"!"));
            }
        }
    }
}
