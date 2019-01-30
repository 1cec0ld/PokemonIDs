package com.gmail.ak1cec0ld.plugins.pokemonids.HMs.RockSmash;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class RSmashInteractListener implements Listener{
    private RSmashController controller;
    private Random r;
    private List<BlockFace> faces;
    
    public RSmashInteractListener(RSmashController ctrl){
        this.controller = ctrl;
        this.r = new Random();
        faces = Arrays.asList(BlockFace.valueOf("WEST"), BlockFace.valueOf("EAST"), BlockFace.valueOf("NORTH"), BlockFace.valueOf("SOUTH"));
    }
    
    
    @EventHandler
    public void onInteractWithBlock(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Block hitblock = event.getClickedBlock();
            if(hitblock.getType().equals(Material.COBBLESTONE_STAIRS)){
                int id = getIdentifierY(hitblock.getLocation());
                if(id >= 0){
                    if(controller.permissionToBreak(event.getPlayer())){
                        event.getPlayer().sendMessage(ChatColor.DARK_GREEN+"You used ROCK SMASH!");
                        breakRock(getRockLoc(hitblock.getLocation(),id));
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED+"This rock looks breakable.");
                    }
                }
            }
        }
    }
    
    private void breakRock(Location loc){
        setLayer(loc,Material.AIR);
        setLayer(loc.toVector().add(new Vector(0,1,0)).toLocation(loc.getWorld()),Material.AIR);
        controller.getPlugin().getServer().getScheduler().runTaskLater(controller.getPlugin(), new Runnable(){
            @Override
            public void run() {
                makeRock(loc);
            }
        }, 100L);
    }

    private Location getRockLoc(Location hitblockLoc, int id_y){
        int identifier = id_y;
        int idshiftx = -1;
        int idshiftz = -1;
        int locsearch = 0;
        while(isSmashRock(hitblockLoc.getWorld().getBlockAt((int)hitblockLoc.getX(), identifier, (int)hitblockLoc.getZ()-1-locsearch))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftz=1;
        }
        locsearch = 0;
        while(isSmashRock(hitblockLoc.getWorld().getBlockAt((int)hitblockLoc.getX()-1-locsearch, identifier, (int)hitblockLoc.getZ()))){
            locsearch++;
        }
        if(locsearch%2 == 0){
            idshiftx = 1;
        }
        
        int minx = (int)Math.min(hitblockLoc.getX(), idshiftx+hitblockLoc.getX());
        int minz = (int)Math.min(hitblockLoc.getZ(), idshiftz+hitblockLoc.getZ());
        World world = hitblockLoc.getWorld();
        for(int rocksearch = identifier; rocksearch <= identifier+8; rocksearch++){
            if(world.getBlockAt(minx, rocksearch, minz).getType().equals(Material.COBBLESTONE_STAIRS)){
                return world.getBlockAt(minx,rocksearch,minz).getLocation();
            }
        }
        return null;
    }
    
    private int getIdentifierY(Location hitblock){
        World world = hitblock.getWorld();
        double IB_x = hitblock.getX();
        double IB_z = hitblock.getZ();
        for(double id = hitblock.getY(); id > hitblock.getY()-8; id--){
            if(isSmashRock(world.getBlockAt((int)IB_x, (int)id, (int)IB_z))){
                return (int)id;
            }
        }
        return -1;
    }
    
    private void makeRock(Location loc){
        setLayer(loc,Material.COBBLESTONE_STAIRS);
        setLayer(loc.toVector().add(new Vector(0,1,0)).toLocation(loc.getWorld()),Material.COBBLESTONE_STAIRS);
        randomizeLayer(loc);
        randomizeLayer(loc.toVector().add(new Vector(0,1,0)).toLocation(loc.getWorld()));
    }
    
    private void setLayer(Location loc, Material newmat){
        loc.getBlock().setType(newmat);
        loc.getBlock().getRelative(0,0,1).setType(newmat);
        loc.getBlock().getRelative(1,0,0).setType(newmat);
        loc.getBlock().getRelative(1,0,1).setType(newmat);
    }
    
    private void randomizeLayer(Location loc){
        setFacingRandom(loc.getBlock());
        setFacingRandom(loc.getBlock().getRelative(0,0,1));
        setFacingRandom(loc.getBlock().getRelative(1,0,0));
        setFacingRandom(loc.getBlock().getRelative(1,0,1));
    }
    
    private void setFacingRandom(Block target){
        Stairs st = (Stairs)target.getBlockData();
        st.setFacing(faces.get(r.nextInt(4)));
        st.setHalf(Bisected.Half.values()[r.nextInt(2)]);
        target.setBlockData(st);
    }
    
    private boolean isSmashRock(Block block){
        return (block.getType().equals(Material.END_PORTAL_FRAME) && ((Directional)block.getBlockData()).getFacing().equals(BlockFace.EAST));
    }
}
