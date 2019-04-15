package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.Whirlpool;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class WhirlpoolInteractListener implements Listener {
    private WhirlpoolController controller;

    public WhirlpoolInteractListener(WhirlpoolController diveController) {
        this.controller = diveController;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Block hitblock = event.getClickedBlock();
            if(hitblock.getType().equals(Material.BARRIER)){
                int id = getIdentifierY(hitblock);
                if(id >= 0){
                    event.setCancelled(true);
                    if(controller.permissionToBreak(event.getPlayer())){
                        PokemonIDs.msgActionBar(event.getPlayer(),"You used WHIRLPOOL!", ChatColor.BLUE);
                        diveBlockBreak(hitblock);
                    } else {
                        PokemonIDs.msgActionBar(event.getPlayer(),"The water is making an impassable Whirlpool!",ChatColor.RED);
                    }
                }
            }
        }
    }
    
    private void diveBlockBreak(Block hitblock){
        Location start = getStart(hitblock);
        for(int ex = start.getBlockX(); ex <= start.getBlockX()+5; ex++){
            for(int wy = start.getBlockY(); wy <= start.getBlockY()+4; wy++){
                for(int ze = start.getBlockZ(); ze <= start.getBlockZ()+5; ze++){
                    hitblock.getWorld().getBlockAt(ex, wy, ze).setType(Material.WATER);
                }
            }
        }
        Bukkit.getScheduler().runTaskLater(controller.getPlugin(), () -> {
            Location copy;
            for (int x = start.getBlockX(); x <= start.getBlockX()+5; x++){
                for (int z = start.getBlockZ(); z <= start.getBlockZ()+5; z++){
                    for (int y = start.getBlockY(); y <= start.getBlockY()+6; y++){
                        copy = Bukkit.getWorld("Japan").getBlockAt(-2208, 27, 464).getLocation();
                        hitblock.getWorld().getBlockAt(x, y, z).setType(copy.add(x-start.getBlockX(), y-start.getBlockY(), z-start.getBlockZ()).getBlock().getType());
                        copy = Bukkit.getWorld("Japan").getBlockAt(-2208, 27, 464).getLocation();
                        hitblock.getWorld().getBlockAt(x, y, z).setBlockData(copy.add(x-start.getBlockX(), y-start.getBlockY(), z-start.getBlockZ()).getBlock().getBlockData());
                    }
                }
            }
        }, 200L);
    }
    
    private Location getStart(Block hitblock) {
        int y = getIdentifierY(hitblock);
        if (y > 0){
            for(int x = hitblock.getX();x >= hitblock.getX()-7;x--){
                for(int z = hitblock.getZ(); z >= hitblock.getZ()-7;z--){
                    if(!hitblock.getWorld().getBlockAt(x, y, z).getRelative(-1, 0, 0).getType().equals(Material.END_PORTAL_FRAME) &&
                       !hitblock.getWorld().getBlockAt(x, y, z).getRelative(0, 0, -1).getType().equals(Material.END_PORTAL_FRAME) &&
                       hitblock.getWorld().getBlockAt(x, y, z).getType().equals(Material.END_PORTAL_FRAME)){
                        Bukkit.getLogger().info("Used Whirlpool at "+hitblock.toString());
                        return hitblock.getWorld().getBlockAt(x, y, z).getLocation();
                    }
                }
            }
        }
        Bukkit.getLogger().info("Couldn't find a GetStart() Location for Whirlpool");
        return new Location(Bukkit.getWorld("Japan"),-2208,27,464);
    }

    private int getIdentifierY(Block hitblock){
        World world = hitblock.getWorld();
        int IB_x = hitblock.getX();
        int IB_z = hitblock.getZ();
        for(int id = hitblock.getY(); id > hitblock.getY()-16; id--){
            if(isWPBarrier(world.getBlockAt(IB_x, id, IB_z))){
                return id;
            }
        }
        return -1;
    }
    
    private boolean isWPBarrier(Block block){
        return (block.getType().equals(Material.END_PORTAL_FRAME) && ((Directional)block.getBlockData()).getFacing().equals(BlockFace.WEST));
    }
}
