package com.gmail.ak1cec0ld.plugins.pokemonids.hms.dive;

import com.gmail.ak1cec0ld.plugins.pokemonids.PokemonIDs;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class DiveInteractListener implements Listener {
    private DiveController controller;

    public DiveInteractListener(DiveController diveController) {
        this.controller = diveController;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)))return;
        Block hitblock = event.getClickedBlock();
        if(!hitblock.getType().equals(Material.BARRIER))return;
        int id = getIdentifierY(hitblock);
        if(id < 0)return;

        event.setCancelled(true);
        if(controller.permissionToBreak(event.getPlayer())){
            PokemonIDs.msgActionBar(event.getPlayer(),"You used DIVE!",ChatColor.BLUE);
            diveBlockBreak(hitblock,id);
        } else {
            PokemonIDs.msgActionBar(event.getPlayer(),"The water looks deep here.", ChatColor.RED);
        }
    }
    
    private void diveBlockBreak(Block hitblock, int id){
        for(int xdiff = hitblock.getX()-2; xdiff <= hitblock.getX()+2; xdiff++){
            for(int zdiff = hitblock.getZ()-2; zdiff <= hitblock.getZ()+2; zdiff++){
                if(isDiveBarrier(hitblock.getWorld().getBlockAt(xdiff, id, zdiff))){
                    Block barrier = hitblock.getWorld().getBlockAt(xdiff, hitblock.getY(), zdiff);
                    barrier.setType(Material.AIR);
                    Bukkit.getScheduler().runTaskLater(controller.getPlugin(), new Runnable(){
                        @Override
                        public void run() {
                            barrier.setType(Material.BARRIER);
                        }
                    }, 100L);
                }
            }
        }
    }
    
    private int getIdentifierY(Block hitblock){
        World world = hitblock.getWorld();
        int IB_x = hitblock.getX();
        int IB_z = hitblock.getZ();
        for(int id = hitblock.getY(); id > hitblock.getY()-16; id--){
            if(isDiveBarrier(world.getBlockAt(IB_x, id, IB_z))){
                return id;
            }
        }
        return -1;
    }
    
    private boolean isDiveBarrier(Block block){
        return (block.getType().equals(Material.END_PORTAL_FRAME) && ((Directional)block.getBlockData()).getFacing().equals(BlockFace.EAST));
    }
}
