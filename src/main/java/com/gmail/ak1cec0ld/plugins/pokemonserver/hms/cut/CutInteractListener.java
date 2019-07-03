package com.gmail.ak1cec0ld.plugins.pokemonserver.hms.cut;

import com.gmail.ak1cec0ld.plugins.pokemonserver.PokemonServer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.EventPriority.HIGH;

public class CutInteractListener implements Listener{
    private CutController controller;
    public CutInteractListener(CutController ctrl){
        this.controller = ctrl;
    }
    
    
    @EventHandler(priority = HIGH)
    public void onInteractWithBlock(PlayerInteractEvent event){
        if(!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)))return;
        Block hitblock = event.getClickedBlock();
        if(!(hitblock.getType().equals(Material.DARK_OAK_LOG)||hitblock.getType().equals(Material.DARK_OAK_LEAVES)))return;
        int id = getIdentifierY(hitblock);
        if(id < 0)return;

        event.setCancelled(true);
        if(controller.permissionToBreak(event.getPlayer())){
            PokemonServer.msgActionBar(event.getPlayer(),"You CUT the tree down",ChatColor.DARK_GREEN);
            cutTree(hitblock,id);
        } else {
            PokemonServer.msgActionBar(event.getPlayer(),"This tree looks like it can be CUT down!",ChatColor.RED);
        }
    }

    private void cutTree(Block hitblock, int identifier){
        int idshiftx = -1;
        int idshiftz = -1;
        int locsearch = 0;
        while(isCutLog(hitblock.getWorld().getBlockAt(hitblock.getX(), identifier, hitblock.getZ()-1-locsearch))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftz = 1;
        }
        locsearch = 0;
        while(isCutLog(hitblock.getWorld().getBlockAt(hitblock.getX()-1-locsearch, identifier, hitblock.getZ()))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftx = 1;
        }
        int minx = Math.min(hitblock.getX(), idshiftx+hitblock.getX());
        int minz = Math.min(hitblock.getZ(), idshiftz+hitblock.getZ());
        
        World world = hitblock.getWorld();
        setLayer(world,minx,identifier,minz,Material.GRASS_BLOCK);
        for(int y = identifier+1; y <= identifier+3; y++){
            setLayer(world,minx,y,minz,Material.AIR);
        }
        
        controller.getPlugin().getServer().getScheduler().runTaskLater(controller.getPlugin(), () -> {
            setLayer(world,minx,identifier+0,minz,Material.END_PORTAL_FRAME);
            setLayer(world,minx,identifier+1,minz,Material.DARK_OAK_LOG);
            setLayer(world,minx,identifier+2,minz,Material.DARK_OAK_LOG);
            setLayer(world,minx,identifier+3,minz,Material.DARK_OAK_LEAVES);
        }, 100L);
    }

    private int getIdentifierY(Block hitblock){
        World world = hitblock.getWorld();
        int IB_x = hitblock.getX();
        int IB_z = hitblock.getZ();
        for(int id = hitblock.getY(); id > hitblock.getY()-8; id--){
            if(isCutLog(world.getBlockAt(IB_x, id, IB_z))){
                return id;
            }
        }
        return -1;
    }
    
    private void setLayer(World world, int xmin, int y, int zmin, Material newmat){
        world.getBlockAt(xmin, y, zmin).setType(newmat);
        if(newmat.equals(Material.END_PORTAL_FRAME)){
            setFacingEast(world.getBlockAt(xmin, y, zmin));
        }
        world.getBlockAt(xmin, y, zmin+1).setType(newmat);
        if(newmat.equals(Material.END_PORTAL_FRAME)){
            setFacingEast(world.getBlockAt(xmin, y, zmin+1));
        }
        world.getBlockAt(xmin+1, y, zmin).setType(newmat);
        if(newmat.equals(Material.END_PORTAL_FRAME)){
            setFacingEast(world.getBlockAt(xmin+1, y, zmin));
        }
        world.getBlockAt(xmin+1, y, zmin+1).setType(newmat);
        if(newmat.equals(Material.END_PORTAL_FRAME)){
            setFacingEast(world.getBlockAt(xmin+1, y, zmin+1));
        }
    }
    
    private void setFacingEast(Block target){
        Directional bd = (Directional)target.getBlockData();
        bd.setFacing(BlockFace.EAST);
        target.setBlockData(bd);
    }
    
    private boolean isCutLog(Block block){
        return (block.getType().equals(Material.END_PORTAL_FRAME) && ((Directional)block.getBlockData()).getFacing().equals(BlockFace.EAST));
    }
}
