package com.gmail.ak1cec0ld.plugins.pokemonids.autohouse;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
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

import java.text.NumberFormat;

public class AutoHouseInteractListener implements Listener{
    private enum OwnerType {SOLO_OWNER, PRIMARY_OWNER, COOWNER}
    private static final String FOR_SALE = ChatColor.COLOR_CHAR+"a[For Sale]";
    private static final String DONOR_SIGN = ChatColor.COLOR_CHAR+"a[AddDonorBonus]";
    private static final String OWNED_BY = ChatColor.COLOR_CHAR+"1OWNED BY:";
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getHand()==null || !event.getHand().equals(EquipmentSlot.HAND) || !event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        Player player = event.getPlayer();
        Block b = event.getClickedBlock();
        if(b == null)return;
        BlockState bs = b.getState();
        if(!bs.getType().toString().contains("_WALL_SIGN"))return;
        Sign interact_block = (Sign) bs;
        String interact_block_line_1 = interact_block.getLine(0);
        String interact_block_line_2 = interact_block.getLine(1);
        String interact_block_line_3 = interact_block.getLine(2);
        String interact_block_line_4 = interact_block.getLine(3);
        String housename = interact_block_line_3+interact_block_line_4;
        switch (interact_block_line_1) {
            case DONOR_SIGN:
                if (!player.isOp()) return;
                AutoHouseConfigManager.setBonus();
                break;
            case FOR_SALE:
                int line_2;
                try {
                    line_2 = Integer.parseInt(interact_block_line_2);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError! Contact a moderator! This house is broken!"));
                    return;
                }
                if (line_2 != AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(housename))) {
                    if (line_2 <= 500) {
                        AutoHouseStorageManager.createHouse(housename, line_2);
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lThis price needed updating!"));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bNew price: " + AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(housename))));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&ePlease try to buy again if you still want it!"));
                    interact_block.setLine(1, Integer.toString(AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(housename))));
                    interact_block.update();
                } else {
                    checkHousePurchase(interact_block, player, line_2);
                }
                break;
            case OWNED_BY:
                if (player.isSneaking()){
                    if(interact_block_line_2.equals(player.getName()) || player.hasPermission("worldguard.region")){
                        checkHouseRemoval(interact_block, player);
                    }
                } else if (interact_block_line_2.equals(player.getName())) {
                    activateCoownerAddition(interact_block, player);
                } else {
                    getSeenTime(interact_block, player);
                }
                break;
        }
    }
    private void checkHousePurchase(Sign interact_block,Player player, int cost){
        switch (AutoHouseController.canBuy(player,cost)){
            case 0:
                executeHousePurchase(interact_block,player,cost);
                AutoHouseTaskManager.removePlayerMode(player,0);
                break;
            case 1:
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNot enough money! You have &4$" + formatter.format(PokemonIDs.getEconomy().getBalance(player)) + "&f/&2$" + cost + "!"));
                break;
            case 2:
                String uuid = player.getUniqueId().toString();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou already own a house, it is " + PokemonIDs.getPlayerStorageManager().getHouse(uuid)));
                break;
            case 3:
                AutoHouseTaskManager.addPlayerMode(player,0,100L);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've chosen to buy this house!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRight-click &aagain &bto confirm this choice!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThis option will time out in 5 seconds..."));
        }
    }
    private static void executeHousePurchase(Sign interact_block, Player player, int cost){
        String uuid = player.getUniqueId().toString();
        String housename = interact_block.getLine(2) + interact_block.getLine(3);
        PokemonIDs.getEconomy().withdrawPlayer(player, cost);
        AutoHouseStorageManager.addHouseOwner(housename, uuid);
        PokemonIDs.getPlayerStorageManager().setHouse(housename, uuid);
        interact_block.setLine(0, OWNED_BY);
        interact_block.setLine(1, player.getName());
        interact_block.update();
        PokemonIDs.instance().getServer().dispatchCommand(PokemonIDs.instance().getServer().getConsoleSender(), "rg addowner -w " + player.getWorld().getName() + " " + housename + " " + player.getName());
    }
    private static void checkHouseRemoval(Sign interact_block, Player player){
        if(!(interact_block.getLine(1).equals(player.getName()) || player.hasPermission("worldguard.region"))){
            return;
        }
        if(AutoHouseTaskManager.getMode(player)==1){
            executeHouseRemoval(interact_block,player);
        } else {
            AutoHouseTaskManager.addPlayerMode(player, 1, 200L);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've chosen to remove " + interact_block.getLine(1) + " from this house!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRight-click &aagain to confirm this choice, or rightclick without crouching to change to &bCo-Owner addition&a!"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThis option will time out in 10 seconds..."));
            if(player.hasPermission("worldguard.region")){
                getSeenTime(interact_block,player);
            }
        }
    }
    private static void executeHouseRemoval(Sign interact_block, Player player) {
        AutoHouseTaskManager.removePlayerMode(player,1);
        if(interact_block.getLine(1).equalsIgnoreCase(player.getName())){
            PokemonIDs.getEconomy().depositPlayer(player, AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(interact_block.getLine(2)+interact_block.getLine(3)))/3);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aHouse Sold! Check your balance!"));
        }
        runHouseEviction(interact_block);
    }
    private static void activateCoownerAddition(Sign interact_block, Player player) {
        if (getOwnerType(interact_block) == OwnerType.SOLO_OWNER){
            if(AutoHouseTaskManager.getMode(player) == 2){
                AutoHouseTaskManager.removePlayerMode(player,2);
                runCoownerAddition(interact_block);
            } else {
                AutoHouseTaskManager.addPlayerMode(player, 2, 200L);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've chosen to &badd a co-owner&a to your house!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bRight-click &aagain to confirm this choice, or crouch-rightclick to change to &bSell Mode&a!"));
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThis option will time out in 10 seconds..."));
            }
        } else {
            AutoHouseTaskManager.removePlayerMode(player,2);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou appear to already have a co-owner! Only 2 people per house please!"));
        }
    }
    private static void getSeenTime(Sign interact_block, Player player) {
        player.performCommand("seen "+interact_block.getLine(1));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Make sure they don't have an AWAY post in our forum, and aren't a GameFreak if their offline time is over 2 weeks!"));
    }

    private static void runCoownerAddition(Sign interact_block) {
        Block nextSign = getHouseSignPositions(interact_block)[1].getBlock();
        nextSign.setType(interact_block.getType());
        nextSign.setBlockData(interact_block.getBlockData());
        Sign s = (Sign) nextSign.getState();
        s.setLine(0, FOR_SALE);
        s.setLine(1, Integer.toString(AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(interact_block.getLine(2)+interact_block.getLine(3)))));
        s.setLine(2, interact_block.getLine(2));
        s.setLine(3, interact_block.getLine(3));
        s.update();
    }

    private static void runHouseEviction(Sign interact_block) {
        //do the paperwork to record that the person is homeless
        String housename = interact_block.getLine(2)+interact_block.getLine(3);
        String ownername = interact_block.getLine(1);
        PokemonIDs.getPlayerStorageManager().removeHouse(PokemonIDs.getOfflinePlayerFromString(ownername).getUniqueId().toString());
        AutoHouseStorageManager.removeHouseOwner(housename, PokemonIDs.getOfflinePlayerFromString(ownername).getUniqueId().toString());
        PokemonIDs.instance().getServer().dispatchCommand(PokemonIDs.instance().getServer().getConsoleSender(), "rg removeowner -w "+interact_block.getWorld().getName()+" "+housename+" "+ownername);
        //figure out how to handle the house:
        //empty it or give it to a coowner, or give it to original owner
        //first find out if the sign has a sign right beside it (marking a coowner)
        OwnerType owner = getOwnerType(interact_block);
        Bukkit.getLogger().info(owner.toString());
        if (owner == OwnerType.SOLO_OWNER){
            interact_block.setLine(0, FOR_SALE);
            interact_block.setLine(1, Integer.toString(AutoHouseController.getPrice(AutoHouseStorageManager.getHouseSize(housename))));
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
    
    private static OwnerType getOwnerType(Sign interact_block){
        Location primarySign = getHouseSignPositions(interact_block)[0];
        Location secondarySign = getHouseSignPositions(interact_block)[1];
        if(interact_block.getLocation().equals(primarySign)){
            if(secondarySign.getBlock().getType().toString().contains("_WALL_SIGN")){
                return OwnerType.PRIMARY_OWNER;
            }
            return OwnerType.SOLO_OWNER;
        } 
        return OwnerType.COOWNER;
    }
    private static Location[] getHouseSignPositions(Sign interact_block){
        BlockFace direction = ((Directional)interact_block.getData()).getFacing();
        if(direction.equals(BlockFace.NORTH)){ //northfacing, coowner sign is x-1
            if(interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getType().toString().contains("_WALL_SIGN")){
                return new Location[]{interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getLocation(),interact_block.getLocation()};
            } else {
                return new Location[]{interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getLocation()};
            }
        } else if(direction.equals(BlockFace.SOUTH)){ //southfacing, coowner sign is x+1
            if(interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getType().toString().contains("_WALL_SIGN")){
                return new Location[]{interact_block.getWorld().getBlockAt(interact_block.getX()-1, interact_block.getY(), interact_block.getZ()).getLocation(),interact_block.getLocation()};
            } else {
                return new Location[]{interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX()+1, interact_block.getY(), interact_block.getZ()).getLocation()};
            }
        } else if(direction.equals(BlockFace.WEST)){ //westfacing, coowner sign is z+1
            if(interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getType().toString().contains("_WALL_SIGN")){
                return new Location[]{interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getLocation(),interact_block.getLocation()};
            } else {
                return new Location[]{interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getLocation()};
            }
        } else if(direction.equals(BlockFace.EAST)){ //eastfacing, coowner sign is z-1
            if(interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getType().toString().contains("_WALL_SIGN")){
                return new Location[]{interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()+1).getLocation(),interact_block.getLocation()};
            } else {
                return new Location[]{interact_block.getLocation(),interact_block.getWorld().getBlockAt(interact_block.getX(), interact_block.getY(), interact_block.getZ()-1).getLocation()};
            }
        }
        return new Location[]{interact_block.getLocation(),interact_block.getLocation()};
    }
}
